package pl.edu.pwr.abis.domain.konkurs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RaportAplikacyjny {

    private final List<String> wykazZalacznikow;
    private LocalDate dataZlozeniaFizycznego;
    private StatusRaportuAplikacyjnego status;
    private Projekt projekt;

    public RaportAplikacyjny(
        List<String> wykazZalacznikow,
        LocalDate dataZlozeniaFizycznego,
        StatusRaportuAplikacyjnego status
    ) {
        this.wykazZalacznikow = requireNonEmptyTexts(wykazZalacznikow, "wykazZalacznikow");
        this.dataZlozeniaFizycznego = Objects.requireNonNull(
            dataZlozeniaFizycznego,
            "dataZlozeniaFizycznego cannot be null"
        );
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    public List<String> getWykazZalacznikow() {
        return Collections.unmodifiableList(wykazZalacznikow);
    }

    public LocalDate getDataZlozeniaFizycznego() {
        return dataZlozeniaFizycznego;
    }

    public void setDataZlozeniaFizycznego(LocalDate dataZlozeniaFizycznego) {
        this.dataZlozeniaFizycznego = Objects.requireNonNull(
            dataZlozeniaFizycznego,
            "dataZlozeniaFizycznego cannot be null"
        );
    }

    public StatusRaportuAplikacyjnego getStatus() {
        return status;
    }

    public void setStatus(StatusRaportuAplikacyjnego status) {
        this.status = Objects.requireNonNull(status, "status cannot be null");
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
            previous.setRaportAplikacyjnyInternal(null);
        }

        this.projekt = projekt;
        if (projekt != null) {
            projekt.setRaportAplikacyjnyInternal(this);
        }
    }

    void setProjektInternal(Projekt projekt) {
        this.projekt = projekt;
    }

    private static List<String> requireNonEmptyTexts(List<String> values, String fieldName) {
        Objects.requireNonNull(values, fieldName + " cannot be null");
        if (values.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }

        List<String> copied = new ArrayList<>(values.size());
        for (String value : values) {
            copied.add(Uzytkownik.requireText(value, fieldName));
        }
        return copied;
    }
}
