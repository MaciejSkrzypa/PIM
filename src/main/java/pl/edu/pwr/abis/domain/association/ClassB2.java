package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classb2")
@Getter
@Setter
public class ClassB2 {

    @Id
    private Long id;

    @Basic
    private String attributeB;

    @ManyToOne(optional = true)
    private ClassA2 classA2;
}
