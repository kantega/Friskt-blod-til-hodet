package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PeriodeRepository extends JpaRepository<Periode, Long> {

    @Query("select p from Periode p where p.stopdato = (select max(pp.stopdato) from Periode pp)")
    Periode findLatestPeriode();
}
