package pl.edu.pwr.abis.domain.konkurs;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CzlonekJury extends Uzytkownik {

    private String imie;
    private String nazwisko;
    private final Set<Konkurs> konkursyJury = new LinkedHashSet<>();
    private Konkurs zarzadzanyKonkurs;

    public CzlonekJury(String login, String haslo, String imie, String nazwisko) {
        super(login, haslo);
        this.imie = requireText(imie, "imie");
        this.nazwisko = requireText(nazwisko, "nazwisko");
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = requireText(imie, "imie");
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = requireText(nazwisko, "nazwisko");
    }

    public Set<Konkurs> getKonkursyJury() {
        return Collections.unmodifiableSet(konkursyJury);
    }

    public void addKonkursJury(Konkurs konkurs) {
        if (konkurs != null) {
            konkurs.addCzlonekJury(this);
        }
    }

    public void removeKonkursJury(Konkurs konkurs) {
        if (konkurs != null) {
            konkurs.removeCzlonekJury(this);
        }
    }

    void addKonkursJuryInternal(Konkurs konkurs) {
        konkursyJury.add(konkurs);
    }

    void removeKonkursJuryInternal(Konkurs konkurs) {
        konkursyJury.remove(konkurs);
        if (zarzadzanyKonkurs == konkurs) {
            zarzadzanyKonkurs = null;
        }
    }

    public Konkurs getZarzadzanyKonkurs() {
        return zarzadzanyKonkurs;
    }

    public void setZarzadzanyKonkurs(Konkurs konkurs) {
        if (this.zarzadzanyKonkurs == konkurs) {
            return;
        }

        Konkurs previous = this.zarzadzanyKonkurs;
        this.zarzadzanyKonkurs = null;
        if (previous != null) {
            previous.setPrzewodniczacyInternal(null);
        }

        if (konkurs != null) {
            konkurs.setPrzewodniczacy(this);
        }
    }

    void setZarzadzanyKonkursInternal(Konkurs konkurs) {
        this.zarzadzanyKonkurs = konkurs;
        if (konkurs != null) {
            konkursyJury.add(konkurs);
        }
    }
}
