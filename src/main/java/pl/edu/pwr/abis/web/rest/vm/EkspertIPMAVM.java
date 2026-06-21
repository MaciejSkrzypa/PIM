package pl.edu.pwr.abis.web.rest.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;

public class EkspertIPMAVM {

    private Long id;

    @NotBlank
    private String imie;

    @NotBlank
    private String nazwisko;

    private boolean wymagajaceWeryfikacji;

    @NotNull
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
