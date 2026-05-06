package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_ekspertipma")
@Getter
@Setter
public class EkspertIPMA {

    @Id
    private Long id;

    @Basic
    private String imie;

    @Basic
    private String nazwisko;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataSzkolenia;

    @OneToOne(optional = true)
    private Asesor asesor;

    @ManyToMany(mappedBy = "aktualniEksperci")
    private Set<CzlonekBiuraNagrody> czlonkowieBiuraNagrody;
}
