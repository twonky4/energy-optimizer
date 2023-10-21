package de.viseit.energy.optimizer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import de.viseit.energy.optimizer.repo.InverterEfficiencyRepository;
import de.viseit.energy.optimizer.repo.entity.InverterEfficiency;
import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureMockMvc
class InverterEfficiencyTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InverterEfficiencyRepository repository;

	@SneakyThrows
	@Test
	void calculateEfficiency() {
		mockMvc.perform(put("/api/v1/inverter")
				.content("{\"produced\":[5421,4862,1479],\"feedIn\":11000}")
				.contentType(APPLICATION_JSON)
				.with(httpBasic("admin", "pass")))
				.andExpect(MockMvcResultMatchers.status().isOk());

		List<InverterEfficiency> result = repository.findAll();

		assertThat(result)
				.extracting("produced", "efficiency")
				.containsExactly(tuple(BigDecimal.valueOf(0.93522d)));
	}
}
