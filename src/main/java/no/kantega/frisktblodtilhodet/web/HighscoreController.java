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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

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

    private int numberOfEntries = 10;

    @RequestMapping
    public String index(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, Model model){
        model.addAttribute("aktiviteter", aktivitetRepository.findAll());

        Map<Person, Integer> allScores = highscoreService.getPersonAndScore();
        Map<Person, Integer> limitedNumberOfScores = getFirstNEntries(allScores);

        model.addAttribute("standing", getPersonStanding(allScores, personRepository.findByUsername(username)));
        model.addAttribute("total", allScores.size());
        model.addAttribute("personAndCount", limitedNumberOfScores);
        return "highscore/highscore";
    }

    @RequestMapping(value = "/Mingruppe/Alle")
    public String getForMinGruppe(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, Model model){
        Person person = personRepository.findByUsername(username);
        Gruppe gruppe = person.getGruppe();
        SortedMap<Person, Integer> allScores = highscoreService.getPersonsAndScoreForGruppe(gruppe);
        Map<Person, Integer> limitedNumberOfScores = getFirstNEntries(allScores);


        model.addAttribute("standing", getPersonStanding(allScores, person));
        model.addAttribute("total", allScores.size());
        model.addAttribute("personAndCount", limitedNumberOfScores);
        return "highscore/list";
    }

    private Integer getPersonStanding(Map<Person, Integer> allScores, Person person) {
        int i = 1;
        for(Person p : allScores.keySet()){
            if(p.equals(person)){
                break;
            }
            i++;
        }
        return i;
    }

    private Map<Person, Integer> getFirstNEntries(Map<Person, Integer> allScores) {
        Map<Person, Integer> limitedMap = new LinkedHashMap<Person, Integer>();
        for (Map.Entry<Person, Integer> entry : allScores.entrySet()) {
            limitedMap.put(entry.getKey(), entry.getValue());
            if(limitedMap.size() == numberOfEntries ){
                break;
            }
        }
        return limitedMap;
    }

    @RequestMapping(value = "/Mingruppe/{aktivitet}")
    public String getForAktivitetForMinGruppe(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, @PathVariable Aktivitet aktivitet, Model model){
        Person person = personRepository.findByUsername(username);
        Gruppe gruppe = person.getGruppe();
        SortedMap<Person, Integer> allScores = highscoreService.getPersonsAndScoreForGruppeAndAktivitet(gruppe, aktivitet);
        Map<Person, Integer> limitedNumberOfScores = getFirstNEntries(allScores);


        model.addAttribute("standing", getPersonStanding(allScores, person));
        model.addAttribute("total", allScores.size());
        model.addAttribute("personAndCount", limitedNumberOfScores);
        return "highscore/list";
    }

    @RequestMapping(value = "/Grupper/{aktivitet}")
    public String getForAktivitetForGrupper(@PathVariable Aktivitet aktivitet, Model model){
        Map<Gruppe, Double> scores = highscoreService.getGrupperAndScoreForAktivitet(aktivitet);
        model.addAttribute("personAndCount", scores);
        return "highscore/list";
    }

    @RequestMapping(value = "/Grupper/Alle")
    public String getForAlleForGruppe(Model model){
        Map<Gruppe, Double> scores = highscoreService.getGrupperAndScoreForAlle();
        model.addAttribute("personAndCount", scores);
        return "highscore/list";
    }

    @RequestMapping(value = "/Totalt/Alle")
    public String getTotalForAlle(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, Model model){
        return index(username, model);
    }

    @RequestMapping(value = "/Totalt/{aktivitet}")
    public String getTotalForAktivitet(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, @PathVariable Aktivitet aktivitet, Model model){
        Map<Person, Integer> allScores = highscoreService.getPersonsAndScoreForAktivitet(aktivitet);
        Map<Person, Integer> limitedNumberOfScores = getFirstNEntries(allScores);


        model.addAttribute("standing", getPersonStanding(allScores, personRepository.findByUsername(username)));
        model.addAttribute("total", allScores.size());
        model.addAttribute("personAndCount", limitedNumberOfScores);
        return "highscore/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Aktivitet.class, new BindByIdEditor(aktivitetRepository));
        binder.registerCustomEditor(Gruppe.class, new BindByIdEditor(gruppeRepository));
    }
}
