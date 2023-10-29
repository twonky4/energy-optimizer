package de.viseit.energy.optimizer.service;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.viseit.energy.optimizer.controller.dto.InverterRecord;
import de.viseit.energy.optimizer.repo.InverterEfficiencyRepository;
import de.viseit.energy.optimizer.repo.entity.InverterEfficiency;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InverterEfficiencyService {
    private final InverterEfficiencyRepository repository;

    public void add(InverterRecord entry) {
        BigDecimal produced = entry.getProduced()
                .stream()
                .reduce(ZERO, BigDecimal::add);

        BigDecimal efficiency;
        if (produced.compareTo(ZERO) != 0) {
            efficiency = entry.getFeedIn().divide(produced, 5, HALF_UP);
        } else {
            efficiency = ZERO;
        }

        if (repository.findByProduced(produced).isEmpty()) {
            repository.save(InverterEfficiency.builder()
                    .efficiency(efficiency)
                    .produced(produced)
                    .build());
        }
    }

    public BigDecimal apply(BigDecimal produced) {
        return repository.findByProduced(produced)
                .or(() -> {
                    Optional<InverterEfficiency> previosValue = repository.findFirstByProducedLessThanOrderByProducedDesc(produced);
                    Optional<InverterEfficiency> nextValue = repository.findFirstByProducedGreaterThanOrderByProduced(produced);

                    if (previosValue.isPresent() && nextValue.isPresent()) {
                        BigDecimal efficiency = nextValue.get().getEfficiency().subtract(previosValue.get().getEfficiency()).multiply(
                                produced.subtract(previosValue.get().getProduced()).divide(nextValue.get().getProduced().subtract(
                                        previosValue.get().getProduced()), 12, HALF_UP))
                                .add(previosValue.get().getEfficiency());
                        return Optional.of(InverterEfficiency.builder()
                                .produced(produced)
                                .efficiency(efficiency)
                                .build());
                    }
                    return Optional.empty();
                })
                .map(e -> e.getEfficiency())
                .map(b -> b.multiply(produced).setScale(0, HALF_UP))
                .orElse(produced);
    }

    public List<InverterEfficiency> get() {
        return repository.findOrderByProduced();
    }
}
