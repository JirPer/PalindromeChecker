package com.medicaments.Vehicle;

import com.medicaments.Exceptions.VehicleExistsException;
import com.medicaments.Exceptions.VehicleNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Optional<Vehicle> vehicleOptional(Integer vehicleNumber) {
        return vehicleRepository.findByVehicleNumber(vehicleNumber);
    }
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicle(Integer vehicleNumber) {
        vehicleOptional(vehicleNumber).orElseThrow(() -> new VehicleNotFoundException("vehicle with number:" + vehicleNumber + " does not exist"));
        return vehicleOptional(vehicleNumber).get();
    }
    @Transactional
    public Vehicle addNewVehicle(Vehicle vehicle) {
        if(vehicleOptional(vehicle.getVehicleNumber()).isPresent()) {
            throw new VehicleExistsException("vehicle " + vehicle.getVehicleNumber() + " already exists");
        }
        return vehicleRepository.save(vehicle);
    }
    @Transactional
    public void deleteVehicle(Integer vehicleNumber) {
        vehicleOptional(vehicleNumber).orElseThrow(() -> new VehicleNotFoundException("vehicle " + vehicleNumber + " does not exist"));
        vehicleRepository.deleteByVehicleNumber(vehicleNumber);
    }
    @Transactional
    public void editVehicle(Integer oldVehicleNumber, Integer vehicleNumber) {
        vehicleOptional(oldVehicleNumber).orElseThrow(() -> new VehicleNotFoundException("vehicle " + vehicleNumber + " does not exist"));
        if(Objects.equals(oldVehicleNumber,vehicleNumber) || vehicleOptional(vehicleNumber).isPresent()) {
            throw new VehicleExistsException("vehicle " + vehicleNumber + " already exists");
        }
        vehicleOptional(oldVehicleNumber).get().setVehicleNumber(vehicleNumber);
    }
}
