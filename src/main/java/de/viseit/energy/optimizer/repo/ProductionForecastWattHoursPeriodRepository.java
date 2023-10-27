package de.viseit.energy.optimizer.repo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.viseit.energy.optimizer.repo.entity.WattHoursPeriod;

@Repository
public interface ProductionForecastWattHoursPeriodRepository extends JpaRepository<WattHoursPeriod, UUID> {
	public Optional<WattHoursPeriod> findByTime(ZonedDateTime time);

	public List<WattHoursPeriod> findByTimeBetween(ZonedDateTime from, ZonedDateTime to);
}
