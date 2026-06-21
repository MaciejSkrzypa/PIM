package pl.edu.pwr.abis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.abis.domain.konkurs.EkspertIPMA;

@Repository
public interface EkspertIPMARepository extends JpaRepository<EkspertIPMA, Long> {}
