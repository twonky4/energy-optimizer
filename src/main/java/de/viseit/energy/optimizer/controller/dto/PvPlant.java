package de.viseit.energy.optimizer.controller.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PvPlant {
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal declination;
	private BigDecimal azimuth;
	private BigDecimal kwPeak;
}
