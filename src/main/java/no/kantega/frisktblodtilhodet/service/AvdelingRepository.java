package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Avdeling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvdelingRepository extends JpaRepository<Avdeling, Long> {
}
