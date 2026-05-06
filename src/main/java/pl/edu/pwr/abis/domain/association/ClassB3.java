package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classb3")
@Getter
@Setter
public class ClassB3 {

    @Id
    private Long id;

    @Basic
    private String attributeB;

    @ManyToOne(optional = true)
    private ClassA3 classA3;

    @ManyToOne(optional = true)
    private ClassA3 otherClassA3;
}
