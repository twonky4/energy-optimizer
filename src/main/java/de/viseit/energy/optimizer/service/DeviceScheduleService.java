package de.viseit.energy.optimizer.service;

import static de.viseit.energy.optimizer.config.ZonedDateTimeConverter.ZONE_EUROPE_BERLIN;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.viseit.energy.optimizer.repo.DeviceRepository;
import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursPeriodRepository;
import de.viseit.energy.optimizer.repo.entity.Device;
import de.viseit.energy.optimizer.repo.entity.WattHoursPeriod;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeviceScheduleService {
    private final ProductionForecastWattHoursPeriodRepository forecastRepo;
    private final DeviceRepository deviceRepo;

    public void calculate() {
        ZonedDateTime dateTime = LocalDate.now().atStartOfDay(ZONE_EUROPE_BERLIN);

        List<WattHoursPeriod> production = forecastRepo.findByTimeBetweenOrderByTime(dateTime, dateTime.plusDays(1));

        List<Device> devices = deviceRepo.findAll(Sort.by("prio"));

        devices.forEach(d -> {
        });
    }
}
