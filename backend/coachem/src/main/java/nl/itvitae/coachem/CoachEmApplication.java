package nl.itvitae.coachem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "The CoachEm API", description = "The backend API for the coachem website!"))
public class CoachEmApplication {

	public static void main(String[] args ) {
		SpringApplication.run(CoachEmApplication.class, args);
	}
}
