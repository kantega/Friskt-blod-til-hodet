package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/statistikk")
public class StatistikkController {
    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    public List getUtforteAktiviteterForPerson(@RequestParam Person person){

        return null;
    }

}