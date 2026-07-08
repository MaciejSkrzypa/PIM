package pl.edu.pwr.abis.web.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Dane eksperta IPMA do dodania albo aktualizacji")
public class EkspertIPMAUpsertDTO {

    @Schema(description = "Identyfikator eksperta", example = "1001")
    private Long id;

    @NotBlank
    @Schema(description = "Imie eksperta", example = "Jan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imie;

    @NotBlank
    @Schema(description = "Nazwisko eksperta", example = "Kowalski", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nazwisko;

    @NotNull
    @Schema(description = "Data szkolenia eksperta", example = "2026-01-10", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataSzkolenia;

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
}
