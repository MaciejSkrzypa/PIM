package pl.edu.pwr.abis.domain.konkurs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The qualified assessor relations are stored per contest because the same project can take part in
 * multiple contests through the association class Zgloszenie.
 */
public class Projekt {

    private final Integer id;
    private final String nazwa;
    private final Integer czasTrwania;
    private Tytul uzyskanyTytul;
    private final List<String> podwykonawcy;
    private final Integer liczbaCzlonkow;
    private final Set<Zgloszenie> zgloszenia = new LinkedHashSet<>();
    private final Set<Kategoria> kategorie = new LinkedHashSet<>();
    private Organizacja sponsor;
    private WizytaStudyjna wizytaStudyjna;
    private RaportAplikacyjny raportAplikacyjny;
    private OcenaWstepna ocenaWstepna;
    private OcenaKoncowa ocenaKoncowa;
    private final Set<OcenaIndywidualna> ocenyIndywidualne = new LinkedHashSet<>();
    private final Map<Konkurs, Set<Asesor>> zespolyAsesorow = new LinkedHashMap<>();
    private final Map<Konkurs, Asesor> asesorzyWiodacy = new LinkedHashMap<>();

    public Projekt(
        Integer id,
        String nazwa,
        Integer czasTrwania,
        List<String> podwykonawcy,
        Integer liczbaCzlonkow
    ) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.nazwa = Uzytkownik.requireText(nazwa, "nazwa");
        this.czasTrwania = Objects.requireNonNull(czasTrwania, "czasTrwania cannot be null");
        this.podwykonawcy = requireNonEmptyTexts(podwykonawcy, "podwykonawcy");
        this.liczbaCzlonkow = Objects.requireNonNull(liczbaCzlonkow, "liczbaCzlonkow cannot be null");
    }

    public Integer getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Integer getCzasTrwania() {
        return czasTrwania;
    }

    public Tytul getUzyskanyTytul() {
        return uzyskanyTytul;
    }

    public void setUzyskanyTytul(Tytul uzyskanyTytul) {
        this.uzyskanyTytul = uzyskanyTytul;
    }

    public List<String> getPodwykonawcy() {
        return Collections.unmodifiableList(podwykonawcy);
    }

    public Integer getLiczbaCzlonkow() {
        return liczbaCzlonkow;
    }

    public Set<Zgloszenie> getZgloszenia() {
        return Collections.unmodifiableSet(zgloszenia);
    }

    public Zgloszenie addKonkurs(
        Konkurs konkurs,
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
            konkurs,
            this,
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
        if (zgloszenia.remove(zgloszenie) && zgloszenie.getKonkurs() != null && !uczestniczyWKonkursie(zgloszenie.getKonkurs())) {
            clearAsesorzyForKonkurs(zgloszenie.getKonkurs());
            removeCategoriesWithoutSharedContest();
        }
    }

    public Set<Konkurs> getKonkursy() {
        Set<Konkurs> konkursy = new LinkedHashSet<>();
        for (Zgloszenie zgloszenie : zgloszenia) {
            if (zgloszenie.getKonkurs() != null) {
                konkursy.add(zgloszenie.getKonkurs());
            }
        }
        return Collections.unmodifiableSet(konkursy);
    }

    public boolean uczestniczyWKonkursie(Konkurs konkurs) {
        for (Zgloszenie zgloszenie : zgloszenia) {
            if (zgloszenie.getKonkurs() == konkurs) {
                return true;
            }
        }
        return false;
    }

    public Set<Kategoria> getKategorie() {
        return Collections.unmodifiableSet(kategorie);
    }

    public void addKategoria(Kategoria kategoria) {
        Objects.requireNonNull(kategoria, "kategoria cannot be null");
        if (!kategorie.contains(kategoria)) {
            kategoria.addProjekt(this);
        }
    }

    public void removeKategoria(Kategoria kategoria) {
        if (kategoria != null && kategorie.remove(kategoria)) {
            kategoria.removeProjektInternal(this);
        }
    }

    void addKategoriaInternal(Kategoria kategoria) {
        kategorie.add(kategoria);
    }

    void removeKategoriaInternal(Kategoria kategoria) {
        kategorie.remove(kategoria);
    }

    public Organizacja getSponsor() {
        return sponsor;
    }

    public void setSponsor(Organizacja sponsor) {
        if (this.sponsor == sponsor) {
            return;
        }

        Organizacja previous = this.sponsor;
        this.sponsor = null;
        if (previous != null) {
            previous.removeSponsorowanyProjektInternal(this);
        }

        this.sponsor = sponsor;
        if (sponsor != null) {
            sponsor.addSponsorowanyProjektInternal(this);
        }
    }

    void setSponsorInternal(Organizacja sponsor) {
        this.sponsor = sponsor;
    }

    public WizytaStudyjna getWizytaStudyjna() {
        return wizytaStudyjna;
    }

    public void setWizytaStudyjna(WizytaStudyjna wizytaStudyjna) {
        if (this.wizytaStudyjna == wizytaStudyjna) {
            return;
        }

        WizytaStudyjna previous = this.wizytaStudyjna;
        this.wizytaStudyjna = null;
        if (previous != null) {
            previous.setProjektInternal(null);
        }

        this.wizytaStudyjna = wizytaStudyjna;
        if (wizytaStudyjna != null) {
            wizytaStudyjna.setProjektInternal(this);
        }
    }

    void setWizytaStudyjnaInternal(WizytaStudyjna wizytaStudyjna) {
        this.wizytaStudyjna = wizytaStudyjna;
    }

    public RaportAplikacyjny getRaportAplikacyjny() {
        return raportAplikacyjny;
    }

    public void setRaportAplikacyjny(RaportAplikacyjny raportAplikacyjny) {
        if (this.raportAplikacyjny == raportAplikacyjny) {
            return;
        }

        RaportAplikacyjny previous = this.raportAplikacyjny;
        this.raportAplikacyjny = null;
        if (previous != null) {
            previous.setProjektInternal(null);
        }

        this.raportAplikacyjny = raportAplikacyjny;
        if (raportAplikacyjny != null) {
            raportAplikacyjny.setProjektInternal(this);
        }
    }

    void setRaportAplikacyjnyInternal(RaportAplikacyjny raportAplikacyjny) {
        this.raportAplikacyjny = raportAplikacyjny;
    }

    public OcenaWstepna getOcenaWstepna() {
        return ocenaWstepna;
    }

    public void setOcenaWstepna(OcenaWstepna ocenaWstepna) {
        if (this.ocenaWstepna == ocenaWstepna) {
            return;
        }

        OcenaWstepna previous = this.ocenaWstepna;
        this.ocenaWstepna = null;
        if (previous != null) {
            previous.setProjektInternal(null);
        }

        this.ocenaWstepna = ocenaWstepna;
        if (ocenaWstepna != null) {
            ocenaWstepna.setProjektInternal(this);
        }
    }

    void setOcenaWstepnaInternal(OcenaWstepna ocenaWstepna) {
        this.ocenaWstepna = ocenaWstepna;
    }

    public OcenaKoncowa getOcenaKoncowa() {
        return ocenaKoncowa;
    }

    public void setOcenaKoncowa(OcenaKoncowa ocenaKoncowa) {
        if (this.ocenaKoncowa == ocenaKoncowa) {
            return;
        }

        OcenaKoncowa previous = this.ocenaKoncowa;
        this.ocenaKoncowa = null;
        if (previous != null) {
            previous.setProjektInternal(null);
        }

        this.ocenaKoncowa = ocenaKoncowa;
        if (ocenaKoncowa != null) {
            ocenaKoncowa.setProjektInternal(this);
        }
    }

    void setOcenaKoncowaInternal(OcenaKoncowa ocenaKoncowa) {
        this.ocenaKoncowa = ocenaKoncowa;
    }

    public Set<OcenaIndywidualna> getOcenyIndywidualne() {
        return Collections.unmodifiableSet(ocenyIndywidualne);
    }

    void addOcenaIndywidualnaInternal(OcenaIndywidualna ocenaIndywidualna) {
        ocenyIndywidualne.add(ocenaIndywidualna);
    }

    void removeOcenaIndywidualnaInternal(OcenaIndywidualna ocenaIndywidualna) {
        ocenyIndywidualne.remove(ocenaIndywidualna);
    }

    public Set<Asesor> getZespolAsesorow(Konkurs konkurs) {
        Set<Asesor> asesorzy = zespolyAsesorow.get(konkurs);
        if (asesorzy == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(asesorzy);
    }

    public void addAsesor(Konkurs konkurs, Asesor asesor) {
        Objects.requireNonNull(konkurs, "konkurs cannot be null");
        Objects.requireNonNull(asesor, "asesor cannot be null");
        ensureParticipationInContest(konkurs);

        Set<Asesor> asesorzy = zespolyAsesorow.get(konkurs);
        if (asesorzy != null && asesorzy.contains(asesor)) {
            return;
        }

        if (asesorzy == null) {
            asesorzy = new LinkedHashSet<>();
            zespolyAsesorow.put(konkurs, asesorzy);
        }

        if (!asesorzy.contains(asesor)) {
            asesor.addProjektDoOcenyInternal(konkurs, this);
            asesorzy.add(asesor);
        }
    }

    public void removeAsesor(Konkurs konkurs, Asesor asesor) {
        if (konkurs == null || asesor == null) {
            return;
        }

        if (Objects.equals(asesorzyWiodacy.get(konkurs), asesor)) {
            setAsesorWiodacy(konkurs, null);
        }

        Set<Asesor> asesorzy = zespolyAsesorow.get(konkurs);
        if (asesorzy != null && asesorzy.remove(asesor)) {
            asesor.removeProjektDoOcenyInternal(konkurs, this);
            if (asesorzy.isEmpty()) {
                zespolyAsesorow.remove(konkurs);
            }
            removeOcenyIndywidualne(konkurs, asesor);
        }
    }

    public Asesor getAsesorWiodacy(Konkurs konkurs) {
        return asesorzyWiodacy.get(konkurs);
    }

    public void setAsesorWiodacy(Konkurs konkurs, Asesor asesor) {
        Objects.requireNonNull(konkurs, "konkurs cannot be null");
        ensureParticipationInContest(konkurs);

        Asesor previous = asesorzyWiodacy.get(konkurs);
        if (previous == asesor) {
            return;
        }

        if (previous != null) {
            asesorzyWiodacy.remove(konkurs);
            previous.removeProwadzonyProjektInternal(konkurs, this);
        }

        if (asesor != null) {
            addAsesor(konkurs, asesor);
            asesor.addProwadzonyProjektInternal(konkurs, this);
            asesorzyWiodacy.put(konkurs, asesor);
        }
    }

    void setAsesorWiodacyInternal(Konkurs konkurs, Asesor asesor) {
        if (asesor == null) {
            asesorzyWiodacy.remove(konkurs);
        } else {
            asesorzyWiodacy.put(konkurs, asesor);
        }
    }

    private void validateZgloszenieUniqueness(Zgloszenie candidate) {
        for (Zgloszenie existing : zgloszenia) {
            if (existing == candidate) {
                continue;
            }
            if (existing.getKonkurs() != null && existing.getKonkurs() == candidate.getKonkurs()) {
                throw new IllegalArgumentException("Projekt moze miec tylko jedno zgloszenie do danego konkursu");
            }
        }
    }

    private void ensureParticipationInContest(Konkurs konkurs) {
        if (!uczestniczyWKonkursie(konkurs)) {
            throw new IllegalArgumentException("Projekt nie bierze udzialu w podanym konkursie");
        }
    }

    private void clearAsesorzyForKonkurs(Konkurs konkurs) {
        Asesor asesorWiodacy = asesorzyWiodacy.get(konkurs);
        if (asesorWiodacy != null) {
            setAsesorWiodacy(konkurs, null);
        }

        Set<Asesor> asesorzy = new LinkedHashSet<>(getZespolAsesorow(konkurs));
        for (Asesor asesor : asesorzy) {
            removeAsesor(konkurs, asesor);
        }
    }

    private void removeCategoriesWithoutSharedContest() {
        Set<Kategoria> invalidCategories = new LinkedHashSet<>();
        for (Kategoria kategoria : kategorie) {
            boolean sharedContestFound = false;
            for (Konkurs konkurs : getKonkursy()) {
                if (kategoria.getKonkursy().contains(konkurs)) {
                    sharedContestFound = true;
                    break;
                }
            }
            if (!sharedContestFound && !getKonkursy().isEmpty()) {
                invalidCategories.add(kategoria);
            }
        }

        for (Kategoria kategoria : invalidCategories) {
            removeKategoria(kategoria);
        }
    }

    private void removeOcenyIndywidualne(Konkurs konkurs, Asesor asesor) {
        Set<OcenaIndywidualna> doUsuniecia = new LinkedHashSet<>();
        for (OcenaIndywidualna ocenaIndywidualna : ocenyIndywidualne) {
            if (ocenaIndywidualna.getKonkurs() == konkurs && ocenaIndywidualna.getAsesor() == asesor) {
                doUsuniecia.add(ocenaIndywidualna);
            }
        }

        for (OcenaIndywidualna ocenaIndywidualna : doUsuniecia) {
            ocenaIndywidualna.detach();
        }
    }

    private static List<String> requireNonEmptyTexts(List<String> values, String fieldName) {
        Objects.requireNonNull(values, fieldName + " cannot be null");
        if (values.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }

        List<String> normalized = new ArrayList<>(values.size());
        for (String value : values) {
            normalized.add(Uzytkownik.requireText(value, fieldName));
        }
        return normalized;
    }
}
