package de.viseit.energy.optimizer.service;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.viseit.energy.optimizer.config.properties.AppProperties;
import de.viseit.energy.optimizer.controller.dto.Forecast;
import de.viseit.energy.optimizer.controller.dto.Forecast.Result;
import de.viseit.energy.optimizer.controller.dto.PvPlant;
import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursDayRepository;
import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursPeriodRepository;
import de.viseit.energy.optimizer.repo.ProductionForecastWattHoursRepository;
import de.viseit.energy.optimizer.repo.ProductionForecastWattsRepository;

@SpringBootTest
class ProductionForecastScheduledServiceTest {
    private ProductionForecastScheduledService service;
    private AppProperties config;

    @Autowired
    ProductionForecastWattsRepository wattsRepository;
    @Autowired
    ProductionForecastWattHoursDayRepository wattHoursDayRepository;
    @Autowired
    ProductionForecastWattHoursPeriodRepository wattHoursPeriodRepository;
    @Autowired
    ProductionForecastWattHoursRepository wattHoursRepository;
    @Autowired
    InverterEfficiencyService efficiency;
    private String tomorrow;
    private String dayAfterTomorrow;

    @BeforeEach
    void setUp() {
        config = mock(AppProperties.class);
        ProductionForecastReaderService forecast = mock(ProductionForecastReaderService.class);

        PvPlant plant1 = PvPlant.builder()
                .azimuth(ONE)
                .declination(ONE)
                .kwPeak(ONE)
                .latitude(ONE)
                .longitude(ONE)
                .build();
        when(config.getPvPlants()).thenReturn(List.of(plant1, plant1, plant1));

        tomorrow = LocalDate.now().plusDays(1).toString();
        dayAfterTomorrow = LocalDate.now().plusDays(2).toString();

        Map<String, BigDecimal> wattHours = new HashMap<>();
        wattHours.put(tomorrow + " 07:58:57", valueOf(0));
        wattHours.put(tomorrow + " 08:00:00", valueOf(1));
        wattHours.put(tomorrow + " 09:00:00", valueOf(164));
        wattHours.put(tomorrow + " 10:00:00", valueOf(351));
        wattHours.put(tomorrow + " 11:00:00", valueOf(518));
        wattHours.put(tomorrow + " 12:00:00", valueOf(693));
        wattHours.put(tomorrow + " 13:00:00", valueOf(868));
        wattHours.put(tomorrow + " 14:00:00", valueOf(1030));
        wattHours.put(tomorrow + " 15:00:00", valueOf(1170));
        wattHours.put(tomorrow + " 16:00:00", valueOf(1278));
        wattHours.put(tomorrow + " 17:00:00", valueOf(1346));
        wattHours.put(tomorrow + " 17:56:32", valueOf(1367));
        wattHours.put(dayAfterTomorrow + " 08:00:44", valueOf(0));
        wattHours.put(dayAfterTomorrow + " 09:00:00", valueOf(275));
        wattHours.put(dayAfterTomorrow + " 10:00:00", valueOf(800));
        wattHours.put(dayAfterTomorrow + " 11:00:00", valueOf(1201));
        wattHours.put(dayAfterTomorrow + " 12:00:00", valueOf(1494));
        wattHours.put(dayAfterTomorrow + " 13:00:00", valueOf(1762));
        wattHours.put(dayAfterTomorrow + " 14:00:00", valueOf(1994));
        wattHours.put(dayAfterTomorrow + " 15:00:00", valueOf(2171));
        wattHours.put(dayAfterTomorrow + " 16:00:00", valueOf(2299));
        wattHours.put(dayAfterTomorrow + " 17:00:00", valueOf(2378));
        wattHours.put(dayAfterTomorrow + " 17:54:35", valueOf(2401));
        Map<String, BigDecimal> wattHoursDay = new HashMap<>();
        wattHoursDay.put(tomorrow + "", valueOf(1367));
        wattHoursDay.put(dayAfterTomorrow + "", valueOf(2401));
        Map<String, BigDecimal> wattHoursPeriod = new HashMap<>();
        wattHoursPeriod.put(tomorrow + " 07:58:57", valueOf(0));
        wattHoursPeriod.put(tomorrow + " 08:00:00", valueOf(1));
        wattHoursPeriod.put(tomorrow + " 09:00:00", valueOf(163));
        wattHoursPeriod.put(tomorrow + " 10:00:00", valueOf(187));
        wattHoursPeriod.put(tomorrow + " 11:00:00", valueOf(167));
        wattHoursPeriod.put(tomorrow + " 12:00:00", valueOf(175));
        wattHoursPeriod.put(tomorrow + " 13:00:00", valueOf(175));
        wattHoursPeriod.put(tomorrow + " 14:00:00", valueOf(162));
        wattHoursPeriod.put(tomorrow + " 15:00:00", valueOf(140));
        wattHoursPeriod.put(tomorrow + " 16:00:00", valueOf(108));
        wattHoursPeriod.put(tomorrow + " 17:00:00", valueOf(68));
        wattHoursPeriod.put(tomorrow + " 17:56:32", valueOf(21));
        wattHoursPeriod.put(dayAfterTomorrow + " 08:00:44", valueOf(0));
        wattHoursPeriod.put(dayAfterTomorrow + " 09:00:00", valueOf(275));
        wattHoursPeriod.put(dayAfterTomorrow + " 10:00:00", valueOf(525));
        wattHoursPeriod.put(dayAfterTomorrow + " 11:00:00", valueOf(401));
        wattHoursPeriod.put(dayAfterTomorrow + " 12:00:00", valueOf(293));
        wattHoursPeriod.put(dayAfterTomorrow + " 13:00:00", valueOf(268));
        wattHoursPeriod.put(dayAfterTomorrow + " 14:00:00", valueOf(232));
        wattHoursPeriod.put(dayAfterTomorrow + " 15:00:00", valueOf(177));
        wattHoursPeriod.put(dayAfterTomorrow + " 16:00:00", valueOf(128));
        wattHoursPeriod.put(dayAfterTomorrow + " 17:00:00", valueOf(79));
        wattHoursPeriod.put(dayAfterTomorrow + " 17:54:35", valueOf(23));
        Map<String, BigDecimal> watts = new HashMap<>();
        watts.put(tomorrow + " 07:58:57", valueOf(0));
        watts.put(tomorrow + " 08:00:00", valueOf(116));
        watts.put(tomorrow + " 09:00:00", valueOf(209));
        watts.put(tomorrow + " 10:00:00", valueOf(164));
        watts.put(tomorrow + " 11:00:00", valueOf(169));
        watts.put(tomorrow + " 12:00:00", valueOf(180));
        watts.put(tomorrow + " 13:00:00", valueOf(170));
        watts.put(tomorrow + " 14:00:00", valueOf(154));
        watts.put(tomorrow + " 15:00:00", valueOf(125));
        watts.put(tomorrow + " 16:00:00", valueOf(91));
        watts.put(tomorrow + " 17:00:00", valueOf(44));
        watts.put(tomorrow + " 17:56:32", valueOf(0));
        watts.put(dayAfterTomorrow + " 08:00:44", valueOf(0));
        watts.put(dayAfterTomorrow + " 09:00:00", valueOf(556));
        watts.put(dayAfterTomorrow + " 10:00:00", valueOf(493));
        watts.put(dayAfterTomorrow + " 11:00:00", valueOf(309));
        watts.put(dayAfterTomorrow + " 12:00:00", valueOf(277));
        watts.put(dayAfterTomorrow + " 13:00:00", valueOf(258));
        watts.put(dayAfterTomorrow + " 14:00:00", valueOf(205));
        watts.put(dayAfterTomorrow + " 15:00:00", valueOf(148));
        watts.put(dayAfterTomorrow + " 16:00:00", valueOf(107));
        watts.put(dayAfterTomorrow + " 17:00:00", valueOf(50));
        watts.put(dayAfterTomorrow + " 17:54:35", valueOf(0));

        when(forecast.read(plant1)).thenReturn(Forecast.builder()
                .result(Result.builder()
                        .wattHours(wattHours)
                        .wattHoursDay(wattHoursDay)
                        .wattHoursPeriod(wattHoursPeriod)
                        .watts(watts)
                        .build())
                .build()).thenReturn(Forecast.builder()
                        .result(Result.builder()
                                .wattHours(new HashMap<>(wattHours))
                                .wattHoursDay(new HashMap<>(wattHoursDay))
                                .wattHoursPeriod(new HashMap<>(wattHoursPeriod))
                                .watts(new HashMap<>(watts))
                                .build())
                        .build())
                .thenReturn(Forecast.builder()
                        .result(Result.builder()
                                .wattHours(new HashMap<>(wattHours))
                                .wattHoursDay(new HashMap<>(wattHoursDay))
                                .wattHoursPeriod(new HashMap<>(wattHoursPeriod))
                                .watts(new HashMap<>(watts))
                                .build())
                        .build());

        service = new ProductionForecastScheduledService(forecast, efficiency, config, wattsRepository, wattHoursDayRepository,
                wattHoursPeriodRepository, wattHoursRepository, mock(DeviceScheduleService.class));
    }

    @Test
    void read() {
        service.setEnabled(true);
        service.read();

        assertThat(wattsRepository.count()).isEqualTo(23);
        assertThat(wattHoursDayRepository.count()).isEqualTo(2);
        assertThat(wattHoursPeriodRepository.count()).isEqualTo(23);
        assertThat(wattHoursRepository.findAll())
                .map(w -> w.getTime().toString(), w -> w.getProductionValue().toPlainString())
                .filteredOn(t -> ((String) t.toList().get(0)).contains(tomorrow + "T08:00+0"))
                .extracting(t -> t.toList().get(1))
                .as("value")
                .contains("3.00");
    }
}
