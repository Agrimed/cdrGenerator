package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CallTimeDto;
import itmo.leo.cdrGenerator.model.CdrFileRequestDto;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.model.UdrDto;
import org.apache.coyote.Request;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CdrController.class)
public class CdrControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubsGeneratorService subsGeneratorService;

    @MockitoBean
    private CdrGeneratorService cdrGeneratorService;

    @MockitoBean
    private UdrGeneratorService udrGeneratorService;

    @MockitoBean
    private CdrFileGeneratorService cdrFileGeneratorService;


    @Test
    public void generateTest() throws Exception {
        when(subsGeneratorService.generateSubs(anyInt())).thenReturn(10);
        when(cdrGeneratorService.generateCdr()).thenReturn(10000);
        MvcResult result = mockMvc.perform(post("/generate"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("Generated 10 subscribers. 10000 CDRs", response);
    }

    @Test
    public void getUdrByMsisdnTest() throws Exception {
        when(udrGeneratorService.cdrCnt()).thenReturn(0L);
        ResultActions result = mockMvc.perform(get("/udr/79990000001"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error")
                        .value("Database is Empty. Call method 'POST <host>:8080/generate'"));
    }

    @Test
    public void getUdrByMsisdnWithWrongTest() throws Exception {
        when(udrGeneratorService.cdrCnt()).thenReturn(100L);
        when(udrGeneratorService.subsCnt()).thenReturn(100L);
        when(udrGeneratorService.getSubs(anyString())).thenThrow(new RuntimeException("Msisdn 79990000001 not found"));
        ResultActions result = mockMvc.perform(get("/udr/79990000001"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error")
                        .value("Msisdn 79990000001 not found"));
    }

    @Test
    public void getUdrByMsisdnHappyPathTest() throws Exception {
        SubscriberDao subs = new SubscriberDao(1L, "79990000001");
        UdrDto udrDto = new UdrDto(
                "79990000001",
                new CallTimeDto(1 * 3600L + 2 * 60 + 3),
                new CallTimeDto(2 * 3600L + 3 * 60 + 4)
        );
        when(udrGeneratorService.cdrCnt()).thenReturn(100L);
        when(udrGeneratorService.subsCnt()).thenReturn(100L);
        when(udrGeneratorService.getSubs(anyString())).thenReturn(subs);
        when(udrGeneratorService.getUdr(subs)).thenReturn(udrDto);
        ResultActions result = mockMvc.perform(get("/udr/79990000001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msisdn")
                        .value("79990000001"))
                .andExpect(jsonPath("$.incomingCall.totalTime")
                        .value("1:02:03"))
                .andExpect(jsonPath("$.outcomingCall.totalTime")
                        .value("2:03:04"));
    }


    @Test
    public void getUdrByMsisdnAndMonthTest() throws Exception {
        when(udrGeneratorService.cdrCnt()).thenReturn(0L);
        ResultActions result = mockMvc.perform(get("/udr/79990000001/2"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error")
                        .value("Database is Empty. Call method 'POST <host>:8080/generate'"));
    }

    @Test
    public void getUdrByMsisdnAndMonthWithWrongTest() throws Exception {
        when(udrGeneratorService.cdrCnt()).thenReturn(100L);
        when(udrGeneratorService.subsCnt()).thenReturn(100L);
        when(udrGeneratorService.getSubs(anyString())).thenThrow(new RuntimeException("Msisdn 79990000001 not found"));
        ResultActions result = mockMvc.perform(get("/udr/79990000001/2"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error")
                        .value("Msisdn 79990000001 not found"));
    }

    @Test
    public void getUdrByMsisdnAndWrongMonthTest() throws Exception {

        when(udrGeneratorService.cdrCnt()).thenReturn(100L);
        when(udrGeneratorService.subsCnt()).thenReturn(100L);

        ResultActions result = mockMvc.perform(get("/udr/79990000001/13"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error")
                        .value("Month number should be in range from 1 to 12"));
    }

    @Test
    public void getUdrByMsisdnAndMonthHappyPathTest() throws Exception {
        SubscriberDao subs = new SubscriberDao(1L, "79990000001");
        UdrDto udrDto = new UdrDto(
                "79990000001",
                new CallTimeDto(1 * 3600L + 2 * 60 + 3),
                new CallTimeDto(2 * 3600L + 3 * 60 + 4)
        );
        when(udrGeneratorService.cdrCnt()).thenReturn(100L);
        when(udrGeneratorService.subsCnt()).thenReturn(100L);
        when(udrGeneratorService.getSubs(anyString())).thenReturn(subs);
        when(udrGeneratorService.getUdrByMonth(subs, 2)).thenReturn(udrDto);

        ResultActions result = mockMvc.perform(get("/udr/79990000001/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msisdn")
                        .value("79990000001"))
                .andExpect(jsonPath("$.incomingCall.totalTime")
                        .value("1:02:03"))
                .andExpect(jsonPath("$.outcomingCall.totalTime")
                        .value("2:03:04"));
    }

}
