package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubsGeneratorServiceTest {

    @Mock
    private SubsRepository subsRepository;

    @InjectMocks
    private SubsGeneratorService subsGeneratorService;

    @Test
    void testGenerateSubs() {
        when(subsRepository.findMaxMsisdn()).thenReturn(Optional.of("79990000000"));
        when(subsRepository.saveAll(anyList())).thenReturn(List.of(new SubscriberDao(1L, "79990000001")));

        int count = subsGeneratorService.generateSubs(1);

        assertEquals(1, count);
        verify(subsRepository, times(1)).saveAll(anyList());
    }

}
