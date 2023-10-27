package de.viseit.energy.optimizer.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import de.viseit.energy.optimizer.controller.dto.PvPlant;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	@NotNull
	private Login login;
	@NotNull
	private List<PvPlant> pvPlants;

	@Getter
	@Setter
	public static class Login {
		private String userName;
		private String password;
	}
}
