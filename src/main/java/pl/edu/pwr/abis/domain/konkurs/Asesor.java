package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_asesor")
@Getter
@Setter
@DiscriminatorValue("Asesor")
public class Asesor extends Uzytkownik {

    @ManyToMany(mappedBy = "zespolAsesorow")
    private Set<Projekt> ocenianeProjekty;

    @OneToMany(mappedBy = "asesorWiodacy")
    private Set<Projekt> zarzadzaneProjekty;

    @OneToMany(mappedBy = "asesor")
    private Set<OcenaIndywidualna> ocenyIndywidualne;

    @OneToOne(optional = true, mappedBy = "asesor")
    private EkspertIPMA ekspertIPMA;
}
