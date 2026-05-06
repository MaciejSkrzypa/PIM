package pl.edu.pwr.abis.domain.konkurs;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Kategoria {

    private final String nazwa;
    private final Set<Konkurs> konkursy = new LinkedHashSet<>();
    private final Set<Projekt> projekty = new LinkedHashSet<>();

    public Kategoria(String nazwa) {
        this.nazwa = Uzytkownik.requireText(nazwa, "nazwa");
    }

    public String getNazwa() {
        return nazwa;
    }

    public Set<Konkurs> getKonkursy() {
        return Collections.unmodifiableSet(konkursy);
    }

    public Set<Projekt> getProjekty() {
        return Collections.unmodifiableSet(projekty);
    }

    public void addKonkurs(Konkurs konkurs) {
        Objects.requireNonNull(konkurs, "konkurs cannot be null");
        if (konkursy.add(konkurs)) {
            konkurs.addKategoriaInternal(this);
        }
    }

    public void removeKonkurs(Konkurs konkurs) {
        if (konkurs != null && konkursy.remove(konkurs)) {
            konkurs.removeKategoriaInternal(this);
            removeProjectsViolatingSubset();
        }
    }

    public void addProjekt(Projekt projekt) {
        Objects.requireNonNull(projekt, "projekt cannot be null");
        validateSubsetConstraint(projekt);
        if (projekty.add(projekt)) {
            projekt.addKategoriaInternal(this);
        }
    }

    public void removeProjekt(Projekt projekt) {
        if (projekt != null && projekty.remove(projekt)) {
            projekt.removeKategoriaInternal(this);
        }
    }

    void addKonkursInternal(Konkurs konkurs) {
        konkursy.add(konkurs);
    }

    void removeKonkursInternal(Konkurs konkurs) {
        konkursy.remove(konkurs);
    }

    void addProjektInternal(Projekt projekt) {
        projekty.add(projekt);
    }

    void removeProjektInternal(Projekt projekt) {
        projekty.remove(projekt);
    }

    private void validateSubsetConstraint(Projekt projekt) {
        if (konkursy.isEmpty() || projekt.getKonkursy().isEmpty()) {
            return;
        }

        for (Konkurs konkurs : projekt.getKonkursy()) {
            if (konkursy.contains(konkurs)) {
                return;
            }
        }

        throw new IllegalArgumentException(
            "Kategoria projektu musi nalezec do co najmniej jednego konkursu, w ktorym projekt bierze udzial"
        );
    }

    private void removeProjectsViolatingSubset() {
        Set<Projekt> invalidProjects = new LinkedHashSet<>();
        for (Projekt projekt : projekty) {
            boolean sharedContestFound = false;
            for (Konkurs konkurs : projekt.getKonkursy()) {
                if (konkursy.contains(konkurs)) {
                    sharedContestFound = true;
                    break;
                }
            }
            if (!sharedContestFound && !projekt.getKonkursy().isEmpty()) {
                invalidProjects.add(projekt);
            }
        }

        for (Projekt projekt : invalidProjects) {
            removeProjekt(projekt);
        }
    }
}
