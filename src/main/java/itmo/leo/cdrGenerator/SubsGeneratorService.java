package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Сервис для генерации абонентов.
 */
@Service
public class SubsGeneratorService {

    //    private final static int SUBS_AMOUNT = 15;
    private final Random random = new Random();
    @Autowired
    private SubsRepository subsRepository;

    /**
     * Генерирует заданное количество абонентов.
     * <p>
     * Новые абоненты создаются с уникальными номерами MSISDN, начиная с наибольшего существующего номера плюс один.
     * </p>
     *
     * @param subsAmount количество абонентов для генерации
     */
    @Transactional
    public Integer generateSubs(Integer subsAmount) {
        List<SubscriberDao> subscriberDaoList = new ArrayList<>();
        String maxMsisdn = subsRepository.findMaxMsisdn().orElse("79990000000");
        Long startValue = Long.parseLong(maxMsisdn) + 1;
        for (Long i = startValue; i < startValue + subsAmount; i++) {
            subscriberDaoList.add(new SubscriberDao(null, i.toString()));
        }
        return subsRepository.saveAll(subscriberDaoList).size();
    }


    /**
     * Возвращает случайный идентификатор абонента из списка.
     *
     * @param list список абонентов
     * @return случайный идентификатор абонента
     */
    private Long getRandomSubsId(List<SubscriberDao> list) {
        Integer index = random.nextInt() % list.size();
        return list.get(index).getId();
    }

}
