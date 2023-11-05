package de.viseit.energy.optimizer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viseit.energy.optimizer.service.DeviceScheduleService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DeviceScheduleController {
    private final DeviceScheduleService service;

    @GetMapping(path = "/device/{name}/schedule/active")
    public boolean isDevicePlannedAsActive() {
        return false;
    }
}
