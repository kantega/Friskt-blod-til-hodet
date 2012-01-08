package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Gruppe;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.GruppeRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Map<Person, Long> getPersonAndScore() {
        Map<Person, Long> personAndCount = new HashMap<Person, Long>();
        for(final Person person : personRepository.findAll()){
            long count = utfortAktivitetRepository.getPoengByPerson(person);
            personAndCount.put(person, count);

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

    public Map<Gruppe, Long> getGruppeAndScore() {
        Map<Gruppe, Long> gruppeAndScore = new HashMap<Gruppe, Long>();
        for(Gruppe gruppe : gruppeRepository.findAll()){
            Long score = utfortAktivitetRepository.getPoengByGruppe(gruppe);
            gruppeAndScore.put(gruppe, score);
        }
        return gruppeAndScore;
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
