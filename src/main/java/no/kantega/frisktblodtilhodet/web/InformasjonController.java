package no.kantega.frisktblodtilhodet.web;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Controller
public class InformasjonController {
    private File informationTextFile;

    @RequestMapping("/information")
    public String showInformation(Model model){
        String text = "";
        try {
            text = IOUtils.toString(new FileReader(informationTextFile));
        } catch (IOException e) {}
        model.addAttribute("informationtext", text);
        return "information";
    }


    @Autowired
    public void setDataDir(File dataDir){
        informationTextFile = new File(dataDir, "informationFile");
    }
}
