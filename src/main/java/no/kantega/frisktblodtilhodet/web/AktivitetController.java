package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.editor.BindPersonByUsername;
import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.model.UtfortAktivitet;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AktivitetController {

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    @Autowired
    private HighscoreService highscoreService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAktiviteter(@CookieValue(value = "USERNAME", required = false, defaultValue = "") String username, Model model){
        if (!username.equals("")) {
            Person person = personRepository.findByUsername(username);

            Map<Aktivitet, Integer> aktivitetAndCountByPerson = highscoreService.getAktivitetAndCountForPerson(person);

            model.addAttribute("aktivitetAndCount", aktivitetAndCountByPerson);
        }
        return "home";
    }

    @RequestMapping(value = "/aktiviteter/{aktivitet}", method = RequestMethod.GET)
    public String visAktivitet(@PathVariable Aktivitet aktivitet, Model model){
        model.addAttribute("aktivitet", aktivitet);
        return "aktivitet";
    }

    @RequestMapping(value = "/aktiviteter/utfortAktivitet", method = RequestMethod.POST)
    public ResponseEntity registerAktivitet(@ModelAttribute UtfortAktivitet utfortAktivitet){
        utfortAktivitetRepository.save(utfortAktivitet);
        return new ResponseEntity(HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Aktivitet.class, new BindByIdEditor(aktivitetRepository));
        binder.registerCustomEditor(Person.class, new BindPersonByUsername(personRepository));
    }
}
