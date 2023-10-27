package de.viseit.energy.optimizer.controller;

import static de.viseit.energy.optimizer.config.ZonedDateTimeConverter.ZONE_EUROPE_BERLIN;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursPeriodRepository;
import de.viseit.energy.optimizer.repo.entity.WattHoursPeriod;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductionForecastWattHoursPeriodController {
	private final ProductionForecastWattHoursPeriodRepository repository;

	@GetMapping(path = "/forecast/watt-hours-period/{date}/")
	public List<WattHoursPeriod> get(@PathVariable("date") @DateTimeFormat(iso = DATE) LocalDate date) {
		ZonedDateTime dateTime = date.atStartOfDay(ZONE_EUROPE_BERLIN);

		return repository.findByTimeBetween(dateTime, dateTime.plusDays(1));
	}
}
