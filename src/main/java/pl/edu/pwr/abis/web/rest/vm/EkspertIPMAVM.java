package pl.edu.pwr.abis.web.rest.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;

@Schema(description = "Publiczny widok eksperta IPMA")
public class EkspertIPMAVM {

    @Schema(description = "Identyfikator eksperta", example = "1001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Schema(description = "Imie eksperta", example = "Jan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imie;

    @NotBlank
    @Schema(description = "Nazwisko eksperta", example = "Kowalski", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nazwisko;

    @Schema(description = "Czy dane eksperta wymagaja weryfikacji", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean wymagajaceWeryfikacji;

    @NotNull
    @Schema(description = "Data szkolenia eksperta", example = "2026-01-10", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataSzkolenia;

    public EkspertIPMAVM() {}

    public EkspertIPMAVM(EkspertIPMA ekspertIPMA) {
        this.id = ekspertIPMA.getId();
        this.imie = ekspertIPMA.getImie();
        this.nazwisko = ekspertIPMA.getNazwisko();
        this.wymagajaceWeryfikacji = ekspertIPMA.isWymagajaceWeryfikacji();
        this.dataSzkolenia = ekspertIPMA.getDataSzkolenia();
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

    public boolean isWymagajaceWeryfikacji() {
        return wymagajaceWeryfikacji;
    }

    public void setWymagajaceWeryfikacji(boolean wymagajaceWeryfikacji) {
        this.wymagajaceWeryfikacji = wymagajaceWeryfikacji;
    }

    public LocalDate getDataSzkolenia() {
        return dataSzkolenia;
    }

    public void setDataSzkolenia(LocalDate dataSzkolenia) {
        this.dataSzkolenia = dataSzkolenia;
    }
}
