package de.viseit.energy.optimizer.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viseit.energy.optimizer.controller.dto.InverterRecord;

@RestController
public class InverterController {
    @PutMapping("/api/v1/inverter")
    public void addInverterRecord(InverterRecord record) {
        // TODO save to statistics
    }
}
