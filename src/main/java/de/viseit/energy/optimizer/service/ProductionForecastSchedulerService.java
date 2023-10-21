package de.viseit.energy.optimizer.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.viseit.energy.optimizer.config.properties.AppProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ProductionForecastSchedulerService {
	private final ProductionForecastReaderService reader;
	private final AppProperties config;

	@Scheduled(fixedDelay = 3600000)
	public void read() {
		config.getPvPlants()
				.stream()
				.forEach(reader::read);
	}
}
