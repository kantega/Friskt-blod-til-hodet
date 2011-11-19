package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.AvdelingRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AvdelingRepository avdelingRepository;

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/aktivitet", method = RequestMethod.POST)
    public ResponseEntity<Aktivitet> saveAktivitet(@ModelAttribute Aktivitet aktivitet){

        Aktivitet save = aktivitetRepository.save(aktivitet);

        return new ResponseEntity<Aktivitet>(save, HttpStatus.OK);
    }

}
