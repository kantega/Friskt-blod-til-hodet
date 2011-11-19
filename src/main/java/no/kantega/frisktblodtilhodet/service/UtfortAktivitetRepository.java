package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.model.UtfortAktivitet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtfortAktivitetRepository extends JpaRepository<UtfortAktivitet, Long> {
    List<UtfortAktivitet> findByPerson(Person person);
}
