package com.medicaments.Vehicle;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findByVehicleNumber(Integer vehicleNumber);
    @Transactional
    void deleteByVehicleNumber(Integer vehicleNumber);
}
