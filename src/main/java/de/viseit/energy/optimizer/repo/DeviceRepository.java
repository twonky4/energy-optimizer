package de.viseit.energy.optimizer.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.viseit.energy.optimizer.repo.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
}
