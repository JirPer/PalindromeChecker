package com.medicaments.Vehicle;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
    @GetMapping("/{vehicleNumber}")
    public Vehicle getVehicleByNumber(@PathVariable Integer vehicleNumber) {
        return vehicleService.getVehicle(vehicleNumber);
    }
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleService.addNewVehicle(vehicle);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{vehicleNumber}").buildAndExpand(savedVehicle.getVehicleNumber())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/{vehicleNumber}")
    public void deleteVehicle(@PathVariable Integer vehicleNumber) {
        vehicleService.deleteVehicle(vehicleNumber);
    }
    @PutMapping("/{oldVehicleNumber}")
    public void editVehicle(@PathVariable Integer oldVehicleNumber, @RequestParam Integer vehicleNumber) {
        vehicleService.editVehicle(oldVehicleNumber, vehicleNumber);
    }
}
