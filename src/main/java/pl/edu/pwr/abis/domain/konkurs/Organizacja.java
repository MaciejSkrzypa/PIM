package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_organizacja")
@Getter
@Setter
public class Organizacja {

    @Id
    private Long id;

    @Basic
    private String nazwaOrganizacji;

    @Basic
    private Boolean statusCzlonkostwaIPMA;

    @Basic
    private String daneKontaktowe;

    @OneToMany(mappedBy = "aplikant")
    private Set<Zgloszenie> zgloszenia;

    @OneToMany(mappedBy = "sponsor")
    private Set<Projekt> projekty;

    @OneToMany(mappedBy = "organizacja")
    private Set<PrzedstawicielAplikanta> przedstawicieleAplikanta;
}
