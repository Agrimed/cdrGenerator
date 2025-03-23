package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.UdrDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CdrController {

    @Autowired
    private SubsGeneratorService subsGeneratorService;

    @Autowired
    private CdrGeneratorService cdrGeneratorService;

    @Autowired
    private UdrGeneratorService udrGeneratorService;

    @GetMapping("/cdr/genSubs")
    public String genSubsPage() {
        subsGeneratorService.generateSubs(50);
        return "cdr";
    }

    @GetMapping("/cdr/genCdr")
    public String genCdr() {
        cdrGeneratorService.generateCdr();
        return "cdr";
    }

    @GetMapping("/udr/{msisdn}")
    public UdrDto getUdrByMsisdn(@PathVariable String msisdn) {
        return udrGeneratorService.getUdr(msisdn);
    }

    @GetMapping("/udr/{msisdn}/{month}")
    public UdrDto hetUdrByMsisdnAndMonth(@PathVariable String msisdn, @PathVariable Integer month) {
        return udrGeneratorService.getUdrByMonth(msisdn, month);
    }
}
