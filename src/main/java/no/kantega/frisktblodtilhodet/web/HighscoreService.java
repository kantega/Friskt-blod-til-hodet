package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
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
