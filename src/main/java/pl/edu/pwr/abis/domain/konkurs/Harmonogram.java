package pl.edu.pwr.abis.domain.konkurs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Harmonogram {

    private LocalDate dataSzkoleniaPE;
    private List<LocalDate> dataWebinarium = new ArrayList<>();
    private LocalDate maxDataOcenyIndywidualnej;
    private LocalDate dataSpotkaniaWirtualnego;
    private List<LocalDate> datyRaportowJury = new ArrayList<>();
    private List<LocalDate> datySpotkanJury = new ArrayList<>();
    private List<String> miejscaSpotkanJury = new ArrayList<>();
    private LocalDate maxDataWizytyStudyjnej;
    private LocalDate dataRaportuKoncowego;
    private LocalDate dataOgloszeniaFinalistow;
    private LocalDate dataOgloszeniaLaureatow;
    private Konkurs konkurs;

    public Harmonogram() {}

    public Harmonogram(Konkurs konkurs) {
        setKonkurs(konkurs);
    }

    public LocalDate getDataSzkoleniaPE() {
        return dataSzkoleniaPE;
    }

    public void setDataSzkoleniaPE(LocalDate dataSzkoleniaPE) {
        this.dataSzkoleniaPE = dataSzkoleniaPE;
    }

    public List<LocalDate> getDataWebinarium() {
        return Collections.unmodifiableList(dataWebinarium);
    }

    public void setDataWebinarium(List<LocalDate> dataWebinarium) {
        this.dataWebinarium = copyDates(dataWebinarium, 4, "dataWebinarium");
    }

    public LocalDate getMaxDataOcenyIndywidualnej() {
        return maxDataOcenyIndywidualnej;
    }

    public void setMaxDataOcenyIndywidualnej(LocalDate maxDataOcenyIndywidualnej) {
        this.maxDataOcenyIndywidualnej = maxDataOcenyIndywidualnej;
    }

    public LocalDate getDataSpotkaniaWirtualnego() {
        return dataSpotkaniaWirtualnego;
    }

    public void setDataSpotkaniaWirtualnego(LocalDate dataSpotkaniaWirtualnego) {
        this.dataSpotkaniaWirtualnego = dataSpotkaniaWirtualnego;
    }

    public List<LocalDate> getDatyRaportowJury() {
        return Collections.unmodifiableList(datyRaportowJury);
    }

    public void setDatyRaportowJury(List<LocalDate> datyRaportowJury) {
        this.datyRaportowJury = copyDates(datyRaportowJury, 2, "datyRaportowJury");
    }

    public List<LocalDate> getDatySpotkanJury() {
        return Collections.unmodifiableList(datySpotkanJury);
    }

    public void setDatySpotkanJury(List<LocalDate> datySpotkanJury) {
        this.datySpotkanJury = copyDates(datySpotkanJury, 2, "datySpotkanJury");
    }

    public List<String> getMiejscaSpotkanJury() {
        return Collections.unmodifiableList(miejscaSpotkanJury);
    }

    public void setMiejscaSpotkanJury(List<String> miejscaSpotkanJury) {
        this.miejscaSpotkanJury = copyTexts(miejscaSpotkanJury, 2, "miejscaSpotkanJury");
    }

    public LocalDate getMaxDataWizytyStudyjnej() {
        return maxDataWizytyStudyjnej;
    }

    public void setMaxDataWizytyStudyjnej(LocalDate maxDataWizytyStudyjnej) {
        this.maxDataWizytyStudyjnej = maxDataWizytyStudyjnej;
    }

    public LocalDate getDataRaportuKoncowego() {
        return dataRaportuKoncowego;
    }

    public void setDataRaportuKoncowego(LocalDate dataRaportuKoncowego) {
        this.dataRaportuKoncowego = dataRaportuKoncowego;
    }

    public LocalDate getDataOgloszeniaFinalistow() {
        return dataOgloszeniaFinalistow;
    }

    public void setDataOgloszeniaFinalistow(LocalDate dataOgloszeniaFinalistow) {
        this.dataOgloszeniaFinalistow = dataOgloszeniaFinalistow;
    }

    public LocalDate getDataOgloszeniaLaureatow() {
        return dataOgloszeniaLaureatow;
    }

    public void setDataOgloszeniaLaureatow(LocalDate dataOgloszeniaLaureatow) {
        this.dataOgloszeniaLaureatow = dataOgloszeniaLaureatow;
    }

    public Konkurs getKonkurs() {
        return konkurs;
    }

    public void setKonkurs(Konkurs konkurs) {
        if (this.konkurs == konkurs) {
            return;
        }

        Konkurs previous = this.konkurs;
        this.konkurs = null;
        if (previous != null) {
            previous.setHarmonogramInternal(null);
        }

        this.konkurs = konkurs;
        if (konkurs != null) {
            konkurs.setHarmonogramInternal(this);
        }
    }

    void setKonkursInternal(Konkurs konkurs) {
        this.konkurs = konkurs;
    }

    private static List<LocalDate> copyDates(List<LocalDate> values, int maxSize, String fieldName) {
        Objects.requireNonNull(values, fieldName + " cannot be null");
        if (values.size() > maxSize) {
            throw new IllegalArgumentException(fieldName + " exceeds max size " + maxSize);
        }
        return new ArrayList<>(values);
    }

    private static List<String> copyTexts(List<String> values, int maxSize, String fieldName) {
        Objects.requireNonNull(values, fieldName + " cannot be null");
        if (values.size() > maxSize) {
            throw new IllegalArgumentException(fieldName + " exceeds max size " + maxSize);
        }
        List<String> copied = new ArrayList<>(values.size());
        for (String value : values) {
            copied.add(Uzytkownik.requireText(value, fieldName));
        }
        return copied;
    }
}
