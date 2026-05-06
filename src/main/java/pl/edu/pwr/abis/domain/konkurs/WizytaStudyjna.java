package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.Basic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_wizytastudyjna")
@Getter
@Setter
public class WizytaStudyjna {

    @Id
    private Long id;

    @Basic
    private String miejsce;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "wizytastudyjna_pytaniaodjury", joinColumns = @JoinColumn(name = "wizyta_id"))
    @Column(name = "pytanie_od_jury")
    @OrderColumn(name = "pytanie_order")
    private List<String> pytaniaOdJury;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "wizytastudyjna_opracowanieodpowiedzi", joinColumns = @JoinColumn(name = "wizyta_id"))
    @Column(name = "opracowanie_odpowiedzi")
    @OrderColumn(name = "odpowiedz_order")
    private List<String> opracowanieOdpowiedzi;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date rzeczywistyTerminZlozeniaRaportu;

    @OneToOne(optional = true)
    private Projekt projekt;
}
