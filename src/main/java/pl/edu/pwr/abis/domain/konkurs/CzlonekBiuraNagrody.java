package pl.edu.pwr.abis.domain.konkurs;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class CzlonekBiuraNagrody extends Uzytkownik {

    private BiuroNagrody biuroNagrody;
    private final Set<EkspertIPMA> aktualniEksperci = new LinkedHashSet<>();

    public CzlonekBiuraNagrody(String login, String haslo) {
        super(login, haslo);
        setBiuroNagrody(BiuroNagrody.getInstance());
    }

    public BiuroNagrody getBiuroNagrody() {
        return biuroNagrody;
    }

    public void setBiuroNagrody(BiuroNagrody biuroNagrody) {
        if (this.biuroNagrody == biuroNagrody) {
            return;
        }

        BiuroNagrody previous = this.biuroNagrody;
        this.biuroNagrody = null;
        if (previous != null) {
            previous.removeCzlonekInternal(this);
        }

        this.biuroNagrody = biuroNagrody;
        if (biuroNagrody != null) {
            biuroNagrody.addCzlonekInternal(this);
        }
    }

    void setBiuroNagrodyInternal(BiuroNagrody biuroNagrody) {
        this.biuroNagrody = biuroNagrody;
    }

    public Set<EkspertIPMA> getAktualniEksperci() {
        return Collections.unmodifiableSet(aktualniEksperci);
    }

    public void addAktualnyEkspert(EkspertIPMA ekspertIPMA) {
        Objects.requireNonNull(ekspertIPMA, "ekspertIPMA cannot be null");
        if (aktualniEksperci.add(ekspertIPMA)) {
            ekspertIPMA.addCzlonekBiuraNagrodyInternal(this);
        }
    }

    public void removeAktualnyEkspert(EkspertIPMA ekspertIPMA) {
        if (ekspertIPMA != null && aktualniEksperci.remove(ekspertIPMA)) {
            ekspertIPMA.removeCzlonekBiuraNagrodyInternal(this);
        }
    }

    void addAktualnyEkspertInternal(EkspertIPMA ekspertIPMA) {
        aktualniEksperci.add(ekspertIPMA);
    }

    void removeAktualnyEkspertInternal(EkspertIPMA ekspertIPMA) {
        aktualniEksperci.remove(ekspertIPMA);
    }
}
