package de.viseit.energy.optimizer.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class InverterRecord {
    private List<BigDecimal> produced;
    private BigDecimal feedIn;
}
