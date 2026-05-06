package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_ocenakoncowa")
@Getter
@Setter
@DiscriminatorValue("OcenaKoncowa")
public class OcenaKoncowa extends OcenaProjektu {

    @OneToOne(optional = true)
    private Projekt projekt;
}
