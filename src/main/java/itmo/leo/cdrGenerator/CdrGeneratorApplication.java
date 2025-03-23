package itmo.leo.cdrGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;


@SpringBootApplication
public class CdrGeneratorApplication {

//	private static Logger LOG = LoggerFactory
//			.getLogger(CdrGeneratorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CdrGeneratorApplication.class, args);
	}

}