package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classa5")
@Getter
@Setter
public class ClassA5 {

    @Id
    private Long id;

    @Basic
    private String attributeA;

    @ManyToMany(mappedBy = "classA5")
    private Set<ClassB5> classB5;
}
