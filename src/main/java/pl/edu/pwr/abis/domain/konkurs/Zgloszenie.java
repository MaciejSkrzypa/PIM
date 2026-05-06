package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_zgloszenie")
@Getter
@Setter
public class Zgloszenie {

    @Id
    private Integer numerAplikacji;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataWprowadzeniaDoSystemu;

    @Enumerated
    private StatusZgloszenia status;

    @Basic
    private Double oplata = 0.0;

    @Enumerated
    private Waluta waluta;

    @ManyToOne(optional = true)
    private Konkurs konkurs;

    @ManyToOne(optional = true)
    private Projekt projekt;

    @ManyToOne(optional = true)
    private Organizacja aplikant;
}
