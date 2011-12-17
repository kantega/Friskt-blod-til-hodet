package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.AktivitetType;
import no.kantega.frisktblodtilhodet.model.Avdeling;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.AvdelingRepository;
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

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AvdelingRepository avdelingRepository;

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String admin(Model model){
        List<Avdeling> avdelinger = avdelingRepository.findAll();
        model.addAttribute("avdelinger", avdelinger);

        List<String> aktivitetTyper = new LinkedList<String>();
        for(AktivitetType aktivitetType : AktivitetType.values()){
            aktivitetTyper.add(aktivitetType.name());
        }
        model.addAttribute("aktivitetsTyper", aktivitetTyper);
        return "admin";
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<Person> savePerson(@ModelAttribute Person person){

        Person save = personRepository.save(person);

        return new ResponseEntity<Person>(save, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/avdeling", method = RequestMethod.POST)
    public ResponseEntity<Avdeling> saveAvdeling(@ModelAttribute Avdeling avdeling){

        Avdeling save = avdelingRepository.save(avdeling);

        return new ResponseEntity<Avdeling>(save, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/aktivitet", method = RequestMethod.POST)
    public ResponseEntity<Aktivitet> saveAktivitet(@ModelAttribute Aktivitet aktivitet){

        Aktivitet save = aktivitetRepository.save(aktivitet);

        return new ResponseEntity<Aktivitet>(save, HttpStatus.CREATED);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Avdeling.class, new BindByIdEditor(avdelingRepository));
    }

}
