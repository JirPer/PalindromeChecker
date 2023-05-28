package com.medicaments.Location;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/locations")
    public List<Location> getAllLocation() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{vehicleNumber}/locations")
    public List<Location> getAllVehicleLocation(@PathVariable Integer vehicleNumber) {
        return locationService.getAllLocationsOfVehicle(vehicleNumber);
    }

    @PostMapping("/{vehicleNumber}/locations")
    public ResponseEntity<Location> createLocation(@PathVariable Integer vehicleNumber, @Valid @RequestBody Location location) {
        Location savedLocation = locationService.createLocationOfVehicle(vehicleNumber, location);

        URI uriLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{placeOfLocation}")
                .buildAndExpand(savedLocation.getPlaceOfLocation())
                .toUri();

        return ResponseEntity.created(uriLocation).build();
    }

    @DeleteMapping("/{vehicleNumber}/locations/{placeOfLocation}")
    public void deleteLocation(@PathVariable Integer vehicleNumber, @PathVariable String placeOfLocation) {
        locationService.deleteLocationOfVehicle(vehicleNumber, placeOfLocation);
    }

    @PutMapping("/{vehicleNumber}/locations/{placeOfLocation}")
    public void editVehicleLocation(@PathVariable Integer vehicleNumber, @PathVariable String placeOfLocation, @RequestParam String newPlaceOfLocation) {
        locationService.editVehicleLocation(vehicleNumber, placeOfLocation, newPlaceOfLocation);
    }
}
