package com.medicaments.Medicament;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/locations")
public class MedicamentController {

    private final MedicamentService medicamentService;

    @GetMapping("/{placeOfLocation}/medicaments")
    public List<Medicament> getAllMedicamentsOfLocation(@PathVariable String placeOfLocation) {
        return medicamentService.medicamentsOfLocation(placeOfLocation);
    }

    @PostMapping("/{placeOfLocation}/medicaments")
    public ResponseEntity<Medicament> createMedicament(@PathVariable String placeOfLocation, @RequestBody Medicament medicament) {
        Medicament savedMedicament = medicamentService.addNewMedicament(placeOfLocation, medicament);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(savedMedicament.getName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{placeOfLocation}/medicaments/{name}")
    public void deleteMedicamentByName(@PathVariable String placeOfLocation, @PathVariable String name) {
        medicamentService.deleteMedicament(placeOfLocation, name);
    }

    @PutMapping("/{placeOfLocation}/medicaments/{name}")
    public void updateMedicamentName(@PathVariable String placeOfLocation, @PathVariable String name, @RequestParam String newName) {
        medicamentService.editMedicamentName(placeOfLocation, name, newName);
    }
}
