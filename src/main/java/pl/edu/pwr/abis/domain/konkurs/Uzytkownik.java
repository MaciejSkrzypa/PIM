package pl.edu.pwr.abis.domain.konkurs;

import java.util.Objects;

/**
 * The generalization set in the diagram is {complete, disjoint}, therefore the base class is
 * abstract and every concrete user must be represented by exactly one subclass.
 */
public abstract class Uzytkownik {

    private final String login;
    private String haslo;

    protected Uzytkownik(String login, String haslo) {
        this.login = requireText(login, "login");
        this.haslo = requireText(haslo, "haslo");
    }

    public String getLogin() {
        return login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = requireText(haslo, "haslo");
    }

    protected static String requireText(String value, String fieldName) {
        String normalized = Objects.requireNonNull(value, fieldName + " cannot be null").trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return normalized;
    }
}
