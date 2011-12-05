package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Gruppe;
import no.kantega.frisktblodtilhodet.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByAvdeling(Gruppe gruppe);

    Person findByUsername(String username);
}
