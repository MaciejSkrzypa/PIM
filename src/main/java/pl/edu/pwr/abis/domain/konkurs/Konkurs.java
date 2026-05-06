package pl.edu.pwr.abis.domain.konkurs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The qualified jury relation from the diagram uses "edycja". In code the qualifier is treated as
 * the identity of the contest object itself, so the relation is stored directly on Konkurs.
 */
public class Konkurs {

    private final Integer edycja;
    private final BigDecimal kosztUczestnictwa;
    private final BigDecimal rabat;
    private Integer wersjaRegulaminu;
    private StatusKonkursu status;
    private Harmonogram harmonogram;
    private final Set<Kategoria> kategorie = new LinkedHashSet<>();
    private final Set<Zgloszenie> zgloszenia = new LinkedHashSet<>();
    private final Set<CzlonekJury> jury = new LinkedHashSet<>();
    private CzlonekJury przewodniczacy;

    public Konkurs(
        Integer edycja,
        BigDecimal kosztUczestnictwa,
        BigDecimal rabat,
        StatusKonkursu status
    ) {
        this.edycja = Objects.requireNonNull(edycja, "edycja cannot be null");
        this.kosztUczestnictwa = OcenaProjektu.requireAmount(kosztUczestnictwa, "kosztUczestnictwa");
        this.rabat = OcenaProjektu.requireAmount(rabat, "rabat");
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    public Integer getEdycja() {
        return edycja;
    }

    public BigDecimal getKosztUczestnictwa() {
        return kosztUczestnictwa;
    }

    public BigDecimal getRabat() {
        return rabat;
    }

    public Integer getWersjaRegulaminu() {
        return wersjaRegulaminu;
    }

    public void setWersjaRegulaminu(Integer wersjaRegulaminu) {
        this.wersjaRegulaminu = wersjaRegulaminu;
    }

    public StatusKonkursu getStatus() {
        return status;
    }

    public void setStatus(StatusKonkursu status) {
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    public Harmonogram getHarmonogram() {
        return harmonogram;
    }

    public void setHarmonogram(Harmonogram harmonogram) {
        if (this.harmonogram == harmonogram) {
            return;
        }

        Harmonogram previous = this.harmonogram;
        this.harmonogram = null;
        if (previous != null) {
            previous.setKonkursInternal(null);
        }

        this.harmonogram = harmonogram;
        if (harmonogram != null) {
            harmonogram.setKonkursInternal(this);
        }
    }

    void setHarmonogramInternal(Harmonogram harmonogram) {
        this.harmonogram = harmonogram;
    }

    public Set<Kategoria> getKategorie() {
        return Collections.unmodifiableSet(kategorie);
    }

    public void addKategoria(Kategoria kategoria) {
        Objects.requireNonNull(kategoria, "kategoria cannot be null");
        if (kategorie.add(kategoria)) {
            kategoria.addKonkursInternal(this);
        }
    }

    public void removeKategoria(Kategoria kategoria) {
        if (kategoria != null && kategorie.remove(kategoria)) {
            kategoria.removeKonkursInternal(this);
        }
    }

    void addKategoriaInternal(Kategoria kategoria) {
        kategorie.add(kategoria);
    }

    void removeKategoriaInternal(Kategoria kategoria) {
        kategorie.remove(kategoria);
    }

    public Set<Zgloszenie> getZgloszenia() {
        return Collections.unmodifiableSet(zgloszenia);
    }

    public Zgloszenie createZgloszenie(
        Projekt projekt,
        Organizacja aplikant,
        Integer numerAplikacji,
        LocalDate dataWprowadzeniaDoSystemu,
        StatusZgloszenia status,
        Waluta waluta
    ) {
        return new Zgloszenie(
            numerAplikacji,
            dataWprowadzeniaDoSystemu,
            status,
            waluta,
            this,
            projekt,
            aplikant
        );
    }

    public void removeZgloszenie(Zgloszenie zgloszenie) {
        if (zgloszenie != null && zgloszenia.contains(zgloszenie)) {
            zgloszenie.detach();
        }
    }

    void addZgloszenieInternal(Zgloszenie zgloszenie) {
        validateZgloszenieUniqueness(zgloszenie);
        zgloszenia.add(zgloszenie);
    }

    void removeZgloszenieInternal(Zgloszenie zgloszenie) {
        zgloszenia.remove(zgloszenie);
    }

    public Set<Projekt> getProjekty() {
        Set<Projekt> projekty = new LinkedHashSet<>();
        for (Zgloszenie zgloszenie : zgloszenia) {
            if (zgloszenie.getProjekt() != null) {
                projekty.add(zgloszenie.getProjekt());
            }
        }
        return Collections.unmodifiableSet(projekty);
    }

    public Set<CzlonekJury> getJury() {
        return Collections.unmodifiableSet(jury);
    }

    public void addCzlonekJury(CzlonekJury czlonekJury) {
        Objects.requireNonNull(czlonekJury, "czlonekJury cannot be null");
        if (jury.add(czlonekJury)) {
            czlonekJury.addKonkursJuryInternal(this);
        }
    }

    public void removeCzlonekJury(CzlonekJury czlonekJury) {
        if (czlonekJury != null && jury.remove(czlonekJury)) {
            if (przewodniczacy == czlonekJury) {
                setPrzewodniczacy(null);
            }
            czlonekJury.removeKonkursJuryInternal(this);
        }
    }

    void addCzlonekJuryInternal(CzlonekJury czlonekJury) {
        jury.add(czlonekJury);
    }

    void removeCzlonekJuryInternal(CzlonekJury czlonekJury) {
        jury.remove(czlonekJury);
        if (przewodniczacy == czlonekJury) {
            przewodniczacy = null;
        }
    }

    public CzlonekJury getPrzewodniczacy() {
        return przewodniczacy;
    }

    public void setPrzewodniczacy(CzlonekJury przewodniczacy) {
        if (this.przewodniczacy == przewodniczacy) {
            return;
        }

        CzlonekJury previous = this.przewodniczacy;
        this.przewodniczacy = null;
        if (previous != null) {
            previous.setZarzadzanyKonkursInternal(null);
        }

        if (przewodniczacy != null) {
            addCzlonekJury(przewodniczacy);
            Konkurs previousManagedContest = przewodniczacy.getZarzadzanyKonkurs();
            if (previousManagedContest != null && previousManagedContest != this) {
                previousManagedContest.setPrzewodniczacy(null);
            }
            przewodniczacy.setZarzadzanyKonkursInternal(this);
            this.przewodniczacy = przewodniczacy;
        }
    }

    void setPrzewodniczacyInternal(CzlonekJury przewodniczacy) {
        this.przewodniczacy = przewodniczacy;
        if (przewodniczacy != null) {
            jury.add(przewodniczacy);
        }
    }

    private void validateZgloszenieUniqueness(Zgloszenie candidate) {
        for (Zgloszenie existing : zgloszenia) {
            if (existing == candidate) {
                continue;
            }
            if (existing.getProjekt() != null && existing.getProjekt() == candidate.getProjekt()) {
                throw new IllegalArgumentException("Projekt moze miec tylko jedno zgloszenie w danym konkursie");
            }
        }
    }
}
