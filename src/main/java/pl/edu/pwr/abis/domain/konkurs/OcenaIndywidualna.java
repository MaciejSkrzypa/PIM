package pl.edu.pwr.abis.domain.konkurs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class OcenaIndywidualna extends OcenaProjektu {

    private Projekt projekt;
    private Asesor asesor;
    private Konkurs konkurs;

    public OcenaIndywidualna(
        BigDecimal ocena,
        LocalDate rzeczywistyTerminWystawienia,
        LocalDate terminReferencyjny,
        Projekt projekt,
        Asesor asesor,
        Konkurs konkurs
    ) {
        super(ocena, rzeczywistyTerminWystawienia, terminReferencyjny);
        attachTo(projekt, asesor, konkurs);
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public Asesor getAsesor() {
        return asesor;
    }

    public Konkurs getKonkurs() {
        return konkurs;
    }

    public void attachTo(Projekt projekt, Asesor asesor, Konkurs konkurs) {
        Objects.requireNonNull(projekt, "projekt cannot be null");
        Objects.requireNonNull(asesor, "asesor cannot be null");
        Objects.requireNonNull(konkurs, "konkurs cannot be null");
        if (!projekt.uczestniczyWKonkursie(konkurs)) {
            throw new IllegalArgumentException("Projekt nie bierze udzialu w podanym konkursie");
        }

        projekt.addAsesor(konkurs, asesor);

        detach();

        this.projekt = projekt;
        this.asesor = asesor;
        this.konkurs = konkurs;

        projekt.addOcenaIndywidualnaInternal(this);
        asesor.addOcenaIndywidualnaInternal(this);
    }

    public void detach() {
        if (projekt != null) {
            projekt.removeOcenaIndywidualnaInternal(this);
        }
        if (asesor != null) {
            asesor.removeOcenaIndywidualnaInternal(this);
        }

        projekt = null;
        asesor = null;
        konkurs = null;
    }
}
