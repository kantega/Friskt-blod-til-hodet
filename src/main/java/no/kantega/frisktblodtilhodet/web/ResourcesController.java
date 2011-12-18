package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.editor.BindPersonByUsername;
import no.kantega.frisktblodtilhodet.model.Gruppe;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.GruppeRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResourcesController {

    @Autowired
    private GruppeRepository gruppeRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/doesPersonExist", method = RequestMethod.GET)
    public @ResponseBody Person getPerson(@RequestParam String username){
        return personRepository.findByUsername(username);
    }

    @RequestMapping(value = "/velgGruppe", method = RequestMethod.GET)
    public String velgGruppe(Model model){
        model.addAttribute("grupper", gruppeRepository.findAll());
        return "velgGruppe";
    }

    @RequestMapping(value = "/velgGruppe", method = RequestMethod.POST)
    public ResponseEntity lagreGruppe(@RequestParam Person person, @RequestParam Gruppe gruppe){
        person.setGruppe(gruppe);
        personRepository.save(person);
        return new ResponseEntity(HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Gruppe.class, new BindByIdEditor(gruppeRepository));
        binder.registerCustomEditor(Person.class, new BindPersonByUsername(personRepository));
    }

}
