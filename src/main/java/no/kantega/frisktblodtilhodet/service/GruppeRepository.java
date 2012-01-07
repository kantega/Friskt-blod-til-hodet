package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Gruppe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GruppeRepository extends JpaRepository<Gruppe, Long> {
    @Query("select gruppe from Gruppe gruppe where gruppe.undergrupper is empty")
    List<Gruppe> findAllLeafGrupper();
}
