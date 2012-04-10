package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.editor.BindByIdEditor;
import no.kantega.frisktblodtilhodet.model.*;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.GruppeRepository;
import no.kantega.frisktblodtilhodet.service.PeriodeRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
    
    @Autowired
    private PeriodeRepository periodeRepository;
    
    private File informationTextFile;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(method = RequestMethod.GET)
    public String admin(Model model){
        List<Gruppe> gruppeer = gruppeRepository.findAll();
        model.addAttribute("grupper", gruppeer);

        List<String> aktivitetTyper = new LinkedList<String>();
        for(AktivitetType aktivitetType : AktivitetType.values()){
            aktivitetTyper.add(aktivitetType.name());
        }
        model.addAttribute("aktivitetsTyper", aktivitetTyper);
        model.addAttribute("informationtext", getInformationText());
        model.addAttribute("currentPeriod", periodeRepository.findLatestPeriode());
        return "admin";
    }

    private String getInformationText() {
        String text = "";
        try {
            text = IOUtils.toString(new FileReader(informationTextFile));
        } catch (IOException e) {}

        return text;
    }

    @RequestMapping(value = "/information", method = RequestMethod.POST)
    public ResponseEntity saveInformation(@RequestParam String informationText) throws IOException {

        IOUtils.write(informationText, new FileOutputStream(informationTextFile));

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity savePerson(@ModelAttribute Person person){

        Person save = personRepository.save(person);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/gruppe", method = RequestMethod.POST)
    public ResponseEntity saveGruppe(@ModelAttribute Gruppe gruppe){

        Gruppe save = gruppeRepository.save(gruppe);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/aktivitet", method = RequestMethod.POST)
    public ResponseEntity saveAktivitet(@ModelAttribute Aktivitet aktivitet){

        Aktivitet save = aktivitetRepository.save(aktivitet);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/nyPeriode", method = RequestMethod.POST)
    public ResponseEntity startNyPeriode(@RequestParam Date stopDate){
        Periode periode = new Periode();
        periode.setStopdato(stopDate);
        
        Periode previousPeriode = periodeRepository.findLatestPeriode();
        periode.setStartdato(previousPeriode.getStopdato());

        periode = periodeRepository.save(periode);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Gruppe.class, new BindByIdEditor(gruppeRepository));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Autowired
    public void setDataDir(File dataDir){
        informationTextFile = new File(dataDir, "informationFile");
    }
}
