package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_biuronagrody")
@Getter
@Setter
@DiscriminatorValue("BiuroNagrody")
public class BiuroNagrody extends Uzytkownik {

    // Stereotype <<Singleton>> is not enforced directly by the JPA mapping.
    @Basic
    private String adresKorespondencyjny;

    @Basic
    private String email;

    @OneToMany(mappedBy = "biuroNagrody", cascade = CascadeType.REMOVE)
    private Set<CzlonekBiuraNagrody> czlonkowie;
}
