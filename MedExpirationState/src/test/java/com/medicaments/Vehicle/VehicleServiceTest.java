package com.medicaments.Vehicle;

import com.medicaments.Exceptions.VehicleExistsException;
import com.medicaments.Exceptions.VehicleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepositoryMock;
    private VehicleService vehicleServiceTest;
    private final Vehicle vehicle = new Vehicle(1,127,new ArrayList<>());

    @BeforeEach
    public void setUp() {
        vehicleRepositoryMock = mock(VehicleRepository.class);
        vehicleServiceTest = new VehicleService(vehicleRepositoryMock);
    }
    @Test
    void getAllVehicles() {
        //given
        //when
        vehicleServiceTest.getAllVehicles();
        //then
        verify(vehicleRepositoryMock).findAll();
    }

    @Test
    void getVehicle() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        //when
        vehicleServiceTest.getVehicle(vehicle.getVehicleNumber());
        //then
        verify(vehicleRepositoryMock,times(2)).findByVehicleNumber(vehicle.getVehicleNumber());
    }

    @Test
    void addNewVehicle() {
        //given
        //when
        vehicleServiceTest.addNewVehicle(vehicle);
        ArgumentCaptor<Vehicle> vehicleArgumentCaptor = ArgumentCaptor.forClass(Vehicle.class);
        verify(vehicleRepositoryMock).save(vehicleArgumentCaptor.capture());
        Vehicle capturedVehicle = vehicleArgumentCaptor.getValue();
        //then
        assertEquals(vehicle,capturedVehicle);
    }

    @Test
    void deleteVehicle() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        //when
        vehicleServiceTest.deleteVehicle(vehicle.getVehicleNumber());
        //then
        verify(vehicleRepositoryMock).deleteByVehicleNumber(vehicle.getVehicleNumber());
    }

    @Test
    void editVehicle() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        //when
        vehicleServiceTest.editVehicle(vehicle.getVehicleNumber(), 157);
        //then
        assertNotEquals(vehicle, new Vehicle());
    }

    @Test
    void throwsExceptionWhenVehicleNumberDoesNotExist() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(128)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(VehicleNotFoundException.class,() -> vehicleServiceTest.getVehicle(128),"vehicle with number:" + vehicle.getVehicleNumber() + " does not exist");
    }

    @Test
    void throwsExceptionWhenVehicleNumberAlreadyExists() {
        //given
        given(vehicleRepositoryMock.findByVehicleNumber(vehicle.getVehicleNumber())).willReturn(Optional.of(vehicle));
        //when
        Vehicle vehicle2 = new Vehicle(2,127,new ArrayList<>());
        //then
        assertThrows(VehicleExistsException.class,() -> vehicleServiceTest.addNewVehicle(vehicle2),"vehicle " + vehicle2.getVehicleNumber() + " already exists");
    }
}