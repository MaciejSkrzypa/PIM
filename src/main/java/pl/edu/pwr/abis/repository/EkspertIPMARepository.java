package pl.edu.pwr.abis.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;

@Repository
public interface EkspertIPMARepository extends JpaRepository<EkspertIPMA, Long> {
    @Modifying
    @Query(
        """
        update konkurs_ekspertipma ekspertIPMA
        set ekspertIPMA.imie = :imie,
            ekspertIPMA.nazwisko = :nazwisko,
            ekspertIPMA.dataSzkolenia = :dataSzkolenia,
            ekspertIPMA.wymagajaceWeryfikacji = true
        where ekspertIPMA.id = :id
        """
    )
    int updateEkspertIPMA(
        @Param("id") Long id,
        @Param("imie") String imie,
        @Param("nazwisko") String nazwisko,
        @Param("dataSzkolenia") LocalDate dataSzkolenia
    );
}
