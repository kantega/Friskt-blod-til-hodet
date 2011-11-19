package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AktivitetRepository extends JpaRepository<Aktivitet, Long> {
}
