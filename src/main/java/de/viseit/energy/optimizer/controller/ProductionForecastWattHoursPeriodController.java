package de.viseit.energy.optimizer.controller;

import static de.viseit.energy.optimizer.config.ZonedDateTimeConverter.ZONE_EUROPE_BERLIN;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursPeriodRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductionForecastWattHoursPeriodController {
    private final ProductionForecastWattHoursPeriodRepository repository;

    @GetMapping(path = "/forecast/watt-hours-period/{date}/")
    public Map<Long, Integer> get(@PathVariable("date") @DateTimeFormat(iso = DATE) LocalDate date) {
        ZonedDateTime dateTime = date.atStartOfDay(ZONE_EUROPE_BERLIN);

        return repository.findByTimeBetweenOrderByTime(dateTime, dateTime.plusDays(1)).stream()
                .collect(toMap(w -> w.getTime().toEpochSecond(), w -> w.getProductionValue().intValue(), (x, y) -> y, LinkedHashMap::new));
    }

    @GetMapping(path = "/forecast/watt-hours-period/{date}/csv")
    public String getCsv(@PathVariable("date") @DateTimeFormat(iso = DATE) LocalDate date) {
        ZonedDateTime dateTime = date.atStartOfDay(ZONE_EUROPE_BERLIN);
        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return repository.findByTimeBetweenOrderByTime(dateTime, dateTime.plusDays(1)).stream()
                .map(w -> w.getTime().format(formatPattern) + "," + w.getProductionValue().intValue())
                .collect(joining("\n"));
    }
}
