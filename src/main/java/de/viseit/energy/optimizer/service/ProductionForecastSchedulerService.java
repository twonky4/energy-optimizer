package de.viseit.energy.optimizer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.viseit.energy.optimizer.controller.dto.PvPlant;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ProductionForecastSchedulerService {
    private final ProductionForecastReaderService reader;

    @Value("${app.pvPlants}")
    private List<PvPlant> pvPlants;

    @Scheduled(fixedDelay = 3600000)
    public void read() {
        pvPlants.stream()
                .forEach(reader::read);
    }
}
