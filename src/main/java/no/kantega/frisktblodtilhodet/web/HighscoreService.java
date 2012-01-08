package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.*;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.GruppeRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class HighscoreService {

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GruppeRepository gruppeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Map<Aktivitet, Integer> getAktivitetAndCountForPerson(Person person) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery();
        Root<UtfortAktivitet> from = cq.from(UtfortAktivitet.class);

        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Person> p = from.get(UtfortAktivitet_.person);
        Path<Aktivitet> aktivitet = from.get(UtfortAktivitet_.aktivitet);

        CriteriaQuery<Object> multiselect = cq.multiselect(sum, aktivitet).where(cb.equal(p, person)).orderBy(cb.desc(sum)).groupBy(aktivitet);

        TypedQuery<Object> query = entityManager.createQuery(multiselect);
        List<Object> resultList = query.getResultList();

        Map<Aktivitet, Integer> aktivitetAndCountByPerson = new LinkedHashMap<Aktivitet, Integer>();
        for (Object o : resultList) {
            Object[] result = (Object[]) o;
            aktivitetAndCountByPerson.put((Aktivitet)result[1], (Integer)result[0]);
        }

        return aktivitetAndCountByPerson;
    }

    public Map<Person, Integer> getPersonAndScore() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Person> person = from.get(UtfortAktivitet_.person);

        CriteriaQuery<Object> select = query.multiselect(sum, person).orderBy(cb.desc(sum)).groupBy(person);

        TypedQuery<Object> query1 = entityManager.createQuery(select);
        List<Object> resultList = query1.getResultList();

        Map<Person, Integer> personAndCount = new LinkedHashMap<Person, Integer>();
        for (Object o : resultList) {
            Object[] result = (Object[]) o;
            Integer count = (Integer) result[0];
            Person p = (Person) result[1];
            personAndCount.put(p, count);
        }
        return personAndCount;
    }

    public Map<Person, Long> getPersonsAndScoreForAktivitet(Aktivitet aktivitet) {
        Map<Person, Long> personAndCount = new HashMap<Person, Long>();
        for(final Person person : personRepository.findAll()){
            long count = utfortAktivitetRepository.getPoengByAktivitetAndPerson(aktivitet, person);
            personAndCount.put(person, count);
        }
        return personAndCount;
    }

    public Map<Person, Long> getPersonsAndScoreForGruppe(Gruppe gruppe) {
        Map<Person, Long> personAndScore = new HashMap<Person, Long>();
        for(Person person : gruppe.getPersons()){
            Long score = utfortAktivitetRepository.getPoengByPerson(person);
            personAndScore.put(person, score);
        }
        return personAndScore;
    }

    public Map<Person, Long> getPersonsAndScoreForGruppeAndAktivitet(Gruppe gruppe, Aktivitet aktivitet) {
        Map<Person, Long> personAndScore = new HashMap<Person, Long>();
        for(Person person : gruppe.getPersons()){
            Long score = utfortAktivitetRepository.getPoengByAktivitetAndPerson(aktivitet, person);
            personAndScore.put(person, score);
        }
        return personAndScore;
    }

    public Map<Gruppe, Long> getGrupperAndScoreForAktivitet(Aktivitet aktivitet) {
        Map<Gruppe, Long> gruppeAndScore = new HashMap<Gruppe, Long>();
        for(Gruppe gruppe : gruppeRepository.findAll()){
            Long score = utfortAktivitetRepository.getPoengByAktivitetAndGruppe(aktivitet, gruppe);
            gruppeAndScore.put(gruppe, score);
        }
        return gruppeAndScore;
    }

    public Map<Gruppe, Long> getGrupperAndScoreForAlle() {
        Map<Gruppe, Long> gruppeAndScore = new HashMap<Gruppe, Long>();
        for(Gruppe gruppe : gruppeRepository.findAll()){
            Long score = utfortAktivitetRepository.getPoengByGruppe(gruppe);
            gruppeAndScore.put(gruppe, score);
        }
        return gruppeAndScore;
    }
}
