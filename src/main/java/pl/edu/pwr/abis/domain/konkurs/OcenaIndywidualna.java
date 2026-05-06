package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_ocenaindywidualna")
@Getter
@Setter
@DiscriminatorValue("OcenaIndywidualna")
public class OcenaIndywidualna extends OcenaProjektu {

    @ManyToOne(optional = true)
    private Projekt projekt;

    @ManyToOne(optional = true)
    private Asesor asesor;

    // Pole konkurs odwzorowuje kwalifikator "a konkurs" z diagramu.
    @ManyToOne(optional = true)
    private Konkurs konkurs;
}
