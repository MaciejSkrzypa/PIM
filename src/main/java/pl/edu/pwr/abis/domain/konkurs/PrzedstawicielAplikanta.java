package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_przedstawicielaplikanta")
@Getter
@Setter
@DiscriminatorValue("PrzedstawicielAplikanta")
public class PrzedstawicielAplikanta extends Uzytkownik {

    @ManyToOne(optional = true)
    private Organizacja organizacja;
}
