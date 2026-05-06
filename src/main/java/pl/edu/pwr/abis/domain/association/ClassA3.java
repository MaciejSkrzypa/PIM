package pl.edu.pwr.abis.domain.association;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "association_classa3")
@Getter
@Setter
public class ClassA3 {

    @Id
    private Long id;

    @Basic
    private String attributeA;

    @OneToMany(mappedBy = "classA3")
    @OrderBy("id")
    private List<ClassB3> classB3;

    @OneToMany(mappedBy = "otherClassA3")
    private List<ClassB3> otherClassB3;
}
