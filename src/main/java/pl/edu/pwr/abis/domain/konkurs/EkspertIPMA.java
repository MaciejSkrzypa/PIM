package pl.edu.pwr.abis.domain.konkurs;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class EkspertIPMA {

    private String imie;
    private String nazwisko;
    private LocalDate dataSzkolenia;
    private Asesor asesor;
    private final Set<CzlonekBiuraNagrody> czlonkowieBiuraNagrody = new LinkedHashSet<>();

    public EkspertIPMA(String imie, String nazwisko, LocalDate dataSzkolenia) {
        this.imie = Uzytkownik.requireText(imie, "imie");
        this.nazwisko = Uzytkownik.requireText(nazwisko, "nazwisko");
        this.dataSzkolenia = Objects.requireNonNull(dataSzkolenia, "dataSzkolenia cannot be null");
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = Uzytkownik.requireText(imie, "imie");
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = Uzytkownik.requireText(nazwisko, "nazwisko");
    }

    public LocalDate getDataSzkolenia() {
        return dataSzkolenia;
    }

    public void setDataSzkolenia(LocalDate dataSzkolenia) {
        this.dataSzkolenia = Objects.requireNonNull(dataSzkolenia, "dataSzkolenia cannot be null");
    }

    public Asesor getAsesor() {
        return asesor;
    }

    public void setAsesor(Asesor asesor) {
        if (this.asesor == asesor) {
            return;
        }

        Asesor previous = this.asesor;
        this.asesor = null;
        if (previous != null) {
            previous.setEkspertIPMAInternal(null);
        }

        this.asesor = asesor;
        if (asesor != null) {
            EkspertIPMA previousExpert = asesor.getEkspertIPMA();
            if (previousExpert != null && previousExpert != this) {
                previousExpert.setAsesor(null);
            }
            asesor.setEkspertIPMAInternal(this);
        }
    }

    void setAsesorInternal(Asesor asesor) {
        this.asesor = asesor;
    }

    public Set<CzlonekBiuraNagrody> getCzlonkowieBiuraNagrody() {
        return Collections.unmodifiableSet(czlonkowieBiuraNagrody);
    }

    public void addCzlonekBiuraNagrody(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        Objects.requireNonNull(czlonekBiuraNagrody, "czlonekBiuraNagrody cannot be null");
        if (czlonkowieBiuraNagrody.add(czlonekBiuraNagrody)) {
            czlonekBiuraNagrody.addAktualnyEkspertInternal(this);
        }
    }

    public void removeCzlonekBiuraNagrody(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        if (czlonekBiuraNagrody != null && czlonkowieBiuraNagrody.remove(czlonekBiuraNagrody)) {
            czlonekBiuraNagrody.removeAktualnyEkspertInternal(this);
        }
    }

    void addCzlonekBiuraNagrodyInternal(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        czlonkowieBiuraNagrody.add(czlonekBiuraNagrody);
    }

    void removeCzlonekBiuraNagrodyInternal(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        czlonkowieBiuraNagrody.remove(czlonekBiuraNagrody);
    }
}
