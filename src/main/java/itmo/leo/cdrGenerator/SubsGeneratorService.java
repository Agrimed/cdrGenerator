package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SubsGeneratorService {

    //    private final static int SUBS_AMOUNT = 15;
    private final Random random = new Random();
    @Autowired
    private SubsRepository subsRepository;


    public void generateSubs(Integer subsAmount) {
        List<SubscriberDao> subscriberDaoList = new ArrayList<>();
        String maxMsisdn = subsRepository.findMaxMsisdn().orElse("79990000000");
        Long startValue = Long.parseLong(maxMsisdn) + 1;
        for (Long i = startValue; i < startValue + subsAmount; i++) {
            subscriberDaoList.add(new SubscriberDao(null, i.toString()));
        }
        subsRepository.saveAll(subscriberDaoList);
    }


    /**
     * getRandomSubsId
     *
     * @param list list of subscribers
     * @return random subscriber id
     */
    private Long getRandomSubsId(List<SubscriberDao> list) {
        Integer index = random.nextInt() % list.size();
        return list.get(index).getId();
    }

}
