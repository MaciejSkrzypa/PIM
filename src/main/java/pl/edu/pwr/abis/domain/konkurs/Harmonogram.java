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

@Entity(name = "konkurs_harmonogram")
@Getter
@Setter
public class Harmonogram {

    @Id
    private Long id;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataSzkoleniaPE;

    @ElementCollection(targetClass = Date.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "harmonogram_datawebinarium", joinColumns = @JoinColumn(name = "harmonogram_id"))
    @Column(name = "data_webinarium")
    @Temporal(TemporalType.DATE)
    @OrderColumn(name = "webinarium_order")
    private List<Date> dataWebinarium;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date maxDataOcenyIndywidualnej;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataSpotkaniaWirtualnego;

    @ElementCollection(targetClass = Date.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "harmonogram_datyraportowjury", joinColumns = @JoinColumn(name = "harmonogram_id"))
    @Column(name = "data_raportu_jury")
    @Temporal(TemporalType.DATE)
    @OrderColumn(name = "raport_jury_order")
    private List<Date> datyRaportowJury;

    @ElementCollection(targetClass = Date.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "harmonogram_datyspotkanjury", joinColumns = @JoinColumn(name = "harmonogram_id"))
    @Column(name = "data_spotkania_jury")
    @Temporal(TemporalType.DATE)
    @OrderColumn(name = "spotkanie_jury_order")
    private List<Date> datySpotkanJury;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "harmonogram_miejscaspotkanjury", joinColumns = @JoinColumn(name = "harmonogram_id"))
    @Column(name = "miejsce_spotkania_jury")
    @OrderColumn(name = "miejsce_jury_order")
    private List<String> miejscaSpotkanJury;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date maxDataWizytyStudyjnej;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataRaportuKoncowego;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataOgloszeniaFinalistow;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataOgloszeniaLaureatow;

    @OneToOne(optional = true)
    private Konkurs konkurs;
}
