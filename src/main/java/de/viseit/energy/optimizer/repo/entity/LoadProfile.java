package de.viseit.energy.optimizer.repo.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class LoadProfile {
    @NotNull
    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @NotNull
    private int hour;

    @NotNull
    private BigDecimal wattHours;

    @ManyToOne(fetch = LAZY, optional = false)
    private Device device;
}
