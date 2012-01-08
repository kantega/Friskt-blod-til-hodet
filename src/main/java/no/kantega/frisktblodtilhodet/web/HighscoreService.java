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

    public Map<Aktivitet, Long> getAktivitetAndCountForPerson(Person person) {
        List<Aktivitet> aktiviteter = aktivitetRepository.findAll();
        Map<Aktivitet, Long> aktivitetAndCountByPerson = new HashMap<Aktivitet, Long>();
        for (Aktivitet aktivitet : aktiviteter) {
            Long countByAktivitetAndPerson = utfortAktivitetRepository.getPoengByAktivitetAndPerson(aktivitet, person);
            if(countByAktivitetAndPerson == null) countByAktivitetAndPerson = 0L;
            aktivitetAndCountByPerson.put(aktivitet, countByAktivitetAndPerson);
        }
        return aktivitetAndCountByPerson;
    }

    public Map<Person, Integer> getPersonAndScore() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Person> person = from.get(UtfortAktivitet_.person);
        CriteriaQuery<Object> select = query.multiselect(sum, person);
        CriteriaQuery<Object> orderBy = select.orderBy(cb.desc(sum));
        CriteriaQuery<Object> groupBy = orderBy.groupBy(person);

        TypedQuery<Object> query1 = entityManager.createQuery(groupBy);
        List<Object> resultList = query1.getResultList();

        Map<Person, Integer> personAndCount = new HashMap<Person, Integer>();
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
