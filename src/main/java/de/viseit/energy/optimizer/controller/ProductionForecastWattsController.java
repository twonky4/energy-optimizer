package de.viseit.energy.optimizer.controller;

import static de.viseit.energy.optimizer.config.ZonedDateTimeConverter.ZONE_EUROPE_BERLIN;
import static java.util.stream.Collectors.toMap;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viseit.energy.optimizer.repo.ProductionForecastWattsRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductionForecastWattsController {
	private final ProductionForecastWattsRepository repository;

	@GetMapping(path = "/forecast/watts/{date}/")
	public Map<Long, BigDecimal> get(@PathVariable("date") @DateTimeFormat(iso = DATE) LocalDate date) {
		ZonedDateTime dateTime = date.atStartOfDay(ZONE_EUROPE_BERLIN);

		return repository.findByTimeBetween(dateTime, dateTime.plusDays(1)).stream()
				.collect(toMap(w -> w.getTime().toEpochSecond(), w -> w.getProductionValue().stripTrailingZeros()));
	}
}
