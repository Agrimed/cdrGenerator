package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CdrFileRequestDto;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.model.UdrDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * REST-контроллер для работы с CDR и UDR.
 * <p>
 * Содержит эндпоинты для генерации абонентов, CDR и получения UDR по номеру и месяцу.
 * </p>
 */
@RestController
public class CdrController {

    private final Integer subsAmount = 50;

    @Autowired
    private SubsGeneratorService subsGeneratorService;

    @Autowired
    private CdrGeneratorService cdrGeneratorService;

    @Autowired
    private UdrGeneratorService udrGeneratorService;

    @Autowired
    private CdrFileGeneratorService cdrFileGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<?> generate() {
        Integer subsCnt;
        Integer cdrCnt;
        try {
            subsCnt = subsGeneratorService.generateSubs(subsAmount);
            cdrCnt = cdrGeneratorService.generateCdr();
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                String.format("Generated %d subscribers. %d CDRs", subsCnt, cdrCnt),
                HttpStatus.OK
        );
    }

    /**
     * Эндпоинт для получения UDR по MSISDN.
     *
     * @param msisdn номер абонента
     * @return объект {@link UdrDto} с данными UDR
     */
    @GetMapping("/udr/{msisdn}")
    public ResponseEntity<?> getUdrByMsisdn(@PathVariable String msisdn) {
        UdrDto udrDto;
        try {
            if (isDbEmpty()) {
                throw new RuntimeException("Database is Empty. Call method 'POST <host>:8080/generate'");
            }
            SubscriberDao subs = udrGeneratorService.getSubs(msisdn);
            udrDto = udrGeneratorService.getUdr(subs);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                udrDto,
                HttpStatus.OK
        );
    }

    /**
     * Эндпоинт для получения UDR по MSISDN и месяцу.
     *
     * @param msisdn номер абонента
     * @param month  месяц для выборки данных
     * @return объект {@link UdrDto} с данными UDR за указанный месяц
     */
    @GetMapping("/udr/{msisdn}/{month}")
    public ResponseEntity<?> getUdrByMsisdnAndMonth(@PathVariable String msisdn, @PathVariable Integer month) {
        UdrDto udrDto;
        try {
            if (isDbEmpty()) {
                throw new RuntimeException("Database is Empty. Call method 'POST <host>:8080/generate'");
            }
            SubscriberDao subs = udrGeneratorService.getSubs(msisdn);
            if (isMonthExists(month)) {
                throw new RuntimeException("Month number should be in range from 1 to 12");
            }
            udrDto = udrGeneratorService.getUdrByMonth(subs, month);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                udrDto,
                HttpStatus.OK
        );
    }

    @GetMapping("/file")
    public ResponseEntity<?> getGeneratedFile(@RequestBody CdrFileRequestDto request) {
        UUID uuid = UUID.randomUUID();
        try {
            if (isDbEmpty()) {
                throw new RuntimeException("Database is Empty. Call method 'POST <host>:8080/generate'");
            }
            SubscriberDao subs = udrGeneratorService.getSubs(request.getMsisdn());
            cdrFileGeneratorService.generateCdrFile(subs, request.getStart(), request.getEnd(), uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                Map.of("UUID", uuid), HttpStatus.OK
        );
    }

    private Boolean isDbEmpty() {
        return udrGeneratorService.cdrCnt() == 0L || udrGeneratorService.subsCnt() == 0;
    }

    private Boolean isMonthExists(Integer month) {
        return month < 1 || month > 12;
    }

}
