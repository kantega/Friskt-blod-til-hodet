package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Gruppe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GruppeRepository extends JpaRepository<Gruppe, Long> {
}
