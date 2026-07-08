package pl.edu.pwr.abis.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;
import pl.edu.pwr.abis.repository.EkspertIPMARepository;
import pl.edu.pwr.abis.web.rest.dto.EkspertIPMADTO;
import pl.edu.pwr.abis.web.rest.dto.EkspertIPMAResponseDTO;
import pl.edu.pwr.abis.web.rest.dto.EkspertIPMAUpsertDTO;

@RestController
@RequestMapping("/EkspertIPMA")
@Tag(name = "Ekspert IPMA", description = "Publiczny endpoint do zapisu ekspertow IPMA")
public class EkspertIPMAResource {

    private static final String STATUS_OK = "OK";
    private static final String STATUS_ERROR = "ERROR";

    private final Logger log = LoggerFactory.getLogger(EkspertIPMAResource.class);

    private final EkspertIPMARepository ekspertIPMARepository;
    private final TransactionTemplate transactionTemplate;
    private final Validator validator;

    public EkspertIPMAResource(
        EkspertIPMARepository ekspertIPMARepository,
        PlatformTransactionManager transactionManager,
        Validator validator
    ) {
        this.ekspertIPMARepository = ekspertIPMARepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        this.validator = validator;
    }

    @Operation(
        summary = "Dodaj albo zaktualizuj ekspertow IPMA",
        description = "Przetwarza liste ekspertow IPMA. Brak id oznacza dodanie eksperta, a podane id oznacza aktualizacje.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Lista ekspertow IPMA do dodania albo aktualizacji",
            required = true,
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EkspertIPMAUpsertDTO.class)))
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Eksperci przetworzeni",
                content = @Content(schema = @Schema(implementation = EkspertIPMAResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Niepoprawne dane wejsciowe albo brak eksperta dla podanego id",
                content = @Content(schema = @Schema(implementation = EkspertIPMAResponseDTO.class))
            )
        }
    )
    @PutMapping
    public ResponseEntity<EkspertIPMAResponseDTO> upsertEksperciIPMA(
        @RequestBody(required = false) List<EkspertIPMAUpsertDTO> eksperciIPMA
    ) {
        log.debug("REST request to upsert EkspertIPMA collection : {}", eksperciIPMA);

        String validationMessage = validatePayload(eksperciIPMA);
        if (validationMessage != null) {
            return badRequest(validationMessage);
        }

        return transactionTemplate.execute(status -> {
            List<EkspertIPMADTO> processedEksperci = processEksperci(eksperciIPMA);
            EkspertIPMADTO errorEkspertIPMA = findEkspertWithError(processedEksperci);
            if (errorEkspertIPMA != null) {
                status.setRollbackOnly();
                return ResponseEntity.badRequest()
                    .body(new EkspertIPMAResponseDTO(STATUS_ERROR, errorEkspertIPMA.getErrorMessage(), List.of(errorEkspertIPMA)));
            }

            return ResponseEntity.ok(new EkspertIPMAResponseDTO(STATUS_OK, "Eksperci IPMA zostali zapisani.", processedEksperci));
        });
    }

    private String validatePayload(List<EkspertIPMAUpsertDTO> eksperciIPMA) {
        if (eksperciIPMA == null || eksperciIPMA.isEmpty()) {
            return "Payload musi zawierac co najmniej jednego eksperta IPMA.";
        }

        for (int i = 0; i < eksperciIPMA.size(); i++) {
            EkspertIPMAUpsertDTO ekspertIPMA = eksperciIPMA.get(i);
            if (ekspertIPMA == null) {
                return "Ekspert IPMA na pozycji " + (i + 1) + " nie moze byc pusty.";
            }

            Set<ConstraintViolation<EkspertIPMAUpsertDTO>> violations = validator.validate(ekspertIPMA);
            if (!violations.isEmpty()) {
                ConstraintViolation<EkspertIPMAUpsertDTO> violation = violations.iterator().next();
                return "Ekspert IPMA na pozycji " + (i + 1) + " ma niepoprawne pole "
                    + violation.getPropertyPath() + ": " + violation.getMessage() + ".";
            }
        }

        return null;
    }

    private List<EkspertIPMADTO> processEksperci(List<EkspertIPMAUpsertDTO> eksperciIPMA) {
        List<EkspertIPMADTO> processedEksperci = new ArrayList<>();

        for (EkspertIPMAUpsertDTO ekspertIPMAUpsertDTO : eksperciIPMA) {
            if (ekspertIPMAUpsertDTO.getId() == null) {
                processedEksperci.add(createEkspertIPMA(ekspertIPMAUpsertDTO));
                continue;
            }

            if (!ekspertIPMARepository.existsById(ekspertIPMAUpsertDTO.getId())) {
                String message = "Nie znaleziono eksperta IPMA o id " + ekspertIPMAUpsertDTO.getId() + ".";
                processedEksperci.add(EkspertIPMADTO.withError(ekspertIPMAUpsertDTO, message));
                break;
            }

            processedEksperci.add(updateEkspertIPMA(ekspertIPMAUpsertDTO));
        }

        return processedEksperci;
    }

    private EkspertIPMADTO findEkspertWithError(List<EkspertIPMADTO> eksperci) {
        return eksperci.stream().filter(ekspertIPMA -> ekspertIPMA.getErrorMessage() != null).findFirst().orElse(null);
    }

    private EkspertIPMADTO createEkspertIPMA(EkspertIPMAUpsertDTO ekspertIPMAUpsertDTO) {
        EkspertIPMA ekspertIPMA = new EkspertIPMA();
        applyUpsertData(ekspertIPMA, ekspertIPMAUpsertDTO);
        ekspertIPMA.setWymagajaceWeryfikacji(true);
        return new EkspertIPMADTO(ekspertIPMARepository.save(ekspertIPMA));
    }

    private EkspertIPMADTO updateEkspertIPMA(EkspertIPMAUpsertDTO ekspertIPMAUpsertDTO) {
        ekspertIPMARepository.updateEkspertIPMA(
            ekspertIPMAUpsertDTO.getId(),
            ekspertIPMAUpsertDTO.getImie(),
            ekspertIPMAUpsertDTO.getNazwisko(),
            ekspertIPMAUpsertDTO.getDataSzkolenia()
        );
        return toEkspertIPMADTO(ekspertIPMAUpsertDTO);
    }

    private void applyUpsertData(EkspertIPMA ekspertIPMA, EkspertIPMAUpsertDTO ekspertIPMAUpsertDTO) {
        ekspertIPMA.setImie(ekspertIPMAUpsertDTO.getImie());
        ekspertIPMA.setNazwisko(ekspertIPMAUpsertDTO.getNazwisko());
        ekspertIPMA.setDataSzkolenia(ekspertIPMAUpsertDTO.getDataSzkolenia());
    }

    private EkspertIPMADTO toEkspertIPMADTO(EkspertIPMAUpsertDTO ekspertIPMAUpsertDTO) {
        EkspertIPMADTO ekspertIPMADTO = new EkspertIPMADTO();
        ekspertIPMADTO.setId(ekspertIPMAUpsertDTO.getId());
        ekspertIPMADTO.setImie(ekspertIPMAUpsertDTO.getImie());
        ekspertIPMADTO.setNazwisko(ekspertIPMAUpsertDTO.getNazwisko());
        ekspertIPMADTO.setDataSzkolenia(ekspertIPMAUpsertDTO.getDataSzkolenia());
        ekspertIPMADTO.setWymagajaceWeryfikacji(true);
        return ekspertIPMADTO;
    }

    private ResponseEntity<EkspertIPMAResponseDTO> badRequest(String message) {
        return ResponseEntity.badRequest().body(new EkspertIPMAResponseDTO(STATUS_ERROR, message));
    }

}
