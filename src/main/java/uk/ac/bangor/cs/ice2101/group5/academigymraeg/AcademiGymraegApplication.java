package uk.ac.bangor.cs.ice2101.group5.academigymraeg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class AcademiGymraegApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AcademiGymraegApplication.class, args);
	}

}
