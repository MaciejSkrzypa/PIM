package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classb5")
@Getter
@Setter
public class ClassB5 {

    @Id
    private Long id;

    @Basic
    private String attributeB;

    @ManyToMany
    private Set<ClassA5> classA5;
}
