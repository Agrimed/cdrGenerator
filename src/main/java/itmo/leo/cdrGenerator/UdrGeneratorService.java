package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.CallTimeDto;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.model.UdrDto;
import itmo.leo.cdrGenerator.persistent.CdrRepository;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для генерации UDR (Usage Detail Record).
 */
@Service
public class UdrGeneratorService {

    @Autowired
    CdrRepository cdrRepository;
    @Autowired
    SubsRepository subsRepository;

    /**
     * Возвращает общее время исходящих звонков для заданного абонента по MSISDN.
     *
     * @param msisdn номер абонента
     * @return общее время исходящих звонков в секундах
     */
    public Long getAllCdrByMsisdn(String msisdn) {
        SubscriberDao subscriberDao = subsRepository.findByMsisdn(msisdn).get();
//        return cdrRepository.getBySubsAOrSubsB(subscriberDao, subscriberDao);
        Long timeInterval = cdrRepository.findOutcomingCallById(subscriberDao.getId());
        return timeInterval;
    }

    /**
     * Возвращает общее время исходящих звонков для заданного абонента по MSISDN за указанный месяц.
     *
     * @param msisdn номер абонента
     * @param month  месяц для выборки данных
     * @return общее время исходящих звонков в секундах за указанный месяц
     */
    public Long getAllCdrByMsidnAndMonth(String msisdn, Integer month) {
        SubscriberDao subscriberDao = subsRepository.findByMsisdn(msisdn).get();
        Long timeIntervalByMonth = cdrRepository.findIncomingCallByIdAndMonth(subscriberDao.getId(), month);
        return timeIntervalByMonth;
    }

    /**
     * Возвращает UDR для заданного абонента.
     *
     * @param subs объект subscriberDao
     * @return объект {@link UdrDto} с данными UDR, включающими время входящих и исходящих звонков
     */
    public UdrDto getUdr(SubscriberDao subs) {
        Long incomingCall = cdrRepository.findIncomingCallById(subs.getId());
        Long outcomingCall = cdrRepository.findOutcomingCallById(subs.getId());
        return new UdrDto(subs.getMsisdn(), new CallTimeDto(incomingCall), new CallTimeDto(outcomingCall));
    }

    /**
     * Возвращает UDR для заданного абонента за указанный месяц.
     *
     * @param subs объект subscriberDao
     * @param month  месяц для выборки данных в интервале 1-12
     * @return объект {@link UdrDto} с данными UDR за указанный месяц
     */
    public UdrDto getUdrByMonth(SubscriberDao subs, Integer month) {
        Long incomingCall = cdrRepository.findIncomingCallByIdAndMonth(subs.getId(), month);
        Long outcomingCall = cdrRepository.findOutcomingCallByIdAndMonth(subs.getId(), month);
        return new UdrDto(subs.getMsisdn(), new CallTimeDto(incomingCall), new CallTimeDto(outcomingCall));
    }

    public SubscriberDao getSubs(String msisdn) {
        return subsRepository.findByMsisdn(msisdn)
                .orElseThrow(() -> new RuntimeException(String.format("Msisdn %s not found", msisdn)));
    }

    public Long cdrCnt() {
        return cdrRepository.count();
    }

    public Long subsCnt() {
        return subsRepository.count();
    }

}
