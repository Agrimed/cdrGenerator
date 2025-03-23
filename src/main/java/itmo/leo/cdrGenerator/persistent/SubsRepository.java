package itmo.leo.cdrGenerator.persistent;

import itmo.leo.cdrGenerator.model.SubscriberDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью абонента.
 * <p>
 * Предоставляет методы для поиска абонентов по MSISDN и получения максимального значения MSISDN.
 * </p>
 */
@Repository
public interface SubsRepository extends JpaRepository<SubscriberDao, Long> {
    /**
     * Находит максимальный номер MSISDN среди всех абонентов.
     * <p>
     * Выполняет нативный SQL-запрос для поиска максимального значения.
     * </p>
     *
     * @return {@link Optional} с максимальным MSISDN, если он существует, иначе пустой {@code Optional}
     */
    @Query(value = "select max(msisdn) from subscriber", nativeQuery = true)
    Optional<String> findMaxMsisdn();

    /**
     * Находит абонента по номеру MSISDN.
     *
     * @param msisdn номер абонента
     * @return {@link Optional} с найденным объектом {@link SubscriberDao} или пустой, если абонент не найден
     */
    Optional<SubscriberDao> findByMsisdn(String msisdn);

}