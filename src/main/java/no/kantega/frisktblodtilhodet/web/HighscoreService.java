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

        addNotPresentAktivitetAndZeroCount(aktivitetAndCountByPerson);
        return aktivitetAndCountByPerson;
    }

    private void addNotPresentAktivitetAndZeroCount(Map<Aktivitet, Integer> aktivitetAndCountByPerson) {
        for(Aktivitet aktivitet : aktivitetRepository.findAll()){
            boolean notPresent = !aktivitetAndCountByPerson.containsKey(aktivitet);
            if(notPresent){
                aktivitetAndCountByPerson.put(aktivitet, 0);
            }
        }
    }

    public Map<Person, Integer> getPersonAndScore() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Person> person = from.get(UtfortAktivitet_.person);

        CriteriaQuery<Object> select = query.multiselect(sum, person).orderBy(cb.desc(sum)).groupBy(person);

        return addResultToMap(select);
    }

    public Map<Person, Integer> getPersonsAndScoreForAktivitet(Aktivitet aktivitet) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Person> person = from.get(UtfortAktivitet_.person);
        Path<Aktivitet> aktivitetPath = from.get(UtfortAktivitet_.aktivitet);

        CriteriaQuery<Object> q = query.multiselect(sum, person).where(cb.equal(aktivitetPath, aktivitet)).orderBy(cb.desc(sum)).groupBy(person);
        return addResultToMap(q);
    }

    private Map<Person, Integer> addResultToMap(CriteriaQuery<Object> q) {
        TypedQuery<Object> query1 = entityManager.createQuery(q);
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

    public Map<Person, Integer> getPersonsAndScoreForGruppe(Gruppe gruppe) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Root<Person> personfrom = query.from(Person.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Person> person = from.get(UtfortAktivitet_.person);

        CriteriaQuery<Object> q = query.multiselect(sum, person).where(cb.equal(personfrom.get(Person_.gruppe), gruppe)).orderBy(cb.desc(sum)).groupBy(person);

        return addResultToMap(q);
    }

    public Map<Person, Integer> getPersonsAndScoreForGruppeAndAktivitet(Gruppe gruppe, Aktivitet aktivitet) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Root<Person> personfrom = query.from(Person.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Person> person = from.get(UtfortAktivitet_.person);

        Predicate and = cb.and(cb.equal(personfrom.get(Person_.gruppe), gruppe), cb.equal(from.get(UtfortAktivitet_.aktivitet), aktivitet));
        CriteriaQuery<Object> q = query.multiselect(sum, person).where(and).orderBy(cb.desc(sum)).groupBy(person);

        return addResultToMap(q);
    }

    public Map<Gruppe, Integer> getGrupperAndScoreForAktivitet(Aktivitet aktivitet) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Root<Gruppe> gruppeFrom = query.from(Gruppe.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Gruppe> personGruppe = from.get(UtfortAktivitet_.person).get(Person_.gruppe);

        Predicate and = cb.and(cb.equal(personGruppe, gruppeFrom), cb.equal(from.get(UtfortAktivitet_.aktivitet), aktivitet));
        CriteriaQuery<Object> sumForGruppe = query.multiselect(sum, gruppeFrom).where(and).groupBy(gruppeFrom);

        TypedQuery<Object> query1 = entityManager.createQuery(sumForGruppe);
        List<Object> resultList = query1.getResultList();

        Map<Gruppe, Integer> gruppeAndScore = new HashMap<Gruppe, Integer>();
        for (Object o : resultList) {
            Object[] result = (Object[]) o;
            Integer count = (Integer) result[0];
            Gruppe g = (Gruppe) result[1];
            gruppeAndScore.put(g, count);
        }
        return gruppeAndScore;
    }

    public Map<Gruppe, Integer> getGrupperAndScoreForAlle() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<UtfortAktivitet> from = query.from(UtfortAktivitet.class);
        Root<Gruppe> gruppeFrom = query.from(Gruppe.class);
        Expression<Integer> sum = cb.sum(from.get(UtfortAktivitet_.poeng));
        Path<Gruppe> personGruppe = from.get(UtfortAktivitet_.person).get(Person_.gruppe);

        CriteriaQuery<Object> sumForGruppe = query.multiselect(sum, gruppeFrom).where(cb.equal(personGruppe, gruppeFrom)).groupBy(gruppeFrom);

        TypedQuery<Object> query1 = entityManager.createQuery(sumForGruppe);
        List<Object> resultList = query1.getResultList();

        Map<Gruppe, Integer> gruppeAndScore = new HashMap<Gruppe, Integer>();
        for (Object o : resultList) {
            Object[] result = (Object[]) o;
            Integer count = (Integer) result[0];
            Gruppe g = (Gruppe) result[1];
            gruppeAndScore.put(g, count);
        }
        return gruppeAndScore;
    }
}
