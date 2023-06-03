package com.medicaments.Location;

import com.medicaments.Exceptions.LocationExistsException;
import com.medicaments.Exceptions.LocationNotFoundException;
import com.medicaments.Vehicle.Vehicle;
import com.medicaments.Vehicle.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class LocationServiceTest {

    @Mock
    private VehicleRepository vehicleRepositoryMock;
    @Mock
    private LocationRepository locationRepositoryMock;
    private LocationService locationServiceTest;
    private final Vehicle vehicle = new Vehicle(1,127,new ArrayList<>());
    private final Location location = new Location(1,new Vehicle(),"bag127",new ArrayList<>());

    @BeforeEach
    public void setUp() {
        vehicleRepositoryMock = mock(VehicleRepository.class);
        locationRepositoryMock = mock(LocationRepository.class);
        locationServiceTest = new LocationService(vehicleRepositoryMock,locationRepositoryMock);
    }
    @Test
    void getAllLocations() {
        //given
        //when
        locationServiceTest.getAllLocations();
        //then
        verify(locationRepositoryMock).findAll();
    }

    @Test
    void getAllLocationsOfVehicle() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        //when
        locationServiceTest.getAllLocationsOfVehicle(vehicle.getVehicleNumber());
        //then
        verify(vehicleRepositoryMock).findByVehicleNumber(vehicle.getVehicleNumber());
    }

    @Test
    void createLocationOfVehicle() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        //when
        locationServiceTest.createLocationOfVehicle(vehicle.getVehicleNumber(),location);

        ArgumentCaptor<Location> locationArgumentCaptor = ArgumentCaptor.forClass(Location.class);
        verify(locationRepositoryMock).save(locationArgumentCaptor.capture());
        Location capturedLocation = locationArgumentCaptor.getValue();
        //then
        assertEquals(location,capturedLocation);
    }

    @Test
    void deleteLocationOfVehicle() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        //when
        locationServiceTest.deleteLocationOfVehicle(vehicle.getVehicleNumber(), location.getPlaceOfLocation());
        //then
        verify(locationRepositoryMock).deleteByPlaceOfLocation(location.getPlaceOfLocation());
    }

    @Test
    void editVehicleLocation() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        //when
        locationServiceTest.editVehicleLocation(vehicle.getVehicleNumber(),location.getPlaceOfLocation(),"cpr127");
        //then
        assertNotEquals(location,new Location());
    }
    @Test
    void throwsExceptionWhenLocationExists() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        given(locationRepositoryMock.findByPlaceOfLocation(location.getPlaceOfLocation())).willReturn(Optional.of(location));
        //when
        //then
        assertThrows(LocationExistsException.class,()-> locationServiceTest.createLocationOfVehicle(vehicle.getVehicleNumber(),location),"location with name: " + location.getPlaceOfLocation() + " already exists");
    }
    @Test
    void throwsExceptionWhenLocationDoesNotExist() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        //when
        //then
        assertThrows(LocationNotFoundException.class,() -> locationServiceTest.editVehicleLocation(vehicle.getVehicleNumber(), location.getPlaceOfLocation(), anyString()),"location with name: " + location.getPlaceOfLocation() + " does not exist");

    }
}