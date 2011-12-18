package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.model.UtfortAktivitet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UtfortAktivitetRepository extends JpaRepository<UtfortAktivitet, Long> {
    List<UtfortAktivitet> findByPerson(Person person);

    /*@Query("select ua.aktivitet, count(ua) from UtfortAktivitet ua where ua.person = :person group by ua.aktivitet")
    Map<Aktivitet, Long> findAktivtetAndCountByPerson(@Param("person") Person person);*/

    @Query("select sum(ua.poeng) from UtfortAktivitet ua where ua.aktivitet = :aktivitet and ua.person = :person")
    Long getCountByAktivitetAndPerson(@Param("aktivitet") Aktivitet aktivitet, @Param("person") Person person);
}
