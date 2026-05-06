package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_kategoria")
@Getter
@Setter
public class Kategoria {

    @Id
    private Long id;

    @Basic
    private String nazwa;

    @ManyToMany(mappedBy = "kategorie")
    private Set<Konkurs> konkursy;

    @ManyToMany(mappedBy = "kategorie")
    private Set<Projekt> projekty;
}
