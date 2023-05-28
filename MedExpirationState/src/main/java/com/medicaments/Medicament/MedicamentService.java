package com.medicaments.Medicament;

import com.medicaments.Exceptions.LocationExistsException;
import com.medicaments.Exceptions.LocationNotFoundException;
import com.medicaments.Exceptions.MedicamentExistsException;
import com.medicaments.Exceptions.MedicamentNotFoundException;
import com.medicaments.Location.Location;
import com.medicaments.Location.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicamentService {

    private final LocationRepository locationRepository;
    private final MedicamentRepository medicamentRepository;

    public List<Medicament> medicamentsOfLocation(String placeOfLocation) {
        Optional<Location> locationOptional = locationRepository.findByPlaceOfLocation(placeOfLocation);
        locationOptional.orElseThrow(() -> new LocationNotFoundException("location with name:" + placeOfLocation + " does not exist"));
        return locationOptional.get().getMedicaments();
    }
    @Transactional
    public Medicament addNewMedicament(String placeOfLocation, Medicament medicament) {
        Optional<Location> locationOptional = locationRepository.findByPlaceOfLocation(placeOfLocation);
        locationOptional.orElseThrow(() -> new LocationNotFoundException("location with name:" + placeOfLocation + " does not exist"));
        Optional<Medicament> medicamentOptional = medicamentRepository.findByName(medicament.getName());
        if(medicamentOptional.isPresent()) {
            throw new MedicamentExistsException("medicament with name: " + medicament.getName() + " already exists");
        }
        medicament.setLocation(locationOptional.get());
        return medicamentRepository.save(medicament);
    }
    @Transactional
    public void deleteMedicament(String placeOfLocation, String name) {
        Optional<Location> locationOptional = locationRepository.findByPlaceOfLocation(placeOfLocation);
        locationOptional.orElseThrow(() -> new LocationExistsException("location with name:" + placeOfLocation + " does not exist"));
        Optional<Medicament> medicamentOptional = medicamentRepository.findByName(name);
        medicamentOptional.orElseThrow(() -> new MedicamentNotFoundException("medicament with name:" + name + " does not exist"));

        medicamentRepository.deleteByName(medicamentOptional.get().getName());
    }
    @Transactional
    public void editMedicamentName(String placeOfLocation, String name, String newName) {
        Optional<Location> locationOptional = locationRepository.findByPlaceOfLocation(placeOfLocation);
        locationOptional.orElseThrow(() -> new MedicamentNotFoundException("medicament with name:" + name + " does not exist"));
        Optional<Medicament> medicamentOptional = medicamentRepository.findByName(name);

        if(!Objects.equals(name, newName)) {
            medicamentOptional.get().setName(newName);
        }
    }
}
