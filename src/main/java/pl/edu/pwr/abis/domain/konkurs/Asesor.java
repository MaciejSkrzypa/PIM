package pl.edu.pwr.abis.domain.konkurs;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Asesor extends Uzytkownik {

    private final Map<Konkurs, Projekt> projektyDoOceny = new LinkedHashMap<>();
    private final Map<Konkurs, Projekt> prowadzoneProjekty = new LinkedHashMap<>();
    private final Set<OcenaIndywidualna> ocenyIndywidualne = new LinkedHashSet<>();
    private EkspertIPMA ekspertIPMA;

    public Asesor(String login, String haslo) {
        super(login, haslo);
    }

    public Map<Konkurs, Projekt> getProjektyDoOceny() {
        return Collections.unmodifiableMap(projektyDoOceny);
    }

    public void addProjektDoOceny(Konkurs konkurs, Projekt projekt) {
        if (konkurs == null || projekt == null) {
            throw new IllegalArgumentException("konkurs and projekt cannot be null");
        }
        projekt.addAsesor(konkurs, this);
    }

    public void removeProjektDoOceny(Konkurs konkurs) {
        Projekt projekt = projektyDoOceny.get(konkurs);
        if (projekt != null) {
            projekt.removeAsesor(konkurs, this);
        }
    }

    void addProjektDoOcenyInternal(Konkurs konkurs, Projekt projekt) {
        Projekt existing = projektyDoOceny.get(konkurs);
        if (existing != null && existing != projekt) {
            throw new IllegalArgumentException(
                "Asesor moze oceniac najwyzej jeden projekt w ramach danego konkursu"
            );
        }
        projektyDoOceny.put(konkurs, projekt);
    }

    void removeProjektDoOcenyInternal(Konkurs konkurs, Projekt projekt) {
        if (projektyDoOceny.get(konkurs) == projekt) {
            projektyDoOceny.remove(konkurs);
        }
        if (prowadzoneProjekty.get(konkurs) == projekt) {
            prowadzoneProjekty.remove(konkurs);
        }
    }

    public Map<Konkurs, Projekt> getProwadzoneProjekty() {
        return Collections.unmodifiableMap(prowadzoneProjekty);
    }

    public void setProwadzonyProjekt(Konkurs konkurs, Projekt projekt) {
        if (konkurs == null || projekt == null) {
            throw new IllegalArgumentException("konkurs and projekt cannot be null");
        }
        projekt.setAsesorWiodacy(konkurs, this);
    }

    public void clearProwadzonyProjekt(Konkurs konkurs) {
        Projekt projekt = prowadzoneProjekty.get(konkurs);
        if (projekt != null) {
            projekt.setAsesorWiodacy(konkurs, null);
        }
    }

    void addProwadzonyProjektInternal(Konkurs konkurs, Projekt projekt) {
        Projekt existing = prowadzoneProjekty.get(konkurs);
        if (existing != null && existing != projekt) {
            throw new IllegalArgumentException(
                "Asesor moze prowadzic najwyzej jeden projekt w ramach danego konkursu"
            );
        }
        addProjektDoOcenyInternal(konkurs, projekt);
        prowadzoneProjekty.put(konkurs, projekt);
    }

    void removeProwadzonyProjektInternal(Konkurs konkurs, Projekt projekt) {
        if (prowadzoneProjekty.get(konkurs) == projekt) {
            prowadzoneProjekty.remove(konkurs);
        }
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

    public EkspertIPMA getEkspertIPMA() {
        return ekspertIPMA;
    }

    public void setEkspertIPMA(EkspertIPMA ekspertIPMA) {
        if (this.ekspertIPMA == ekspertIPMA) {
            return;
        }

        EkspertIPMA previous = this.ekspertIPMA;
        this.ekspertIPMA = null;
        if (previous != null) {
            previous.setAsesorInternal(null);
        }

        this.ekspertIPMA = ekspertIPMA;
        if (ekspertIPMA != null) {
            Asesor previousAsesor = ekspertIPMA.getAsesor();
            if (previousAsesor != null && previousAsesor != this) {
                previousAsesor.setEkspertIPMA(null);
            }
            ekspertIPMA.setAsesorInternal(this);
        }
    }

    void setEkspertIPMAInternal(EkspertIPMA ekspertIPMA) {
        this.ekspertIPMA = ekspertIPMA;
    }
}
