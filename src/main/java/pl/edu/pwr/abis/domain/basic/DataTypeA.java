package pl.edu.pwr.abis.domain.basic;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DataTypeA {

    @Basic
    private Character attributeA;

    @Basic
    private String attributeB;
}
