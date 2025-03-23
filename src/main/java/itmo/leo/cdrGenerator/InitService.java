//package itmo.leo.cdrGenerator;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class InitService implements InitializingBean {
//
//    @Autowired
//    private SubsGeneratorService subsGeneratorService;
//
//    @Autowired
//    private CdrGeneratorService cdrGeneratorService;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        subsGeneratorService.generateSubs(50);
//        cdrGeneratorService.generateCdr();
//    }
//
//}
