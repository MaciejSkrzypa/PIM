package pl.edu.pwr.abis.domain.konkurs;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The diagram does not show precise multiplicities for applicant representatives, so the
 * implementation uses the common one-to-many organization-to-representatives interpretation.
 */
public class Organizacja {

    private String nazwaOrganizacji;
    private Boolean statusCzlonkostwaIPMA;
    private String daneKontaktowe;
    private final Set<Zgloszenie> zgloszenia = new LinkedHashSet<>();
    private final Set<Projekt> sponsorowaneProjekty = new LinkedHashSet<>();
    private final Set<PrzedstawicielAplikanta> przedstawicieleAplikanta = new LinkedHashSet<>();

    public Organizacja(String nazwaOrganizacji, String daneKontaktowe) {
        this.nazwaOrganizacji = Uzytkownik.requireText(nazwaOrganizacji, "nazwaOrganizacji");
        this.daneKontaktowe = Uzytkownik.requireText(daneKontaktowe, "daneKontaktowe");
    }

    public String getNazwaOrganizacji() {
        return nazwaOrganizacji;
    }

    public void setNazwaOrganizacji(String nazwaOrganizacji) {
        this.nazwaOrganizacji = Uzytkownik.requireText(nazwaOrganizacji, "nazwaOrganizacji");
    }

    public Boolean getStatusCzlonkostwaIPMA() {
        return statusCzlonkostwaIPMA;
    }

    public void setStatusCzlonkostwaIPMA(Boolean statusCzlonkostwaIPMA) {
        this.statusCzlonkostwaIPMA = statusCzlonkostwaIPMA;
    }

    public String getDaneKontaktowe() {
        return daneKontaktowe;
    }

    public void setDaneKontaktowe(String daneKontaktowe) {
        this.daneKontaktowe = Uzytkownik.requireText(daneKontaktowe, "daneKontaktowe");
    }

    public Set<Zgloszenie> getZgloszenia() {
        return Collections.unmodifiableSet(zgloszenia);
    }

    public void addZgloszenie(Zgloszenie zgloszenie) {
        Objects.requireNonNull(zgloszenie, "zgloszenie cannot be null");
        zgloszenie.setAplikant(this);
    }

    public void removeZgloszenie(Zgloszenie zgloszenie) {
        if (zgloszenie != null && zgloszenia.contains(zgloszenie)) {
            zgloszenie.setAplikant(null);
        }
    }

    void addZgloszenieInternal(Zgloszenie zgloszenie) {
        zgloszenia.add(zgloszenie);
    }

    void removeZgloszenieInternal(Zgloszenie zgloszenie) {
        zgloszenia.remove(zgloszenie);
    }

    public Set<Projekt> getSponsorowaneProjekty() {
        return Collections.unmodifiableSet(sponsorowaneProjekty);
    }

    public void addSponsorowanyProjekt(Projekt projekt) {
        Objects.requireNonNull(projekt, "projekt cannot be null");
        projekt.setSponsor(this);
    }

    public void removeSponsorowanyProjekt(Projekt projekt) {
        if (projekt != null && sponsorowaneProjekty.contains(projekt)) {
            projekt.setSponsor(null);
        }
    }

    void addSponsorowanyProjektInternal(Projekt projekt) {
        sponsorowaneProjekty.add(projekt);
    }

    void removeSponsorowanyProjektInternal(Projekt projekt) {
        sponsorowaneProjekty.remove(projekt);
    }

    public Set<PrzedstawicielAplikanta> getPrzedstawicieleAplikanta() {
        return Collections.unmodifiableSet(przedstawicieleAplikanta);
    }

    public void addPrzedstawicielAplikanta(PrzedstawicielAplikanta przedstawicielAplikanta) {
        Objects.requireNonNull(przedstawicielAplikanta, "przedstawicielAplikanta cannot be null");
        przedstawicielAplikanta.setOrganizacja(this);
    }

    public void removePrzedstawicielAplikanta(PrzedstawicielAplikanta przedstawicielAplikanta) {
        if (przedstawicielAplikanta != null && przedstawicieleAplikanta.contains(przedstawicielAplikanta)) {
            przedstawicielAplikanta.setOrganizacja(null);
        }
    }

    void addPrzedstawicielAplikantaInternal(PrzedstawicielAplikanta przedstawicielAplikanta) {
        przedstawicieleAplikanta.add(przedstawicielAplikanta);
    }

    void removePrzedstawicielAplikantaInternal(PrzedstawicielAplikanta przedstawicielAplikanta) {
        przedstawicieleAplikanta.remove(przedstawicielAplikanta);
    }
}
