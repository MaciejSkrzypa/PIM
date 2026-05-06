package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classa2")
@Getter
@Setter
public class ClassA2 {

    @Id
    private Long id;

    @Basic
    private String attributeA;

    @OneToMany(mappedBy = "classA2")
    private Set<ClassB2> classB2;
}
