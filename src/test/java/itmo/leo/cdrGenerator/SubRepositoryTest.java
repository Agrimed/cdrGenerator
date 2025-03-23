package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.model.SubscriberDao;
import itmo.leo.cdrGenerator.persistent.SubsRepository;
import org.hibernate.sql.Insert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class SubRepositoryTest {
    @Autowired
    SubsRepository subsRepository;

    @BeforeEach
    void setUp() {
        List<SubscriberDao> list = List.of(
                new SubscriberDao(null, "79990000001"),
                new SubscriberDao(null, "79990000002"),
                new SubscriberDao(null, "79990000003"),
                new SubscriberDao(null, "79990000004"),
                new SubscriberDao(null, "79990000005")
        );
        subsRepository.saveAll(list);
    }

    @AfterEach
    void clean() {
        subsRepository.deleteAll();
    }

    @Test
    public void findMaxMsisdn() {
        assertEquals(5, subsRepository.count());
        assertEquals("79990000005", subsRepository.findMaxMsisdn().get());
    }

    @Test
    public void findByMsisdn() {
        assertEquals("79990000003", subsRepository.findByMsisdn("79990000003").get().getMsisdn());
        assertTrue(subsRepository.findByMsisdn("799900000010").isEmpty());
    }

}
