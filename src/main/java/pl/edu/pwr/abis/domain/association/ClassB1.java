package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classb1")
@Getter
@Setter
public class ClassB1 {

    @Id
    private Long id;

    @Basic
    private String attributeB;

    @OneToOne(optional = true)
    private ClassA1 classA1;
}
