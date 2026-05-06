package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_projekt")
@Getter
@Setter
public class Projekt {

    @Id
    private Integer id;

    @Basic
    private String nazwa;

    @Basic
    private Integer czasTrwania;

    @Enumerated
    private Tytul uzyskanyTytul;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "projekt_podwykonawcy", joinColumns = @JoinColumn(name = "projekt_id"))
    @Column(name = "podwykonawca", nullable = false)
    private Set<String> podwykonawcy;

    @Basic
    private Integer liczbaCzlonkow;

    @OneToMany(mappedBy = "projekt", cascade = CascadeType.REMOVE)
    private Set<Zgloszenie> zgloszenia;

    // Ograniczenie {subsets konkurs.kategorie} nie jest wymuszane na poziomie samej definicji encji.
    @ManyToMany
    private Set<Kategoria> kategorie;

    @ManyToOne(optional = true)
    private Organizacja sponsor;

    @OneToOne(optional = true, mappedBy = "projekt", cascade = CascadeType.REMOVE)
    private WizytaStudyjna wizytaStudyjna;

    @OneToOne(optional = true, mappedBy = "projekt", cascade = CascadeType.REMOVE)
    private RaportAplikacyjny raportAplikacyjny;

    @OneToOne(optional = true, mappedBy = "projekt", cascade = CascadeType.REMOVE)
    private OcenaWstepna ocenaWstepna;

    @OneToOne(optional = true, mappedBy = "projekt", cascade = CascadeType.REMOVE)
    private OcenaKoncowa ocenaKoncowa;

    @OneToMany(mappedBy = "projekt", cascade = CascadeType.REMOVE)
    private Set<OcenaIndywidualna> ocenyIndywidualne;

    // Kwalifikator "a konkurs" nie jest odwzorowany osobna kolekcja; role realizuje klasa OcenaIndywidualna.
    @ManyToMany
    private Set<Asesor> zespolAsesorow;

    @ManyToOne(optional = true)
    private Asesor asesorWiodacy;
}
