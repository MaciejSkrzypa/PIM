package pl.edu.pwr.abis.domain.konkurs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Zgloszenie {

    private final Integer numerAplikacji;
    private final LocalDate dataWprowadzeniaDoSystemu;
    private StatusZgloszenia status;
    private BigDecimal oplata = BigDecimal.ZERO;
    private final Waluta waluta;
    private Konkurs konkurs;
    private Projekt projekt;
    private Organizacja aplikant;

    public Zgloszenie(
        Integer numerAplikacji,
        LocalDate dataWprowadzeniaDoSystemu,
        StatusZgloszenia status,
        Waluta waluta,
        Konkurs konkurs,
        Projekt projekt,
        Organizacja aplikant
    ) {
        this.numerAplikacji = Objects.requireNonNull(numerAplikacji, "numerAplikacji cannot be null");
        this.dataWprowadzeniaDoSystemu = Objects.requireNonNull(
            dataWprowadzeniaDoSystemu,
            "dataWprowadzeniaDoSystemu cannot be null"
        );
        this.status = Objects.requireNonNull(status, "status cannot be null");
        this.waluta = Objects.requireNonNull(waluta, "waluta cannot be null");
        setKonkurs(konkurs);
        setProjekt(projekt);
        setAplikant(aplikant);
    }

    public Integer getNumerAplikacji() {
        return numerAplikacji;
    }

    public LocalDate getDataWprowadzeniaDoSystemu() {
        return dataWprowadzeniaDoSystemu;
    }

    public StatusZgloszenia getStatus() {
        return status;
    }

    public void setStatus(StatusZgloszenia status) {
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    public BigDecimal getOplata() {
        return oplata;
    }

    public void setOplata(BigDecimal oplata) {
        this.oplata = OcenaProjektu.requireAmount(oplata, "oplata");
    }

    public Waluta getWaluta() {
        return waluta;
    }

    public Konkurs getKonkurs() {
        return konkurs;
    }

    public void setKonkurs(Konkurs konkurs) {
        if (this.konkurs == konkurs) {
            return;
        }
        validateContestAssignment(konkurs, projekt);

        Konkurs previous = this.konkurs;
        this.konkurs = null;
        if (previous != null) {
            previous.removeZgloszenieInternal(this);
        }

        this.konkurs = konkurs;
        if (konkurs != null) {
            konkurs.addZgloszenieInternal(this);
        }
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        if (this.projekt == projekt) {
            return;
        }
        validateProjectAssignment(konkurs, projekt);

        Projekt previous = this.projekt;
        this.projekt = null;
        if (previous != null) {
            previous.removeZgloszenieInternal(this);
        }

        this.projekt = projekt;
        if (projekt != null) {
            projekt.addZgloszenieInternal(this);
        }
    }

    public Organizacja getAplikant() {
        return aplikant;
    }

    public void setAplikant(Organizacja aplikant) {
        if (this.aplikant == aplikant) {
            return;
        }

        Organizacja previous = this.aplikant;
        this.aplikant = null;
        if (previous != null) {
            previous.removeZgloszenieInternal(this);
        }

        this.aplikant = aplikant;
        if (aplikant != null) {
            aplikant.addZgloszenieInternal(this);
        }
    }

    public void detach() {
        setAplikant(null);
        setProjekt(null);
        setKonkurs(null);
    }

    private void validateContestAssignment(Konkurs konkurs, Projekt projekt) {
        if (konkurs == null || projekt == null) {
            return;
        }

        for (Zgloszenie existing : konkurs.getZgloszenia()) {
            if (existing != this && existing.getProjekt() == projekt) {
                throw new IllegalArgumentException("Projekt moze miec tylko jedno zgloszenie w konkursie");
            }
        }
    }

    private void validateProjectAssignment(Konkurs konkurs, Projekt projekt) {
        if (konkurs == null || projekt == null) {
            return;
        }

        for (Zgloszenie existing : projekt.getZgloszenia()) {
            if (existing != this && existing.getKonkurs() == konkurs) {
                throw new IllegalArgumentException("Projekt moze miec tylko jedno zgloszenie do danego konkursu");
            }
        }
    }
}
