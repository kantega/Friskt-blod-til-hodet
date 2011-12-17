package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.editor.BindPersonByUsername;
import no.kantega.frisktblodtilhodet.model.Avdeling;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.AvdelingRepository;
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
    private AvdelingRepository avdelingRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/doesPersonExist", method = RequestMethod.GET)
    public @ResponseBody Person getPerson(@RequestParam String username){
        return personRepository.findByUsername(username);
    }

    @RequestMapping(value = "/velgAvdeling", method = RequestMethod.GET)
    public String velgAvdeling(Model model){
        model.addAttribute("avdelinger", avdelingRepository.findAll());
        return "velgAvdeling";
    }

    @RequestMapping(value = "/velgAvdeling", method = RequestMethod.POST)
    public ResponseEntity lagreAvdeling(@RequestParam Person person, @RequestParam Avdeling avdeling){
        person.setAvdeling(avdeling);
        personRepository.save(person);
        return new ResponseEntity(HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Avdeling.class, new BindByIdEditor(avdelingRepository));
        binder.registerCustomEditor(Person.class, new BindPersonByUsername(personRepository));
    }

}
