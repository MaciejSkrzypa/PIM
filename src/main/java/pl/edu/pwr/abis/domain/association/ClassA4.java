package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classa4")
@Getter
@Setter
public class ClassA4 {

    @Id
    private Long id;

    @Basic
    private String attributeA;

    // ewentualnie @ElementCollection ale tylko skierowane asocjacje
    @OneToMany(mappedBy = "classA4", cascade = CascadeType.REMOVE)
    private Set<ClassB4> classB4;
}
