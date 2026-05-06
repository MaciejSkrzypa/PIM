package pl.edu.pwr.abis.domain.basic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "basic_classa")
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "attributeB", "attributeB1" }) })
public class ClassA {

    @Id
    private Long id;

    private static int attributeA;

    @Basic
    private int attributeA1 = 123; //long

    @Basic
    private double attributeA2; // float

    @Basic
    private long attributeA3;

    @NotNull
    @Basic
    private String attributeA4;

    @Basic
    private boolean attributeA5;

    @Basic
    private Double attributeA6; // Float, BigDecimal

    @Basic
    private Long attributeA7; // BigDecimal

    @Basic
    private Boolean attributeA8;

    @Basic
    private String attributeA9;

    @Transient
    public int getAttributeA10() {
        return 0;
    }

    @NotNull
    @Basic
    private String attributeB; // gdy jeden atrybut {id} to @Column(unique = true)

    @NotNull
    @Basic
    private String attributeB1; // gdy jeden atrybut {id} to @Column(unique = true)

    @NotNull
    @Basic
    @Setter(AccessLevel.NONE)
    private String attributeB2;

    @NotNull
    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "attributeA", column = @Column(name = "attributeC_attributeA")),
            @AttributeOverride(name = "attributeB", column = @Column(name = "attributeC_attributeB")),
        }
    )
    private DataTypeA attributeC;

    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "attributeA", column = @Column(name = "attributeC1_attributeA")),
            @AttributeOverride(name = "attributeB", column = @Column(name = "attributeC1_attributeB")),
        }
    )
    private DataTypeA attributeC1;

    @NotNull
    @Enumerated
    private EnumerationA attributeC2;

    @Enumerated
    private EnumerationA attributeC3;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "attributeds", joinColumns = @JoinColumn(name = "classa_id"))
    @Column(name = "attributed", nullable = false)
    private Set<String> attributeD = new HashSet<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "attributed1s", joinColumns = @JoinColumn(name = "classa_id"))
    @Column(name = "attributed1", nullable = false)
    private List<String> attributeD1 = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(
        name = "attributed2s",
        joinColumns = @JoinColumn(name = "classa_id"),
        uniqueConstraints = { @UniqueConstraint(columnNames = { "attributed2" }) }
    )
    @Column(name = "attributed2", nullable = false)
    private List<String> attributeD2 = new ArrayList<>();
}
