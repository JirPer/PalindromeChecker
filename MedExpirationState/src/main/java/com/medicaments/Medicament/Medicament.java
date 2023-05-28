package com.medicaments.Medicament;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicaments.Location.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Location location;
    private String name;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfExpiraton;
}
