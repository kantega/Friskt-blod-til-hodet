package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.model.UtfortAktivitet;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.AvdelingRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
public class ResourcesController {

    @Autowired
    private AvdelingRepository avdelingRepository;

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAktiviteter(Model model){
        List<Aktivitet> aktiviteter = aktivitetRepository.findAll();
        model.addAttribute("aktiviteter", aktiviteter);
        return "home";
    }

    @RequestMapping(value = "/utfortaktivitet", method = RequestMethod.POST)
    public ResponseEntity registerAktivitet(@RequestParam Aktivitet aktivitet, @RequestParam Person person){

        UtfortAktivitet utfortAktivitet = new UtfortAktivitet(aktivitet, person);
        utfortAktivitetRepository.save(utfortAktivitet);
        return new ResponseEntity(HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Aktivitet.class, new BindByIdEditor(aktivitetRepository));
        binder.registerCustomEditor(Person.class, new BindPersonByUsername());
    }

    private class BindPersonByUsername extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) {
            setValue(personRepository.findByUsername(text));
        }

        @Override
        public String getAsText() {
            Person value = (Person) getValue();
            return value.getUsername();
        }
    }
}
