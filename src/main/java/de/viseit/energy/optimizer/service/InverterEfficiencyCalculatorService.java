package de.viseit.energy.optimizer.service;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import de.viseit.energy.optimizer.controller.dto.InverterRecord;
import de.viseit.energy.optimizer.repo.InverterEfficiencyRepository;
import de.viseit.energy.optimizer.repo.entity.InverterEfficiency;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InverterEfficiencyCalculatorService {
	private final InverterEfficiencyRepository repository;

	public void calculate(InverterRecord entry) {
		BigDecimal produced = entry.getProduced()
				.stream()
				.reduce(ZERO, BigDecimal::add);

		BigDecimal efficiency;
		if (produced.compareTo(ZERO) != 0) {
			efficiency = entry.getFeedIn().divide(produced, 5, HALF_UP);
		} else {
			efficiency = ZERO;
		}

		repository.save(InverterEfficiency.builder()
				.efficiency(efficiency)
				.produced(produced)
				.build());
	}
}
