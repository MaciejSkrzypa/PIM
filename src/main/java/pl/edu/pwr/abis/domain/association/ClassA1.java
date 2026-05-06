package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classa1")
@Getter
@Setter
public class ClassA1 {

    @Id
    private Long id;

    @Basic
    private String attributeA;

    @OneToOne(optional = false, mappedBy = "classA1")
    private ClassB1 classB1;
}
