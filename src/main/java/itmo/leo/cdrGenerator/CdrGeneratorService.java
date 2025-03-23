package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * CdrGeneratorService generate cdr's
 */
@Service
public class CdrGeneratorService {

    private final Random generator = new Random();
    @Autowired
    private CdrRepository cdrRepository;
    @Autowired
    private SubsRepository subsRepository;

    private final Long secPerYear = 60L * 60L * 24L * 365L;
    private final Integer maxCallLength = 60 * 60;
    private final Integer minNumberOfCalls = 10000;
    private final Integer maxNumberOfCalls = 100000;

    /**
     * generateCdr generate
     */
    public void generateCdr() {

        Integer numberOfCalls = generateCallsAmount();
        List<SubscriberDao> list = subsRepository.findAll();
        List<CdrRecordDao> cdrRecords = new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Long step = secPerYear / numberOfCalls;

        for (int i = 0; i < numberOfCalls; i++) {
            SubscriberDao subsA = getRandomSubs(list);
            SubscriberDao subsB;
            do {
                subsB = getRandomSubs(list);
            } while (subsA.getId() == subsB.getId());

            Integer callType = generateCallType();
            LocalDateTime endDate = startDate.plusSeconds(generateCallLength());

            CdrRecordDao cdrRecordDao = new CdrRecordDao(null, callType, subsA, subsB, startDate, endDate);
            cdrRecords.add(cdrRecordDao);
            startDate = startDate.plusSeconds(step + generateTimeShift(step));
        }

        cdrRepository.saveAll(cdrRecords);
    }

    private Long generateTimeShift(Long boundary) {
        return generator.nextLong() % boundary;
    }

    private Long generateCallLength() {
        return generator.nextLong(maxCallLength);
    }

    private SubscriberDao getRandomSubs(List<SubscriberDao> list) {
        return list.get(generator.nextInt(list.size()));
    }

    private Integer generateCallsAmount() {
        return generator.nextInt(minNumberOfCalls, maxNumberOfCalls);
    }

    private Integer generateCallType() {
        return generator.nextBoolean() ? 1 : 2;
    }


}
