package pl.edu.pwr.abis.domain.generalization;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("ClassA")
public class ClassA extends AbstractClassA {

}
