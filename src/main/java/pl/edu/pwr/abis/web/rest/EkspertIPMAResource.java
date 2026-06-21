package pl.edu.pwr.abis.web.rest;

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
public class EkspertIPMAResource {

    private final Logger log = LoggerFactory.getLogger(EkspertIPMAResource.class);

    private final EkspertIPMARepository ekspertIPMARepository;

    public EkspertIPMAResource(EkspertIPMARepository ekspertIPMARepository) {
        this.ekspertIPMARepository = ekspertIPMARepository;
    }

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

    @RequestMapping(value = "/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    public ResponseEntity<EkspertIPMAVM> updateEkspertIPMA(
        @PathVariable("id") Long id,
        @Valid @RequestBody EkspertIPMAVM ekspertIPMAVM
    ) {
        log.debug("REST request to update EkspertIPMA : {}", id);

        return ResponseUtil.wrapOrNotFound(
            ekspertIPMARepository
                .findById(id)
                .map(ekspertIPMA -> {
                    ekspertIPMA.setImie(ekspertIPMAVM.getImie());
                    ekspertIPMA.setNazwisko(ekspertIPMAVM.getNazwisko());
                    ekspertIPMA.setDataSzkolenia(ekspertIPMAVM.getDataSzkolenia());
                    ekspertIPMA.setWymagajaceWeryfikacji(true);
                    return new EkspertIPMAVM(ekspertIPMARepository.save(ekspertIPMA));
                })
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EkspertIPMAVM> getEkspertIPMA(@PathVariable("id") Long id) {
        log.debug("REST request to get EkspertIPMA : {}", id);
        return ResponseUtil.wrapOrNotFound(ekspertIPMARepository.findById(id).map(EkspertIPMAVM::new));
    }

    @GetMapping
    public ResponseEntity<List<EkspertIPMAVM>> getAllEksperciIPMA() {
        log.debug("REST request to get all EkspertIPMA");
        List<EkspertIPMAVM> eksperci = ekspertIPMARepository.findAll().stream().map(EkspertIPMAVM::new).toList();
        return ResponseEntity.ok(eksperci);
    }
}
