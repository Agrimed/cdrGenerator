package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CallTimeDto;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.model.UdrDto;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdrGeneratorService {

    @Autowired
    CdrRepository cdrRepository;
    @Autowired
    SubsRepository subsRepository;

    public Long getAllCdrByMsisdn(String msisdn) {
        SubscriberDao subscriberDao = subsRepository.findByMsisdn(msisdn).get();
//        return cdrRepository.getBySubsAOrSubsB(subscriberDao, subscriberDao);
        Long timeInterval = cdrRepository.findOutcomingCallById(subscriberDao.getId());
        return timeInterval;
    }

    public Long getAllCdrByMsidnAndMonth(String msisdn, Integer month) {
        SubscriberDao subscriberDao = subsRepository.findByMsisdn(msisdn).get();
        Long timeIntervalByMonth = cdrRepository.findIncomingCallByIdAndMonth(subscriberDao.getId(), month);
        return timeIntervalByMonth;
    }

    public UdrDto getUdr(String msisdn) {
        SubscriberDao subs = subsRepository.findByMsisdn(msisdn).get();
        Long incomingCall = cdrRepository.findIncomingCallById(subs.getId());
        Long outcomingCall = cdrRepository.findOutcomingCallById(subs.getId());
        return new UdrDto(msisdn, new CallTimeDto(incomingCall), new CallTimeDto(outcomingCall));
    }

    public UdrDto getUdrByMonth(String msisdn, Integer month) {
        SubscriberDao subs = subsRepository.findByMsisdn(msisdn).get();
        Long incomingCall = cdrRepository.findIncomingCallByIdAndMonth(subs.getId(), month);
        Long outcomingCall = cdrRepository.findOutcomingCallByIdAndMonth(subs.getId(), month);
        return new UdrDto(msisdn, new CallTimeDto(incomingCall), new CallTimeDto(outcomingCall));
    }

}
