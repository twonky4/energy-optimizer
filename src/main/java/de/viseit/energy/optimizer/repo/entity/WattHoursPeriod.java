package de.viseit.energy.optimizer.repo.entity;

import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
@AllArgsConstructor(access = PRIVATE)
public class WattHoursPeriod {
	@NotNull
	@Id
	@GeneratedValue(strategy = UUID)
	private UUID id;
	@NotNull
	@Column(unique = true)
	private ZonedDateTime time;
	@NotNull
	@DecimalMin("0")
	@DecimalMax("999999")
	@Digits(integer = 6, fraction = 0)
	private BigDecimal productionValue;
}
