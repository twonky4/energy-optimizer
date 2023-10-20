package de.viseit.energy.optimizer.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.viseit.energy.optimizer.controller.dto.Forecast;
import de.viseit.energy.optimizer.controller.dto.PvPlant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ProductionForecastReaderService {
    private final RestTemplate restTemplate;

    public void read(PvPlant plant) {
        Forecast forecast = restTemplate.getForObject(
                "https://api.forecast.solar/estimate/{latitude}/{longitude}/{declination}/{azimuth}/{kwPeak}",
                Forecast.class, plant.getLatitude(), plant.getLongitude(), plant.getDeclination(), plant.getAzimuth(),
                plant.getKwPeak());
    }
}
