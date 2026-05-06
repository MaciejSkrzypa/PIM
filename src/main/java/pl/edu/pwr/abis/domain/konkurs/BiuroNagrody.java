package pl.edu.pwr.abis.domain.konkurs;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public final class BiuroNagrody {

    private static final BiuroNagrody INSTANCE = new BiuroNagrody();

    private String adresKorespondencyjny;
    private String email;
    private final Set<CzlonekBiuraNagrody> czlonkowie = new LinkedHashSet<>();

    private BiuroNagrody() {}

    public static BiuroNagrody getInstance() {
        return INSTANCE;
    }

    public String getAdresKorespondencyjny() {
        return adresKorespondencyjny;
    }

    public void setAdresKorespondencyjny(String adresKorespondencyjny) {
        this.adresKorespondencyjny = Uzytkownik.requireText(
            adresKorespondencyjny,
            "adresKorespondencyjny"
        );
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Uzytkownik.requireText(email, "email");
    }

    public Set<CzlonekBiuraNagrody> getCzlonkowie() {
        return Collections.unmodifiableSet(czlonkowie);
    }

    public void addCzlonek(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        Objects.requireNonNull(czlonekBiuraNagrody, "czlonekBiuraNagrody cannot be null");
        if (czlonkowie.add(czlonekBiuraNagrody)) {
            czlonekBiuraNagrody.setBiuroNagrodyInternal(this);
        }
    }

    public void removeCzlonek(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        if (czlonekBiuraNagrody != null && czlonkowie.remove(czlonekBiuraNagrody)) {
            czlonekBiuraNagrody.setBiuroNagrodyInternal(null);
        }
    }

    void addCzlonekInternal(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        czlonkowie.add(czlonekBiuraNagrody);
    }

    void removeCzlonekInternal(CzlonekBiuraNagrody czlonekBiuraNagrody) {
        czlonkowie.remove(czlonekBiuraNagrody);
    }
}
