package com.medicaments.Vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VehicleService vehicleService;
    @Autowired
    private ObjectMapper objectMapper;
    private final Vehicle vehicle = new Vehicle(1,127,new ArrayList<>());
    @Test
    void getAllVehicles() throws Exception {
        //given
        List<Vehicle> listOfVehicles = new ArrayList<>();
        listOfVehicles.add(vehicle);
        given(vehicleService.getAllVehicles()).willReturn(listOfVehicles);

        //when
        ResultActions response = mockMvc.perform(get("/vehicles"));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(listOfVehicles.size()));
    }

    @Test
    void getVehicleByNumber() throws Exception {
        //given
        given(vehicleService.getVehicle(vehicle.getVehicleNumber())).willReturn(vehicle);

        //when
        ResultActions response = mockMvc.perform(get("/vehicles/{vehicleNumber}",vehicle.getVehicleNumber()));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.vehicleNumber").value(127));
    }

    @Test
    void createVehicle() throws Exception{
        //given
        given(vehicleService.addNewVehicle(any(Vehicle.class))).willReturn(vehicle);

        //when
        ResultActions response = mockMvc.perform(post("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicle)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void deleteVehicle() throws Exception {
        //given
        given(vehicleService.getVehicle(vehicle.getVehicleNumber())).willReturn(vehicle);

        //when
        ResultActions response = mockMvc.perform(delete("/vehicles/{vehicleNumber}", vehicle.getVehicleNumber()));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void editVehicle() throws Exception{
        //given
        Vehicle editedVehicle = new Vehicle(vehicle.getId(),128,new ArrayList<>());
        given(vehicleService.getVehicle(vehicle.getVehicleNumber())).willReturn(vehicle);
        given(vehicleService.getVehicle(editedVehicle.getVehicleNumber())).willReturn(editedVehicle);

        //when
        ResultActions response = mockMvc.perform(put("/vehicles/128?vehicleNumber={vehicleNumber}", editedVehicle.getVehicleNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editedVehicle)));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}