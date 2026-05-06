package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pwr.abis.domain.basic.DataTypeA;
import pl.edu.pwr.abis.domain.basic.EnumerationA;

@Entity(name = "association_classa")
@Getter
@Setter
public class ClassA {

    @Id
    private Long id;

    @Basic
    private String attributeA;

    @OneToOne(optional = true, mappedBy = "classA")
    private ClassB classB;

    @OneToOne(optional = true, mappedBy = "classA_")
    private ClassB classB_;
}
