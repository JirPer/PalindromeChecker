package com.medicaments.Location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicaments.Medicament.Medicament;
import com.medicaments.Vehicle.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Vehicle vehicle;
    @NotNull
    private String placeOfLocation;
    @OneToMany(mappedBy = "location",
              orphanRemoval = true,
              cascade = CascadeType.ALL)
    private List<Medicament> medicaments;
}
