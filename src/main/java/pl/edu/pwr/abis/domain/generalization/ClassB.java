package pl.edu.pwr.abis.domain.generalization;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("ClassB")
public class ClassB extends AbstractClassA {

}
