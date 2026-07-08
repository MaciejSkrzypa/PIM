package pl.edu.pwr.abis.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dane eksperta IPMA zwracane przez API")
public class EkspertIPMADTO {

    @Schema(description = "Identyfikator eksperta", example = "1001")
    private Long id;

    @Schema(description = "Imie eksperta", example = "Jan")
    private String imie;

    @Schema(description = "Nazwisko eksperta", example = "Kowalski")
    private String nazwisko;

    @Schema(description = "Data szkolenia eksperta", example = "2026-01-10")
    private LocalDate dataSzkolenia;

    @Schema(description = "Czy dane eksperta wymagaja weryfikacji", example = "true")
    private boolean wymagajaceWeryfikacji;

    @Schema(description = "Komunikat bledu dla eksperta, jezeli przetwarzanie sie nie powiodlo")
    private String errorMessage;

    public EkspertIPMADTO() {}

    public EkspertIPMADTO(EkspertIPMA ekspertIPMA) {
        this.id = ekspertIPMA.getId();
        this.imie = ekspertIPMA.getImie();
        this.nazwisko = ekspertIPMA.getNazwisko();
        this.dataSzkolenia = ekspertIPMA.getDataSzkolenia();
        this.wymagajaceWeryfikacji = ekspertIPMA.isWymagajaceWeryfikacji();
    }

    public static EkspertIPMADTO withError(EkspertIPMAUpsertDTO upsertDTO, String errorMessage) {
        EkspertIPMADTO ekspertIPMADTO = new EkspertIPMADTO();
        ekspertIPMADTO.setId(upsertDTO.getId());
        ekspertIPMADTO.setImie(upsertDTO.getImie());
        ekspertIPMADTO.setNazwisko(upsertDTO.getNazwisko());
        ekspertIPMADTO.setDataSzkolenia(upsertDTO.getDataSzkolenia());
        ekspertIPMADTO.setWymagajaceWeryfikacji(false);
        ekspertIPMADTO.setErrorMessage(errorMessage);
        return ekspertIPMADTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public LocalDate getDataSzkolenia() {
        return dataSzkolenia;
    }

    public void setDataSzkolenia(LocalDate dataSzkolenia) {
        this.dataSzkolenia = dataSzkolenia;
    }

    public boolean isWymagajaceWeryfikacji() {
        return wymagajaceWeryfikacji;
    }

    public void setWymagajaceWeryfikacji(boolean wymagajaceWeryfikacji) {
        this.wymagajaceWeryfikacji = wymagajaceWeryfikacji;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
