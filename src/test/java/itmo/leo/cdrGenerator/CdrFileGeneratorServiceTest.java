package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CdrFileGeneratorServiceTest {

    @Mock
    private CdrRepository cdrRepository;

    @InjectMocks
    CdrFileGeneratorService cdrFileGeneratorService;

    SubscriberDao subsA = new SubscriberDao(1L, "79990000001");
    SubscriberDao subsB = new SubscriberDao(2L, "79990000002");

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

    @Test
    void generateCdrFileTest() throws IOException {

        String INPUT_FILE_NAME = "./reports/79990000001_61f0c404-5cb3-11e7-907b-a6006ad3dba0.csv";

        when(cdrRepository.
                findCallsByDateRange(
                        anyLong(),
                        any(),
                        any()))
                .thenReturn(list);

        cdrFileGeneratorService.generateCdrFile(
                subsA,
                LocalDateTime.parse("2025-01-01T12:00:00"),
                LocalDateTime.parse("2025-05-01T12:00:00"),
                UUID.fromString("61f0c404-5cb3-11e7-907b-a6006ad3dba0")
        );

        try (Stream<String> fileStream = Files.lines(Paths.get(INPUT_FILE_NAME))) {
            int noOfLines = (int) fileStream.count();
            assertEquals(list.size(), noOfLines);
            Files.delete(Path.of(INPUT_FILE_NAME));
        }
    }

}
