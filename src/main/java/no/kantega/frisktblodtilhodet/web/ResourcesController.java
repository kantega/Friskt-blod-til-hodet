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
    public @ResponseBody PersonDummy getPerson(@RequestParam String username){
        Person person = personRepository.findByUsername(username);
        PersonDummy personDummy = new PersonDummy();
        Gruppe gruppe = person.getGruppe();
        if (gruppe != null) {
            personDummy.setGruppe(gruppe.getId());
        }
        return personDummy;
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

    private class PersonDummy {
        private Long gruppe;

        public Long getGruppe() {
            return gruppe;
        }

        public void setGruppe(Long gruppe) {
            this.gruppe = gruppe;
        }
    }
}
