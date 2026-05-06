package pl.edu.pwr.abis.domain.konkurs;

public class PrzedstawicielAplikanta extends Uzytkownik {

    private Organizacja organizacja;

    public PrzedstawicielAplikanta(String login, String haslo) {
        super(login, haslo);
    }

    public Organizacja getOrganizacja() {
        return organizacja;
    }

    public void setOrganizacja(Organizacja organizacja) {
        if (this.organizacja == organizacja) {
            return;
        }

        Organizacja previous = this.organizacja;
        this.organizacja = null;
        if (previous != null) {
            previous.removePrzedstawicielAplikantaInternal(this);
        }

        this.organizacja = organizacja;
        if (organizacja != null) {
            organizacja.addPrzedstawicielAplikantaInternal(this);
        }
    }

    void setOrganizacjaInternal(Organizacja organizacja) {
        this.organizacja = organizacja;
    }
}
