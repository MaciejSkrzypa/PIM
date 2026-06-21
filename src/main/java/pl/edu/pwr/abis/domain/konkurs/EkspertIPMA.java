package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_ekspertipma")
@Table(name = "konkurs_ekspertipma")
@Getter
@Setter
public class EkspertIPMA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Basic
    private String imie;

    @Basic
    private String nazwisko;

    @Basic
    private LocalDate dataSzkolenia;

    @Column(nullable = false)
    private boolean wymagajaceWeryfikacji = false;

    @OneToOne(optional = true)
    private Asesor asesor;

    @ManyToMany(mappedBy = "aktualniEksperci")
    private Set<CzlonekBiuraNagrody> czlonkowieBiuraNagrody;
}
