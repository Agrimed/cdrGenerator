package itmo.leo.cdrGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@RequestMapping("cdr")
public class CdrController {

    @Autowired
    private SubsGeneratorService subsGeneratorService;

    @Autowired
    private CdrGeneratorService cdrGeneratorService;

    @GetMapping("/cdr")
    public String viewHomePage(Model model) {
        model.addAttribute("userName", "Kirill");
        return "cdr";
    }

    @GetMapping("/cdr/genSubs")
    public String genSubsPage(Model model) {
        subsGeneratorService.generateSubs(50);
        return "cdr";
    }

    @GetMapping("/cdr/genCdr")
    public String genCdr(Model model) {
        cdrGeneratorService.generateCdr();
        return "cdr";
    }

}
