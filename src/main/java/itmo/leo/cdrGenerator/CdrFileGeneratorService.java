package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CdrFileGeneratorService {

    private final String path = "./reports/";

    @Autowired
    private CdrRepository cdrRepository;

    public boolean generateCdrFile(SubscriberDao subs, LocalDateTime start, LocalDateTime end, UUID uuid) throws FileNotFoundException {
        List<CdrRecordDao> list = cdrRepository.findCallsByDateRange(subs.getId(), start, end);
        String fileName = String.format("%s_%s.csv",subs.getMsisdn(), uuid.toString());
        File file = new File(path + fileName);
        file.getParentFile().mkdir();
        try (PrintWriter fos = new PrintWriter(file)) {
            list.forEach(fos::println);
        }
        return true;
    }

}
