package pl.edu.pwr.abis.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;
import pl.edu.pwr.abis.repository.EkspertIPMARepository;
import pl.edu.pwr.abis.web.rest.vm.EkspertIPMAVM;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/EkspertIPMA")
@Tag(name = "Ekspert IPMA", description = "Publiczne endpointy do zarzadzania ekspertami IPMA")
public class EkspertIPMAResource {

    private final Logger log = LoggerFactory.getLogger(EkspertIPMAResource.class);

    private final EkspertIPMARepository ekspertIPMARepository;

    public EkspertIPMAResource(EkspertIPMARepository ekspertIPMARepository) {
        this.ekspertIPMARepository = ekspertIPMARepository;
    }

    @Operation(
        summary = "Utworz eksperta IPMA",
        description = "Tworzy eksperta IPMA. Pole wymagajaceWeryfikacji jest ustawiane automatycznie na false.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Ekspert utworzony",
                content = @Content(schema = @Schema(implementation = EkspertIPMAVM.class))
            ),
            @ApiResponse(responseCode = "400", description = "Niepoprawne dane wejsciowe", content = @Content)
        }
    )
    @PostMapping
    public ResponseEntity<EkspertIPMAVM> createEkspertIPMA(@Valid @RequestBody EkspertIPMAVM ekspertIPMAVM)
        throws URISyntaxException {
        log.debug("REST request to save EkspertIPMA : {}", ekspertIPMAVM);

        EkspertIPMA ekspertIPMA = new EkspertIPMA();
        ekspertIPMA.setImie(ekspertIPMAVM.getImie());
        ekspertIPMA.setNazwisko(ekspertIPMAVM.getNazwisko());
        ekspertIPMA.setDataSzkolenia(ekspertIPMAVM.getDataSzkolenia());
        ekspertIPMA.setWymagajaceWeryfikacji(false);

        EkspertIPMA result = ekspertIPMARepository.save(ekspertIPMA);
        return ResponseEntity.created(new URI("/EkspertIPMA/" + result.getId())).body(new EkspertIPMAVM(result));
    }

    @Operation(
        summary = "Zaktualizuj eksperta IPMA",
        description = "Aktualizuje dane eksperta IPMA i ustawia wymagajaceWeryfikacji na true. Identyfikator eksperta musi być podany w ciele żądania.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Ekspert IPMA do zaktualizowania z identyfikatorem",
            required = true,
            content = @Content(schema = @Schema(implementation = EkspertIPMAVM.class))
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ekspert zaktualizowany",
                content = @Content(schema = @Schema(implementation = EkspertIPMAVM.class))
            ),
            @ApiResponse(responseCode = "400", description = "Niepoprawne dane wejsciowe", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ekspert nie istnieje", content = @Content)
        }
    )
    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH })
    public ResponseEntity<EkspertIPMAVM> updateEkspertIPMA(
        @Valid @RequestBody EkspertIPMAVM ekspertIPMAVM
    ) {
        log.debug("REST request to update EkspertIPMA : {}", ekspertIPMAVM);

        if (ekspertIPMAVM.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseUtil.wrapOrNotFound(
            ekspertIPMARepository
                .findById(ekspertIPMAVM.getId())
                .map(ekspertIPMA -> {
                    ekspertIPMA.setImie(ekspertIPMAVM.getImie());
                    ekspertIPMA.setNazwisko(ekspertIPMAVM.getNazwisko());
                    ekspertIPMA.setDataSzkolenia(ekspertIPMAVM.getDataSzkolenia());
                    ekspertIPMA.setWymagajaceWeryfikacji(true);
                    return new EkspertIPMAVM(ekspertIPMARepository.save(ekspertIPMA));
                })
        );
    }

    @Operation(
        summary = "Pobierz eksperta IPMA",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ekspert znaleziony",
                content = @Content(schema = @Schema(implementation = EkspertIPMAVM.class))
            ),
            @ApiResponse(responseCode = "404", description = "Ekspert nie istnieje", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EkspertIPMAVM> getEkspertIPMA(
        @Parameter(description = "Identyfikator eksperta", example = "1001") @PathVariable("id") Long id
    ) {
        log.debug("REST request to get EkspertIPMA : {}", id);
        return ResponseUtil.wrapOrNotFound(ekspertIPMARepository.findById(id).map(EkspertIPMAVM::new));
    }

    @Operation(
        summary = "Pobierz liste ekspertow IPMA",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista ekspertow",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = EkspertIPMAVM.class)))
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<EkspertIPMAVM>> getAllEksperciIPMA() {
        log.debug("REST request to get all EkspertIPMA");
        List<EkspertIPMAVM> eksperci = ekspertIPMARepository.findAll().stream().map(EkspertIPMAVM::new).toList();
        return ResponseEntity.ok(eksperci);
    }
}
