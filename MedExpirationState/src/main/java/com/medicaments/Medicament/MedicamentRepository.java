package com.medicaments.Medicament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament,Integer> {
    Optional<Medicament> findByName(String name);
    void deleteByName(String name);

}
