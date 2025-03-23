package itmo.leo.cdrGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения CDR Generator.
 * <p>
 * Точка входа в Spring Boot приложение.
 * </p>
 */
@SpringBootApplication
public class CdrGeneratorApplication {

//	private static Logger LOG = LoggerFactory
//			.getLogger(CdrGeneratorApplication.class);
    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(CdrGeneratorApplication.class, args);
    }

}