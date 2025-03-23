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
 * Сервис для генерации записей CDR.
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
     * Генерирует записи CDR.
     * <p>
     * Записи создаются на основе случайно выбранных абонентов, типов вызовов и длительности звонка.
     * Результирующий список сохраняется в репозитории.
     * </p>
     */
    public Integer generateCdr() {

        Integer numberOfCalls = generateCallsAmount();
        List<SubscriberDao> list = subsRepository.findAll();
        if (list.isEmpty()) {
            throw new RuntimeException("Subscriber table is empty");
        }
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

        return cdrRepository.saveAll(cdrRecords).size();
    }

    /**
     * Генерирует случайное смещение времени в пределах заданной границы.
     *
     * @param boundary граница для генерации смещения
     * @return смещение времени в секундах
     */
    private Long generateTimeShift(Long boundary) {
        return generator.nextLong() % boundary;
    }

    /**
     * Генерирует длительность звонка в секундах.
     *
     * @return длительность звонка в секундах
     */
    private Long generateCallLength() {
        return generator.nextLong(maxCallLength);
    }

    /**
     * Возвращает случайного абонента из списка.
     *
     * @param list список абонентов
     * @return случайно выбранный абонент
     */
    private SubscriberDao getRandomSubs(List<SubscriberDao> list) {
        return list.get(generator.nextInt(list.size()));
    }

    /**
     * Генерирует количество звонков в заданном диапазоне.
     *
     * @return количество звонков
     */
    private Integer generateCallsAmount() {
        return generator.nextInt(minNumberOfCalls, maxNumberOfCalls);
    }

    /**
     * Генерирует тип звонка.
     *
     * @return 1 или 2, в зависимости от случайного выбора
     */
    private Integer generateCallType() {
        return generator.nextBoolean() ? 1 : 2;
    }


}
