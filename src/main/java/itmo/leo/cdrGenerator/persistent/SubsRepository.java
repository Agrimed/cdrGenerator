package itmo.leo.cdrGenerator.persistent;

import itmo.leo.cdrGenerator.model.SubscriberDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubsRepository extends JpaRepository<SubscriberDao, Long> {

    @Query(value = "select max(msisdn) from subscriber", nativeQuery = true)
    Optional<String> findMaxMsisdn();

}