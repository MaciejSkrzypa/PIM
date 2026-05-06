package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classb4")
@Getter
@Setter
public class ClassB4 {

    @Id
    private Long id;

    @Basic
    private String attributeB;

    @ManyToOne(optional = true)
    private ClassA4 classA4;
}
