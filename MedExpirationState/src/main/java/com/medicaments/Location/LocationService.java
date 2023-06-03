package com.medicaments.Location;

import com.medicaments.Exceptions.LocationExistsException;
import com.medicaments.Exceptions.LocationNotFoundException;
import com.medicaments.Exceptions.VehicleNotFoundException;
import com.medicaments.Vehicle.Vehicle;
import com.medicaments.Vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationService {

    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public List<Location> getAllLocationsOfVehicle(Integer vehicleNumber) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        vehicleOptional.orElseThrow(() -> new VehicleNotFoundException("vehicle with number:" + vehicleNumber + " does not exist"));

        return vehicleOptional.get().getPlaceOfLocation();
    }

    @Transactional
    public Location createLocationOfVehicle(Integer vehicleNumber, Location location) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        Optional<Location> locationOptional = locationRepository.findByPlaceOfLocation(location.getPlaceOfLocation());

        vehicleOptional.orElseThrow(() -> new VehicleNotFoundException("vehicle with number:" + vehicleNumber + " does not exist"));
        if(locationOptional.isPresent()){
            throw new LocationExistsException("location with name: " + location.getPlaceOfLocation() + " already exists");
        }
        location.setVehicle(vehicleOptional.get());
        return locationRepository.save(location);
    }
    @Transactional
    public void deleteLocationOfVehicle(Integer vehicleNumber, String placeOfLocation) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        vehicleOptional.orElseThrow(() -> new VehicleNotFoundException("vehicle with number:" + vehicleNumber + " does not exist"));
        Optional<Location> locationOptional = locationRepository.findByPlaceOfLocation(placeOfLocation);
        locationOptional.orElseThrow(() -> new LocationNotFoundException("location with name: " + placeOfLocation + " does not exist"));

        locationRepository.deleteByPlaceOfLocation(placeOfLocation);
    }

    @Transactional
    public void editVehicleLocation(Integer vehicleNumber, String placeOfLocation, String newPlaceOfLocation) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        vehicleOptional.orElseThrow(() -> new VehicleNotFoundException("vehicle with number: " + vehicleNumber + " does not exist"));
        Optional<Location> locationOptional = locationRepository.findByPlaceOfLocation(placeOfLocation);
        locationOptional.orElseThrow(() -> new LocationNotFoundException("location with name: " + placeOfLocation + " does not exist"));

        if(!Objects.equals(placeOfLocation, newPlaceOfLocation)) {
            locationOptional.get().setPlaceOfLocation(newPlaceOfLocation);
        }
    }

}
