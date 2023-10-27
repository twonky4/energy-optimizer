package de.viseit.energy.optimizer.repo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.viseit.energy.optimizer.repo.entity.WattHours;

@Repository
public interface ProductionForecastWattHoursRepository extends JpaRepository<WattHours, UUID> {
	public Optional<WattHours> findByTime(ZonedDateTime time);

	public List<WattHours> findByTimeBetween(ZonedDateTime from, ZonedDateTime to);
}
