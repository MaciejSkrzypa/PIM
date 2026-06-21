package pl.edu.pwr.abis.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.abis.IntegrationTest;
import pl.edu.pwr.abis.web.rest.vm.EkspertIPMAVM;

@AutoConfigureMockMvc
@IntegrationTest
class EkspertIPMAResourceIT {

    private static final String ENTITY_API_URL = "/EkspertIPMA";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc restEkspertIPMAMockMvc;

    @Test
    @Transactional
    void createGetAndUpdateEkspertIPMAWithoutJwt() throws Exception {
        EkspertIPMAVM createRequest = ekspertIPMA("Jan", "Kowalski", "2026-01-10");
        createRequest.setWymagajaceWeryfikacji(true);

        MvcResult createResult = restEkspertIPMAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.imie").value("Jan"))
            .andExpect(jsonPath("$.nazwisko").value("Kowalski"))
            .andExpect(jsonPath("$.wymagajaceWeryfikacji").value(false))
            .andExpect(jsonPath("$.dataSzkolenia").value("2026-01-10"))
            .andReturn();

        String location = createResult.getResponse().getHeader("Location");

        restEkspertIPMAMockMvc
            .perform(get(location).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.imie").value("Jan"))
            .andExpect(jsonPath("$.nazwisko").value("Kowalski"))
            .andExpect(jsonPath("$.wymagajaceWeryfikacji").value(false))
            .andExpect(jsonPath("$.dataSzkolenia").value("2026-01-10"))
            .andExpect(jsonPath("$.id").doesNotExist())
            .andExpect(jsonPath("$.asesor").doesNotExist());

        EkspertIPMAVM updateRequest = ekspertIPMA("Anna", "Nowak", "2026-02-15");

        restEkspertIPMAMockMvc
            .perform(put(location).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imie").value("Anna"))
            .andExpect(jsonPath("$.nazwisko").value("Nowak"))
            .andExpect(jsonPath("$.wymagajaceWeryfikacji").value(true))
            .andExpect(jsonPath("$.dataSzkolenia").value("2026-02-15"));
    }

    private EkspertIPMAVM ekspertIPMA(String imie, String nazwisko, String dataSzkolenia) {
        EkspertIPMAVM ekspertIPMAVM = new EkspertIPMAVM();
        ekspertIPMAVM.setImie(imie);
        ekspertIPMAVM.setNazwisko(nazwisko);
        ekspertIPMAVM.setDataSzkolenia(LocalDate.parse(dataSzkolenia));
        return ekspertIPMAVM;
    }
}
