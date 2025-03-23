package itmo.leo.cdrGenerator.persistent;

import itmo.leo.cdrGenerator.model.CdrRecordDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdrRepository extends JpaRepository<CdrRecordDao, Long> {
}
