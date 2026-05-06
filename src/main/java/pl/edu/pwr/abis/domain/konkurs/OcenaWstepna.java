package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_ocenawstepna")
@Getter
@Setter
@DiscriminatorValue("OcenaWstepna")
public class OcenaWstepna extends OcenaProjektu {

    @OneToOne(optional = true)
    private Projekt projekt;
}
