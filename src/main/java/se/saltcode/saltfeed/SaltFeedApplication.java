package se.saltcode.saltfeed;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import se.saltcode.saltfeed.mocking.DatabaseMocker;

@OpenAPIDefinition(
	info = @Info(
		title = "SaltFeed API",
		description = "A nice little backend"
	)
)
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)

@SpringBootApplication
public class SaltFeedApplication {

	private static final Logger logger = LoggerFactory.getLogger(SaltFeedApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(SaltFeedApplication.class, args);

		logger.info("Go to http://localhost:8081/swagger-ui.html to see the endpoints");
		logger.info("Go to http://localhost:8081/h2-console to view the database");
	}
}
