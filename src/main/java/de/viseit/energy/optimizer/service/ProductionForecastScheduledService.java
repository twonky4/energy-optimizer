package de.viseit.energy.optimizer.service;

import static de.viseit.energy.optimizer.config.ZonedDateTimeConverter.ZONE_EUROPE_BERLIN;
import static lombok.AccessLevel.PACKAGE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.viseit.energy.optimizer.config.properties.AppProperties;
import de.viseit.energy.optimizer.controller.dto.Forecast.Result;
import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursDayRepository;
import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursPeriodRepository;
import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursRepository;
import de.viseit.energy.optimizer.repo.ProductionForecastWattsRepository;
import de.viseit.energy.optimizer.repo.entity.WattHours;
import de.viseit.energy.optimizer.repo.entity.WattHoursDay;
import de.viseit.energy.optimizer.repo.entity.WattHoursPeriod;
import de.viseit.energy.optimizer.repo.entity.Watts;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductionForecastScheduledService {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final ProductionForecastReaderService forecast;
	private final InverterEfficiencyService efficiency;
	private final AppProperties config;
	private final ProductionForecastWattsRepository wattsRepository;
	private final ProductionForecastWattHoursDayRepository wattHoursDayRepository;
	private final ProductionForecastWattHoursPeriodRepository wattHoursPeriodRepository;
	private final ProductionForecastWattHoursRepository wattHoursRepository;

	@Value("${app.scheduled.enabled:false}")
	@Setter(PACKAGE)
	private boolean enabled;

	@Scheduled(fixedDelay = 3600000)
	public void read() {
		if (!enabled) {
			log.warn("scheduler not enabled");
			return;
		}
		log.info("read PV");

		config.getPvPlants()
				.stream()
				.map(forecast::read)
				.map(f -> f.getResult())
				.reduce((a, b) -> {
					merge(a, b, Result::getWattHours);
					merge(a, b, Result::getWattHoursDay);
					merge(a, b, Result::getWattHoursPeriod);
					merge(a, b, Result::getWatts);
					return a;
				})
				.map(r -> {
					r.getWattHours().entrySet().forEach(e -> e.setValue(efficiency.apply(e.getValue())));
					r.getWattHoursDay().entrySet().forEach(e -> e.setValue(efficiency.apply(e.getValue())));
					r.getWattHoursPeriod().entrySet().forEach(e -> e.setValue(efficiency.apply(e.getValue())));
					r.getWatts().entrySet().forEach(e -> e.setValue(efficiency.apply(e.getValue())));
					return r;
				}).ifPresent(r -> {
					r.getWatts().entrySet().forEach(this::handleWatts);
					r.getWattHoursDay().entrySet().forEach(this::handleWattHoursDay);
					r.getWattHoursPeriod().entrySet().forEach(this::handleWattHoursPeriod);
					r.getWattHours().entrySet().forEach(this::handleWattHours);
				});
	}

	private void handleWattHours(Entry<String, BigDecimal> i) {
		ZonedDateTime time = parseDateTime(i.getKey());
		WattHours entity = wattHoursRepository.findByTime(time)
				.map(e -> {
					e.setProductionValue(i.getValue());
					return e;
				})
				.orElseGet(() -> WattHours.builder().time(time).productionValue(i.getValue()).build());
		wattHoursRepository.save(entity);
	}

	private void handleWattHoursDay(Entry<String, BigDecimal> i) {
		ZonedDateTime time = parseDate(i.getKey());
		WattHoursDay entity = wattHoursDayRepository.findByTime(time)
				.map(e -> {
					e.setProductionValue(i.getValue());
					return e;
				})
				.orElseGet(() -> WattHoursDay.builder().time(time).productionValue(i.getValue()).build());
		wattHoursDayRepository.save(entity);
	}

	private void handleWattHoursPeriod(Entry<String, BigDecimal> i) {
		ZonedDateTime time = parseDateTime(i.getKey());
		WattHoursPeriod entity = wattHoursPeriodRepository.findByTime(time)
				.map(e -> {
					e.setProductionValue(i.getValue());
					return e;
				})
				.orElseGet(() -> WattHoursPeriod.builder().time(time).productionValue(i.getValue()).build());
		wattHoursPeriodRepository.save(entity);
	}

	private void handleWatts(Entry<String, BigDecimal> i) {
		ZonedDateTime time = parseDateTime(i.getKey());
		Watts entity = wattsRepository.findByTime(time)
				.map(e -> {
					e.setProductionValue(i.getValue());
					return e;
				})
				.orElseGet(() -> Watts.builder().time(time).productionValue(i.getValue()).build());
		wattsRepository.save(entity);
	}

	private void merge(Result a, Result b, Function<Result, Map<String, BigDecimal>> m) {
		m.apply(a).entrySet().forEach(e -> e.setValue(m.apply(b).get(e.getKey()).add(e.getValue())));
	}

	private ZonedDateTime parseDateTime(String str) {
		return LocalDateTime.parse(str, DATE_TIME_FORMATTER).atZone(ZONE_EUROPE_BERLIN);
	}

	private ZonedDateTime parseDate(String str) {
		LocalDate localDate = LocalDate.parse(str, DATE_FORMATTER);

		return localDate.atStartOfDay(ZONE_EUROPE_BERLIN);
	}
}
