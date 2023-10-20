package de.viseit.energy.optimizer.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PvPlant {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal declination;
    private BigDecimal azimuth;
    private BigDecimal kwPeak;
}
