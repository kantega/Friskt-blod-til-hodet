package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Gruppe;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.GruppeRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private PersonRepository personRepository;

    @Autowired
    private GruppeRepository gruppeRepository;

    @RequestMapping
    public String index(Model model){
        model.addAttribute("aktiviteter", aktivitetRepository.findAll());

        Map<Person, Integer> scores = highscoreService.getPersonAndScore();
        model.addAttribute("personAndCount", scores);
        return "highscore/highscore";
    }

    @RequestMapping(value = "/Mingruppe/Alle")
    public String getForMinGruppe(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, Model model){
        Person person = personRepository.findByUsername(username);
        Gruppe gruppe = person.getGruppe();
        Map<Person, Integer> scores = highscoreService.getPersonsAndScoreForGruppe(gruppe);
        model.addAttribute("personAndCount", scores);
        return "highscore/list";
    }

    @RequestMapping(value = "/Mingruppe/{aktivitet}")
    public String getForAktivitetForMinGruppe(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, @PathVariable Aktivitet aktivitet, Model model){
        Person person = personRepository.findByUsername(username);
        Gruppe gruppe = person.getGruppe();
        Map<Person, Integer> scores = highscoreService.getPersonsAndScoreForGruppeAndAktivitet(gruppe, aktivitet);
        model.addAttribute("personAndCount", scores);
        return "highscore/list";
    }

    @RequestMapping(value = "/Grupper/{aktivitet}")
    public String getForAktivitetForGrupper(@PathVariable Aktivitet aktivitet, Model model){
        Map<Gruppe, Integer> scores = highscoreService.getGrupperAndScoreForAktivitet(aktivitet);
        model.addAttribute("personAndCount", scores);
        return "highscore/list";
    }

    @RequestMapping(value = "/Grupper/Alle")
    public String getForAlleForGruppe(Model model){
        Map<Gruppe, Integer> scores = highscoreService.getGrupperAndScoreForAlle();
        model.addAttribute("personAndCount", scores);
        return "highscore/list";
    }

    @RequestMapping(value = "/Totalt/Alle")
    public String getTotalForAlle(Model model){
        return index(model);
    }

    @RequestMapping(value = "/Totalt/{aktivitet}")
    public String getTotalForAktivitet(@PathVariable Aktivitet aktivitet, Model model){
        Map<Person, Integer> scores = highscoreService.getPersonsAndScoreForAktivitet(aktivitet);
        model.addAttribute("personAndCount", scores);
        return "highscore/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Aktivitet.class, new BindByIdEditor(aktivitetRepository));
        binder.registerCustomEditor(Gruppe.class, new BindByIdEditor(gruppeRepository));
    }
}
