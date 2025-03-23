package itmo.leo.cdrGenerator.persistent;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdrRepository extends JpaRepository<CdrRecordDao, Long> {

    List<CdrRecordDao> getBySubsAOrSubsB(SubscriberDao subsA, SubscriberDao subsB);

    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsA_id = ?1")
    Long findOutcomingCallById(Long id);

    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsB_id = ?1")
    Long findIncomingCallById(Long id);

    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsA_id = ?1 and extract(month from call_end) = ?2")
    Long findOutcomingCallByIdAndMonth(Long id, Integer month);

    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsB_id = ?1 and extract(month from call_end) = ?2")
    Long findIncomingCallByIdAndMonth(Long id, Integer month);
}
