package com.medicaments.Vehicle;

import com.medicaments.Location.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Integer id;
    @NotNull
    private Integer vehicleNumber;
    @OneToMany(mappedBy = "vehicle",
               orphanRemoval = true,
               cascade = CascadeType.ALL)
    private List<Location> placeOfLocation;
}
