package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Gruppe;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/highscore")
public class HighscoreController {
    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    @RequestMapping(value = "/person/{person}")
    public List getUtforteAktiviteterForPerson(@PathVariable Person person){

        return null;
    }

    @RequestMapping(value = "/person/{person}/overall")
    public List getOverallForPerson(@PathVariable Person person){
        Long poengByPerson = utfortAktivitetRepository.getPoengByPerson(person);
        return null;
    }

    @RequestMapping(value = "/gruppe/{gruppe}/overall")
    public List getOverallForGruppe(@PathVariable Gruppe gruppe){
        Long poengByGruppe = utfortAktivitetRepository.getPoengByGruppe(gruppe);
        return null;
    }

    @RequestMapping(value = "/person/{person}/{aktivitet}")
    public List getForAktivitetForPerson(@PathVariable Person person, @PathVariable Aktivitet aktivitet){
        Long poengByPerson = utfortAktivitetRepository.getPoengByPerson(person);
        return null;
    }

    @RequestMapping(value = "/gruppe/{gruppe}/{aktivitet}")
    public List getForAktivitetForGruppe(@PathVariable Gruppe gruppe, @PathVariable Aktivitet aktivitet){
        Long poengByGruppe = utfortAktivitetRepository.getPoengByAktivitetAndPersonGruppe(aktivitet, gruppe);
        return null;
    }
}
