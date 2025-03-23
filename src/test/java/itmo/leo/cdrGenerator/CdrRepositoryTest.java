package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class CdrRepositoryTest {
    @Autowired
    CdrRepository cdrRepository;

    @Autowired
    SubsRepository subsRepository;

    private SubscriberDao subsA;
    private SubscriberDao subsB;

    @BeforeEach
    void setUp() {
        subsA = subsRepository.save(new SubscriberDao(null, "79990000001"));
        subsB = subsRepository.save(new SubscriberDao(null, "79990000002"));

        List<CdrRecordDao> list = List.of(
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-01-01T12:00:00"), LocalDateTime.parse("2025-01-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-02-01T12:00:00"), LocalDateTime.parse("2025-02-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-03-01T12:00:00"), LocalDateTime.parse("2025-03-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-04-01T12:00:00"), LocalDateTime.parse("2025-04-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-05-01T12:00:00"), LocalDateTime.parse("2025-05-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-06-01T12:00:00"), LocalDateTime.parse("2025-06-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-07-01T12:00:00"), LocalDateTime.parse("2025-07-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-08-01T12:00:00"), LocalDateTime.parse("2025-08-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-09-01T12:00:00"), LocalDateTime.parse("2025-09-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-10-01T12:00:00"), LocalDateTime.parse("2025-10-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-11-01T12:00:00"), LocalDateTime.parse("2025-11-01T12:05:00")),
                new CdrRecordDao(null, 1,  subsA, subsB, LocalDateTime.parse("2025-12-01T12:00:00"), LocalDateTime.parse("2025-12-01T12:05:00")),

                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-01-01T12:00:00"), LocalDateTime.parse("2025-01-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-02-01T12:00:00"), LocalDateTime.parse("2025-02-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-03-01T12:00:00"), LocalDateTime.parse("2025-03-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-04-01T12:00:00"), LocalDateTime.parse("2025-04-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-05-01T12:00:00"), LocalDateTime.parse("2025-05-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-06-01T12:00:00"), LocalDateTime.parse("2025-06-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-07-01T12:00:00"), LocalDateTime.parse("2025-07-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-08-01T12:00:00"), LocalDateTime.parse("2025-08-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-09-01T12:00:00"), LocalDateTime.parse("2025-09-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-10-01T12:00:00"), LocalDateTime.parse("2025-10-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-11-01T12:00:00"), LocalDateTime.parse("2025-11-01T12:10:00")),
                new CdrRecordDao(null, 1,  subsB, subsA, LocalDateTime.parse("2025-12-01T12:00:00"), LocalDateTime.parse("2025-12-01T12:10:00"))
        );

        cdrRepository.saveAll(list);
    }

    @AfterEach
    void clean() {
        cdrRepository.deleteAll();
        subsRepository.deleteAll();
    }

    @Test
    public void findOutcomingCallById() {

        assertEquals(5 * 12 * 60, cdrRepository.findOutcomingCallById(getSubsA()));
        assertEquals(10 * 12 * 60, cdrRepository.findOutcomingCallById(getSubsB()));
    }

    @Test
    public void findIncomingCallById() {
        assertEquals(10 * 12 * 60, cdrRepository.findIncomingCallById(getSubsA()));
        assertEquals(5 * 12 * 60, cdrRepository.findIncomingCallById(getSubsB()));
    }

    @Test
    public void findOutcomingCallByIdAndMonth() {
        assertEquals(5 * 60, cdrRepository.findOutcomingCallByIdAndMonth(getSubsA(), 8));
        assertEquals(10 * 60, cdrRepository.findOutcomingCallByIdAndMonth(getSubsB(), 8));
    }

    @Test
    public void findIncomingCallIdAndMonth() {
        assertEquals(10 * 60, cdrRepository.findIncomingCallByIdAndMonth(getSubsA(), 10));
        assertEquals(5 * 60, cdrRepository.findIncomingCallByIdAndMonth(getSubsB(), 10));
    }

    @Test
    public void findCallsByDateRangeTest() {
        List<CdrRecordDao> list = cdrRepository.findCallsByDateRange(
                getSubsA(),
                LocalDateTime.parse("2025-02-01T00:00:00"),
                LocalDateTime.parse("2025-08-01T00:00:00"));
         assertEquals(12, list.size());
    }

    @Test
    public void findCallsByUnrealDateRangeTest() {
        List<CdrRecordDao> list = cdrRepository.findCallsByDateRange(
                getSubsA(),
                LocalDateTime.parse("2026-02-01T00:00:00"),
                LocalDateTime.parse("2026-08-01T00:00:00"));
        assertEquals(0, list.size());
    }



    private Long getSubsA() {
        return subsRepository.findByMsisdn("79990000001").get().getId();
    }
    private Long getSubsB() {
        return subsRepository.findByMsisdn("79990000002").get().getId();
    }


}
