package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_raportaplikacyjny")
@Getter
@Setter
public class RaportAplikacyjny {

    @Id
    private Long id;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "raportaplikacyjny_zalaczniki", joinColumns = @JoinColumn(name = "raport_id"))
    @Column(name = "zalacznik")
    private List<String> wykazZalacznikow;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataZlozeniaFizycznego;

    @Enumerated
    private StatusRaportuAplikacyjnego status;

    @OneToOne(optional = true)
    private Projekt projekt;
}
