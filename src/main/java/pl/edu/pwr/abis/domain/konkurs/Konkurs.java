package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_konkurs")
@Getter
@Setter
public class Konkurs {

    @Id
    private Integer edycja;

    @Basic
    private Double kosztUczestnictwa;

    @Basic
    private Double rabat;

    @Basic
    private Integer wersjaRegulaminu;

    @Enumerated
    private StatusKonkursu status;

    @OneToOne(optional = true, mappedBy = "konkurs", cascade = CascadeType.REMOVE)
    private Harmonogram harmonogram;

    @ManyToMany
    private Set<Kategoria> kategorie;

    @OneToMany(mappedBy = "konkurs", cascade = CascadeType.REMOVE)
    private Set<Zgloszenie> zgloszenia;

    // Kwalifikator "edycja" jest reprezentowany bezposrednio przez encje Konkurs.
    @ManyToMany
    private Set<CzlonekJury> jury;

    // Poprawka po weryfikacji licznosci: jeden czlonek jury moze przewodniczyc wielu konkursom.
    // Ograniczenie {subsets jury} nie jest wymuszane na poziomie samej definicji encji.
    @ManyToOne(optional = true)
    private CzlonekJury przewodniczacy;
}
