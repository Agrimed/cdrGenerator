package itmo.leo.cdrGenerator;

import itmo.leo.cdrGenerator.persistent.SubsRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.jpa.hibernate.ddl-auto=create-drop"
})
class CdrGeneratorApplicationTests {

	@Autowired
	SubsGeneratorService subsGeneratorService;

	@Autowired
	SubsRepository subsRepository;

	@Test
	void generateSubs() {
		subsGeneratorService.generateSubs(2);
        assertEquals(2, subsRepository.count());
	}

	@Test
	void contextLoads() {
	}

}
