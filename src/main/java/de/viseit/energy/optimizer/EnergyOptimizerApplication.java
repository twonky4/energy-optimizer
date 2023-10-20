package de.viseit.energy.optimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnergyOptimizerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnergyOptimizerApplication.class, args);
	}
}
