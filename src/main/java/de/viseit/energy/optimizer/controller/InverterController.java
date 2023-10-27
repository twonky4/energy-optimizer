package de.viseit.energy.optimizer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viseit.energy.optimizer.controller.dto.InverterRecord;
import de.viseit.energy.optimizer.service.InverterEfficiencyService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class InverterController {
	private final InverterEfficiencyService service;

	@PostMapping(path = "/inverter")
	public void addInverterRecord(@RequestBody InverterRecord entry) {
		service.add(entry);
	}
}
