package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Gruppe;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.GruppeRepository;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/highscore")
public class HighscoreController {
    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    @Autowired
    private HighscoreService highscoreService;

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private GruppeRepository gruppeRepository;

    @RequestMapping
    public String index(Model model){
        model.addAttribute("grupper", gruppeRepository.findAllLeafGrupper());
        model.addAttribute("aktiviteter", aktivitetRepository.findAll());
        return "highscore/highscore";
    }

    @RequestMapping(value = "/person/{person}")
    public String getUtforteAktiviteterForPerson(@PathVariable Person person, Model model){
        Map<Aktivitet, Long> aktivitetAndCountByPerson = highscoreService.getAktivitetAndCountForPerson(person);
        model.addAttribute("aktivitetAndCount", aktivitetAndCountByPerson);
        return "highscore/list";
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
