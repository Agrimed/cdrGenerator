package itmo.leo.cdrGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.model.UdrDto;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CdrGeneratorServicesTest {

    @Mock
    private SubsRepository subsRepository;

    @InjectMocks
    private CdrGeneratorService cdrGeneratorService;




    private SubscriberDao testSubscriber;

    @BeforeEach
    void setUp() {
        testSubscriber = new SubscriberDao(1L, "79990000001");
    }


    @Test
    void testGenerateCdrEmptyDB() {
        when(subsRepository.findAll()).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cdrGeneratorService.generateCdr();
        });

        assertEquals("Subscriber table is empty", exception.getMessage());
    }

}
