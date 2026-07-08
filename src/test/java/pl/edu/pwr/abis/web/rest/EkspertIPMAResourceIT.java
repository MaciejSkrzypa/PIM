package pl.edu.pwr.abis.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pwr.abis.IntegrationTest;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;
import pl.edu.pwr.abis.repository.EkspertIPMARepository;
import pl.edu.pwr.abis.web.rest.dto.EkspertIPMAUpsertDTO;

@AutoConfigureMockMvc
@IntegrationTest
class EkspertIPMAResourceIT {

    private static final String ENTITY_API_URL = "/EkspertIPMA";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc restEkspertIPMAMockMvc;

    @Autowired
    private EkspertIPMARepository ekspertIPMARepository;

    @BeforeEach
    void cleanUp() {
        ekspertIPMARepository.deleteAll();
    }

    @Test
    void upsertEksperciIPMAWithoutJwt() throws Exception {
        EkspertIPMA existingEkspertIPMA = saveEkspertIPMA("Jan", "Kowalski", "2026-01-10", false);

        EkspertIPMAUpsertDTO updateRequest = ekspertIPMA(
            existingEkspertIPMA.getId(),
            "Anna",
            "Nowak",
            "2026-02-15"
        );
        EkspertIPMAUpsertDTO createRequest = ekspertIPMA(null, "Piotr", "Wisniewski", "2026-03-20");

        restEkspertIPMAMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(List.of(updateRequest, createRequest)))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("Eksperci IPMA zostali zapisani."))
            .andExpect(jsonPath("$.eksperci.length()").value(2))
            .andExpect(jsonPath("$.eksperci[0].id").value(existingEkspertIPMA.getId().intValue()))
            .andExpect(jsonPath("$.eksperci[0].imie").value("Anna"))
            .andExpect(jsonPath("$.eksperci[0].nazwisko").value("Nowak"))
            .andExpect(jsonPath("$.eksperci[0].wymagajaceWeryfikacji").value(true))
            .andExpect(jsonPath("$.eksperci[0].dataSzkolenia").value("2026-02-15"))
            .andExpect(jsonPath("$.eksperci[0].errorMessage").doesNotExist())
            .andExpect(jsonPath("$.eksperci[1].imie").value("Piotr"))
            .andExpect(jsonPath("$.eksperci[1].nazwisko").value("Wisniewski"))
            .andExpect(jsonPath("$.eksperci[1].wymagajaceWeryfikacji").value(true))
            .andExpect(jsonPath("$.eksperci[1].dataSzkolenia").value("2026-03-20"))
            .andExpect(jsonPath("$.eksperci[1].errorMessage").doesNotExist());
    }

    @Test
    void upsertEksperciIPMAReturnsBadRequestWithoutEksperciForInvalidPayload() throws Exception {
        EkspertIPMAUpsertDTO invalidRequest = ekspertIPMA(null, "", "Kowalski", "2026-01-10");

        restEkspertIPMAMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(List.of(invalidRequest)))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("ERROR"))
            .andExpect(jsonPath("$.message").isNotEmpty())
            .andExpect(jsonPath("$.eksperci").doesNotExist());
    }

    @Test
    void upsertEksperciIPMAReturnsErrorEkspertAndRollsBackWhenIdDoesNotExist() throws Exception {
        long countBefore = ekspertIPMARepository.count();
        EkspertIPMAUpsertDTO createRequest = ekspertIPMA(null, "Jan", "Kowalski", "2026-01-10");
        EkspertIPMAUpsertDTO missingUpdateRequest = ekspertIPMA(999999L, "Anna", "Nowak", "2026-02-15");
        EkspertIPMAUpsertDTO notProcessedRequest = ekspertIPMA(null, "Piotr", "Wisniewski", "2026-03-20");

        restEkspertIPMAMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(List.of(createRequest, missingUpdateRequest, notProcessedRequest)))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("ERROR"))
            .andExpect(jsonPath("$.message").value("Nie znaleziono eksperta IPMA o id 999999."))
            .andExpect(jsonPath("$.eksperci.length()").value(1))
            .andExpect(jsonPath("$.eksperci[0].id").value(999999))
            .andExpect(jsonPath("$.eksperci[0].imie").value("Anna"))
            .andExpect(jsonPath("$.eksperci[0].nazwisko").value("Nowak"))
            .andExpect(jsonPath("$.eksperci[0].dataSzkolenia").value("2026-02-15"))
            .andExpect(jsonPath("$.eksperci[0].errorMessage").value("Nie znaleziono eksperta IPMA o id 999999."));

        assertThat(ekspertIPMARepository.count()).isEqualTo(countBefore);
    }

    private EkspertIPMA saveEkspertIPMA(
        String imie,
        String nazwisko,
        String dataSzkolenia,
        boolean wymagajaceWeryfikacji
    ) {
        EkspertIPMA ekspertIPMA = new EkspertIPMA();
        ekspertIPMA.setImie(imie);
        ekspertIPMA.setNazwisko(nazwisko);
        ekspertIPMA.setDataSzkolenia(LocalDate.parse(dataSzkolenia));
        ekspertIPMA.setWymagajaceWeryfikacji(wymagajaceWeryfikacji);
        return ekspertIPMARepository.saveAndFlush(ekspertIPMA);
    }

    private EkspertIPMAUpsertDTO ekspertIPMA(Long id, String imie, String nazwisko, String dataSzkolenia) {
        EkspertIPMAUpsertDTO ekspertIPMAUpsertDTO = new EkspertIPMAUpsertDTO();
        ekspertIPMAUpsertDTO.setId(id);
        ekspertIPMAUpsertDTO.setImie(imie);
        ekspertIPMAUpsertDTO.setNazwisko(nazwisko);
        ekspertIPMAUpsertDTO.setDataSzkolenia(LocalDate.parse(dataSzkolenia));
        return ekspertIPMAUpsertDTO;
    }
}
