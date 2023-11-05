package de.viseit.energy.optimizer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.viseit.energy.optimizer.repo.InverterEfficiencyRepository;
import de.viseit.energy.optimizer.repo.entity.InverterEfficiency;

@SpringBootTest
class InverterEfficiencyServiceTest {
    @Autowired
    private InverterEfficiencyRepository repository;
    @Autowired
    private InverterEfficiencyService service;

    @Test
    void calulatedEfficiencyByExact() {
        repository.save(InverterEfficiency.builder()
                .efficiency(BigDecimal.valueOf(0.2))
                .produced(BigDecimal.valueOf(1000))
                .build());

        BigDecimal calulated = service.apply(BigDecimal.valueOf(1000));
        assertThat(calulated).isEqualByComparingTo(BigDecimal.valueOf(200));
    }

    @Test
    void calulatedEfficiencyByRange() {
        repository.save(InverterEfficiency.builder()
                .efficiency(BigDecimal.valueOf(0.5))
                .produced(BigDecimal.valueOf(11000))
                .build());
        repository.save(InverterEfficiency.builder()
                .efficiency(BigDecimal.valueOf(0.7))
                .produced(BigDecimal.valueOf(18000))
                .build());

        BigDecimal calulated = service.apply(BigDecimal.valueOf(17000));
        assertThat(calulated).isEqualByComparingTo(BigDecimal.valueOf(12541));
    }
}
