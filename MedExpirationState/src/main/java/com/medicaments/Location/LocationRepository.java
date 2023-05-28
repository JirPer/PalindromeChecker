package com.medicaments.Location;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {

    Optional<Location> findByPlaceOfLocation(String placeOfLocation);
    @Transactional
    void deleteByPlaceOfLocation(String placeOfLocation);
}
