package pl.edu.pwr.abis.domain.konkurs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WizytaStudyjna {

    private final String miejsce;
    private List<String> pytaniaOdJury = new ArrayList<>();
    private List<String> opracowanieOdpowiedzi = new ArrayList<>();
    private final LocalDate rzeczywistyTerminZlozeniaRaportu;
    private Projekt projekt;

    public WizytaStudyjna(
        String miejsce,
        List<String> pytaniaOdJury,
        List<String> opracowanieOdpowiedzi,
        LocalDate rzeczywistyTerminZlozeniaRaportu
    ) {
        this.miejsce = Uzytkownik.requireText(miejsce, "miejsce");
        setPytaniaOdJury(pytaniaOdJury);
        setOpracowanieOdpowiedzi(opracowanieOdpowiedzi);
        this.rzeczywistyTerminZlozeniaRaportu = Objects.requireNonNull(
            rzeczywistyTerminZlozeniaRaportu,
            "rzeczywistyTerminZlozeniaRaportu cannot be null"
        );
    }

    public String getMiejsce() {
        return miejsce;
    }

    public List<String> getPytaniaOdJury() {
        return Collections.unmodifiableList(pytaniaOdJury);
    }

    public void setPytaniaOdJury(List<String> pytaniaOdJury) {
        this.pytaniaOdJury = copyTexts(pytaniaOdJury, "pytaniaOdJury");
    }

    public List<String> getOpracowanieOdpowiedzi() {
        return Collections.unmodifiableList(opracowanieOdpowiedzi);
    }

    public void setOpracowanieOdpowiedzi(List<String> opracowanieOdpowiedzi) {
        this.opracowanieOdpowiedzi = copyTexts(opracowanieOdpowiedzi, "opracowanieOdpowiedzi");
    }

    public LocalDate getRzeczywistyTerminZlozeniaRaportu() {
        return rzeczywistyTerminZlozeniaRaportu;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        if (this.projekt == projekt) {
            return;
        }

        Projekt previous = this.projekt;
        this.projekt = null;
        if (previous != null) {
            previous.setWizytaStudyjnaInternal(null);
        }

        this.projekt = projekt;
        if (projekt != null) {
            projekt.setWizytaStudyjnaInternal(this);
        }
    }

    void setProjektInternal(Projekt projekt) {
        this.projekt = projekt;
    }

    private static List<String> copyTexts(List<String> values, String fieldName) {
        Objects.requireNonNull(values, fieldName + " cannot be null");
        List<String> copied = new ArrayList<>(values.size());
        for (String value : values) {
            copied.add(Uzytkownik.requireText(value, fieldName));
        }
        return copied;
    }
}
