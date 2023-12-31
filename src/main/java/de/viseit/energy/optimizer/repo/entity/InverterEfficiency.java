package de.viseit.energy.optimizer.repo.entity;

import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.math.BigDecimal;
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
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table
@AllArgsConstructor(access = PRIVATE)
public class InverterEfficiency {
	@NotNull
	@Id
	@GeneratedValue(strategy = UUID)
	private UUID id;
	@Column(unique = true)
	@NotNull
	@DecimalMin("0")
	@DecimalMax("20000")
	@Digits(integer = 5, fraction = 0)
	private BigDecimal produced;
	@NotNull
	@DecimalMin("0")
	@DecimalMax("1")
	@Digits(integer = 1, fraction = 5)
	private BigDecimal efficiency;
}
