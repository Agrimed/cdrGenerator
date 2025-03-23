package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.model.UdrDto;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UdrGeneratorServiceTest {

    @Mock
    private CdrRepository cdrRepository;

    @InjectMocks
    private UdrGeneratorService udrGeneratorService;

    private final SubscriberDao subs = new SubscriberDao(1L, "79990000001");

    @Test
    void testGetUdrByMsisdn() {

        when(cdrRepository.findIncomingCallById(subs.getId())).thenReturn(1 * 3600L + 2 * 60 + 3);
        when(cdrRepository.findOutcomingCallById(subs.getId())).thenReturn(2 * 3600L + 3 * 60 + 4);

        UdrDto udr = udrGeneratorService.getUdr(subs);

        assertEquals("79990000001", udr.getMsisdn());
        assertEquals("1:02:03", udr.getIncomingCall().getTotalTime());
        assertEquals("2:03:04", udr.getOutcomingCall().getTotalTime());
    }

    @Test
    void testGetUdrByMonth() {
        when(cdrRepository.findIncomingCallByIdAndMonth(subs.getId(), 1)).thenReturn(1 * 3600L + 2 * 60 + 3);
        when(cdrRepository.findOutcomingCallByIdAndMonth(subs.getId(), 1)).thenReturn(2 * 3600L + 3 * 60 + 4);

        UdrDto udr = udrGeneratorService.getUdrByMonth(subs, 1);

        assertEquals("79990000001", udr.getMsisdn());
        assertEquals("1:02:03", udr.getIncomingCall().getTotalTime());
        assertEquals("2:03:04", udr.getOutcomingCall().getTotalTime());
    }

}
