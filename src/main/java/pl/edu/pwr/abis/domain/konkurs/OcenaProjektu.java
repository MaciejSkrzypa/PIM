package pl.edu.pwr.abis.domain.konkurs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The derived attribute czyOcenaOpozniona depends on a business deadline not made explicit in the
 * diagram, so the implementation derives it from an optional reference date passed at creation.
 */
public abstract class OcenaProjektu {

    private final BigDecimal ocena;
    private final LocalDate rzeczywistyTerminWystawienia;
    private final LocalDate terminReferencyjny;

    protected OcenaProjektu(
        BigDecimal ocena,
        LocalDate rzeczywistyTerminWystawienia,
        LocalDate terminReferencyjny
    ) {
        this.ocena = requireAmount(ocena, "ocena");
        this.rzeczywistyTerminWystawienia = Objects.requireNonNull(
            rzeczywistyTerminWystawienia,
            "rzeczywistyTerminWystawienia cannot be null"
        );
        this.terminReferencyjny = terminReferencyjny;
    }

    public BigDecimal getOcena() {
        return ocena;
    }

    public LocalDate getRzeczywistyTerminWystawienia() {
        return rzeczywistyTerminWystawienia;
    }

    public boolean isCzyOcenaOpozniona() {
        return terminReferencyjny != null && rzeczywistyTerminWystawienia.isAfter(terminReferencyjny);
    }

    protected static BigDecimal requireAmount(BigDecimal value, String fieldName) {
        return Objects.requireNonNull(value, fieldName + " cannot be null");
    }
}
