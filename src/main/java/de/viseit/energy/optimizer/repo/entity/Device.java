package de.viseit.energy.optimizer.repo.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Device {
    @NotNull
    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    @NotNull
    private int prio;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "device", fetch = LAZY)
    private List<LoadProfile> loadProfile;
}
