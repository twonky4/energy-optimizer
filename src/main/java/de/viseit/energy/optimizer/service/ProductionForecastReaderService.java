package de.viseit.energy.optimizer.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.viseit.energy.optimizer.controller.dto.Forecast;
import de.viseit.energy.optimizer.controller.dto.PvPlant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductionForecastReaderService {
	public Forecast read(PvPlant plant) {
		log.info("load -> https://api.forecast.solar/estimate/{}/{}/{}/{}/{}", plant.getLatitude(), plant.getLongitude(), plant.getDeclination(),
				plant.getAzimuth(), plant.getKwPeak());

		return new RestTemplate().getForObject(
				"https://api.forecast.solar/estimate/{latitude}/{longitude}/{declination}/{azimuth}/{kwPeak}",
				Forecast.class, plant.getLatitude(), plant.getLongitude(), plant.getDeclination(), plant.getAzimuth(),
				plant.getKwPeak());
	}
}
