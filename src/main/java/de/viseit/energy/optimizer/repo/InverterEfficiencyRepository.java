package de.viseit.energy.optimizer.repo;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.viseit.energy.optimizer.repo.entity.InverterEfficiency;

@Repository
public interface InverterEfficiencyRepository extends JpaRepository<InverterEfficiency, UUID> {
    Optional<InverterEfficiency> findByProduced(BigDecimal produced);

    Optional<InverterEfficiency> findFirstByProducedGreaterThanOrderByProduced(BigDecimal produced);

    Optional<InverterEfficiency> findFirstByProducedLessThanOrderByProducedDesc(BigDecimal produced);
}
