package no.kantega.frisktblodtilhodet.service;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HighscoreService {

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

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
}
