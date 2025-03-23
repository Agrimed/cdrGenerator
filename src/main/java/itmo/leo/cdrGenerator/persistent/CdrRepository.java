package itmo.leo.cdrGenerator.persistent;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import itmo.leo.cdrGenerator.model.SubscriberDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с записями CDR (Call Detail Record).
 * <p>
 * Предоставляет методы для поиска записей по абонентам и расчёта суммарной длительности звонков.
 * </p>
 */
@Repository
public interface CdrRepository extends JpaRepository<CdrRecordDao, Long> {

    /**
     * Находит все записи CDR, где заданный абонент является либо инициатором, либо получателем вызова.
     *
     * @param subsA абонент, являющийся инициатором вызова
     * @param subsB абонент, являющийся получателем вызова
     * @return список записей CDR
     */
    List<CdrRecordDao> getBySubsAOrSubsB(SubscriberDao subsA, SubscriberDao subsB);

    /**
     * Вычисляет суммарную длительность исходящих звонков (в секундах) для абонента с указанным идентификатором.
     * <p> Нативный SQL-запрос для расчёта разницы между временем начала и завершения вызова.</p>
     *
     * @param id идентификатор абонента
     * @return суммарная длительность исходящих звонков в секундах
     */
    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsA_id = ?1")
    Long findOutcomingCallById(Long id);

    /**
     * Вычисляет суммарную длительность входящих звонков (в секундах) для абонента с указанным идентификатором.
     * <p>
     * Нативный SQL-запрос для расчёта разницы между временем начала и завершения вызова.
     * </p>
     *
     * @param id идентификатор абонента
     * @return суммарная длительность входящих звонков в секундах
     */
    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsB_id = ?1")
    Long findIncomingCallById(Long id);

    /**
     * Вычисляет суммарную длительность исходящих звонков (в секундах) для абонента с указанным идентификатором за определённый месяц.
     * <p>
     * Нативный SQL-запрос, включающий фильтрацию по месяцу завершения вызова.
     * </p>
     *
     * @param id    идентификатор абонента
     * @param month месяц, по которому производится фильтрация
     * @return суммарная длительность исходящих звонков в секундах за указанный месяц
     */
    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsA_id = ?1 and extract(month from call_end) = ?2")
    Long findOutcomingCallByIdAndMonth(Long id, Integer month);


    /**
     * Вычисляет суммарную длительность входящих звонков (в секундах) для абонента с указанным идентификатором за определённый месяц.
     * <p>
     * Нативный SQL-запрос, включающий фильтрацию по месяцу завершения вызова.
     * </p>
     *
     * @param id    идентификатор абонента
     * @param month месяц, по которому производится фильтрация
     * @return суммарная длительность входящих звонков в секундах за указанный месяц
     */
    @NativeQuery("select sum(datediff(SECOND, call_start, call_end)) from cdr_record where subsB_id = ?1 and extract(month from call_end) = ?2")
    Long findIncomingCallByIdAndMonth(Long id, Integer month);

    @NativeQuery("select * from cdr_record where (subsA_id = ?1 or subsB_id = ?1) and call_start >= ?2 and call_end < ?3")
    List<CdrRecordDao> findCallsByDateRange(Long id, LocalDateTime start, LocalDateTime end);
}
