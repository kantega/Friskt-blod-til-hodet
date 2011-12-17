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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AktivitetController {

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAktiviteter(Model model){
        List<Aktivitet> aktiviteter = aktivitetRepository.findAll();

        Map<Aktivitet, Long> aktivitetAndCountByPerson = new HashMap<Aktivitet, Long>();
        Person person = personRepository.findAll().get(0);
        for (Aktivitet aktivitet : aktiviteter) {
            Long countByAktivitetAndPerson = utfortAktivitetRepository.getCountByAktivitetAndPerson(aktivitet, person);
            aktivitetAndCountByPerson.put(aktivitet, countByAktivitetAndPerson);
        }

        model.addAttribute("aktiviteter", aktivitetAndCountByPerson);
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
