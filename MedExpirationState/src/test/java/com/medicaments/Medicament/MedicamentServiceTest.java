package com.medicaments.Medicament;

import com.medicaments.Exceptions.MedicamentExistsException;
import com.medicaments.Exceptions.MedicamentNotFoundException;
import com.medicaments.Location.Location;
import com.medicaments.Location.LocationRepository;
import com.medicaments.Vehicle.Vehicle;
import com.medicaments.Vehicle.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MedicamentServiceTest {
    @Mock
    LocationRepository locationRepositoryMock;
    @Mock
    MedicamentRepository medicamentRepositoryMock;
    private MedicamentService medicamentServiceTest;
    private final Location location = new Location(1,new Vehicle(),"bag127",new ArrayList<>());
    private final Medicament medicament = new Medicament(1, new Location(), "Adrenaline", LocalDate.now());

    @BeforeEach
    public void setUp() {
        locationRepositoryMock = mock(LocationRepository.class);
        medicamentRepositoryMock = mock(MedicamentRepository.class);
        medicamentServiceTest = new MedicamentService(locationRepositoryMock,medicamentRepositoryMock);
    }
    @Test
    void medicamentsOfLocation() {
        //given
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        //when
        medicamentServiceTest.medicamentsOfLocation(location.getPlaceOfLocation());
        //then
        verify(locationRepositoryMock).findByPlaceOfLocation(location.getPlaceOfLocation());
    }
    @Test
    void addNewMedicament() {
        //given
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        //when
        medicamentServiceTest.addNewMedicament(location.getPlaceOfLocation(), medicament);
        ArgumentCaptor<Medicament> medicamentArgumentCaptor = ArgumentCaptor.forClass(Medicament.class);
        verify(medicamentRepositoryMock).save(medicamentArgumentCaptor.capture());
        Medicament capturedMedicament = medicamentArgumentCaptor.getValue();
        //then
        assertEquals(medicament,capturedMedicament);
    }
    @Test
    void deleteMedicament() {
        //given
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        given(medicamentRepositoryMock.findByName(medicament.getName())).willReturn(Optional.of(medicament));
        //when
        medicamentServiceTest.deleteMedicament(location.getPlaceOfLocation(), medicament.getName());
        //then
        verify(medicamentRepositoryMock).deleteByName(medicament.getName());
    }

    @Test
    void editMedicamentName() {
        //given
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        given(medicamentRepositoryMock.findByName(medicament.getName())).willReturn(Optional.of(medicament));
        //when
        medicamentServiceTest.editMedicamentName(location.getPlaceOfLocation(), medicament.getName(), "Atropine");
        //then
        assertNotEquals(medicament,new Medicament());
    }

    @Test
    void throwsMedicamentNotFoundWhenItsNotPresent() {
        //given
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        //then
        assertThrows(MedicamentNotFoundException.class,() -> medicamentServiceTest.
                deleteMedicament(location.getPlaceOfLocation(),anyString()),"medicament with name:" + medicament.getName() + " does not exist");
    }
    @Test
    void throwsMedicamentAlreadyExistsWhenExistingIsFound() {
        //given
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        given(medicamentRepositoryMock.findByName(medicament.getName())).willReturn(Optional.of(medicament));
        //when
        //then
        assertThrows(MedicamentExistsException.class, () -> medicamentServiceTest.addNewMedicament(location.getPlaceOfLocation(), medicament),"medicament with name: " + medicament.getName() + " already exists");
    }
}