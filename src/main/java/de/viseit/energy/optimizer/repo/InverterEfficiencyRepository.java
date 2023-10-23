package de.viseit.energy.optimizer.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.viseit.energy.optimizer.repo.entity.InverterEfficiency;

@Repository
public interface InverterEfficiencyRepository extends JpaRepository<InverterEfficiency, UUID> {
}
