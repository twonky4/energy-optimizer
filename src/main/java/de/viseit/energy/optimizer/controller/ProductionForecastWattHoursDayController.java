package de.viseit.energy.optimizer.controller;

import static de.viseit.energy.optimizer.config.ZonedDateTimeConverter.ZONE_EUROPE_BERLIN;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursDayRepository;
import de.viseit.energy.optimizer.repo.entity.WattHoursDay;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductionForecastWattHoursDayController {
	private final ProductionForecastWattHoursDayRepository repository;

	@GetMapping(path = "/forecast/watt-hours-day/{date}/")
	public ResponseEntity<Map<String, Number>> get(@PathVariable("date") @DateTimeFormat(iso = DATE) LocalDate date) {
		ZonedDateTime dateTime = date.atStartOfDay(ZONE_EUROPE_BERLIN);

		Optional<WattHoursDay> value = repository.findByTime(dateTime);
		if (value.isPresent()) {
			return ResponseEntity.ok(Map.of("time", value.get().getTime().toEpochSecond(), "value", value.get().getProductionValue()
					.intValue()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
