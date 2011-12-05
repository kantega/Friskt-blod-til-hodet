package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Gruppe;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.GruppeRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private GruppeRepository gruppeRepository;

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String admin(Model model){
        List<Gruppe> gruppeer = gruppeRepository.findAll();
        model.addAttribute("gruppeer", gruppeer);

        return "admin";
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<Person> savePerson(@ModelAttribute Person person){

        Person save = personRepository.save(person);

        return new ResponseEntity<Person>(save, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/gruppe", method = RequestMethod.POST)
    public ResponseEntity<Gruppe> saveGruppe(@ModelAttribute Gruppe gruppe){

        Gruppe save = gruppeRepository.save(gruppe);

        return new ResponseEntity<Gruppe>(save, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/aktivitet", method = RequestMethod.POST)
    public ResponseEntity<Aktivitet> saveAktivitet(@ModelAttribute Aktivitet aktivitet){

        Aktivitet save = aktivitetRepository.save(aktivitet);

        return new ResponseEntity<Aktivitet>(save, HttpStatus.CREATED);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Gruppe.class, new BindByIdEditor(gruppeRepository));
    }

}
