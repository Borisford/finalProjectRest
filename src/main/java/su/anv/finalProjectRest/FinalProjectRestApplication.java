package su.anv.finalProjectRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinalProjectRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectRestApplication.class, args);
	}

}
