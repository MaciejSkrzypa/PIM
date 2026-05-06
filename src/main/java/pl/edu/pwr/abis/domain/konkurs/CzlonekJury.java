package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_czlonekjury")
@Getter
@Setter
@DiscriminatorValue("CzlonekJury")
public class CzlonekJury extends Uzytkownik {

    @Basic
    private String imie;

    @Basic
    private String nazwisko;

    @ManyToMany(mappedBy = "jury")
    private java.util.Set<Konkurs> konkursyJury;

    // Poprzednie odwzorowanie 1-1 bylo bledne; z diagramu wynika 0..* konkursow po stronie czlonka jury.
    @OneToMany(mappedBy = "przewodniczacy")
    private List<Konkurs> zarzadzaneKonkursy = new ArrayList<>();
}
