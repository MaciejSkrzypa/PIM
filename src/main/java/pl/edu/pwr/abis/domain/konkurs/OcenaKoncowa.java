package pl.edu.pwr.abis.domain.konkurs;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OcenaKoncowa extends OcenaProjektu {

    private Projekt projekt;

    public OcenaKoncowa(
        BigDecimal ocena,
        LocalDate rzeczywistyTerminWystawienia,
        LocalDate terminReferencyjny
    ) {
        super(ocena, rzeczywistyTerminWystawienia, terminReferencyjny);
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        if (this.projekt == projekt) {
            return;
        }

        Projekt previous = this.projekt;
        this.projekt = null;
        if (previous != null) {
            previous.setOcenaKoncowaInternal(null);
        }

        this.projekt = projekt;
        if (projekt != null) {
            projekt.setOcenaKoncowaInternal(this);
        }
    }

    void setProjektInternal(Projekt projekt) {
        this.projekt = projekt;
    }
}
