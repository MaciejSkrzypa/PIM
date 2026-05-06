package pl.edu.pwr.abis.domain.konkurs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "konkurs_admin")
@Getter
@Setter
@DiscriminatorValue("Admin")
public class Admin extends Uzytkownik {}
