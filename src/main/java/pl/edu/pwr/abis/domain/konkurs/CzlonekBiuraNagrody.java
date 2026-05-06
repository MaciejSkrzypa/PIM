package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_czlonekbiuranagrody")
@Getter
@Setter
@DiscriminatorValue("CzlonekBiuraNagrody")
public class CzlonekBiuraNagrody extends Uzytkownik {

    @ManyToOne(optional = true)
    private BiuroNagrody biuroNagrody;

    @ManyToMany
    private Set<EkspertIPMA> aktualniEksperci;
}
