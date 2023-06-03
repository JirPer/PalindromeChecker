package com.medicaments.Location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicaments.Vehicle.Vehicle;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = LocationController.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationService locationService;
    @Autowired
    private ObjectMapper objectMapper;
    private final Vehicle vehicle = new Vehicle(1,127,new ArrayList<>());
    private final Location location = new Location(1,new Vehicle(),"bag127",new ArrayList<>());

    @Test
    void getAllLocation() throws Exception {
        //given
        List<Location> listOfLocations = new ArrayList<>();
        listOfLocations.add(location);
        given(locationService.getAllLocations()).willReturn(listOfLocations);
        //when
        ResultActions response = mockMvc.perform(get("/vehicles/locations"));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(listOfLocations.size()));
    }
    @Test
    void getAllVehicleLocation() throws Exception {
        //given
        List<Location> listOfLocations = new ArrayList<>();
        listOfLocations.add(location);
        vehicle.setPlaceOfLocation(listOfLocations);
        given(locationService.getAllLocationsOfVehicle(vehicle.getVehicleNumber()))
                .willReturn(vehicle.getPlaceOfLocation());
        //when
        ResultActions response = mockMvc.perform(get("/vehicles/{vehicleNumber}/locations",
                vehicle.getVehicleNumber()));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(listOfLocations.size()));
    }

    @Test
    void createLocation() throws Exception {
        //given
        given(locationService.createLocationOfVehicle(anyInt(),any(Location.class))).willReturn(location);

        //when
        ResultActions response = mockMvc.perform(post("/vehicles/{vehicleNumber}/locations",
                vehicle.getVehicleNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteLocation() throws Exception {
        //given
        //when
        ResultActions response = mockMvc.perform(delete("/vehicles/{vehicleNumber}/locations/" +
                "{placeOfLocation}",vehicle.getVehicleNumber(),location.getPlaceOfLocation()));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void editVehicleLocation() throws Exception {
        //given
        //when
        ResultActions response = mockMvc.perform(put("/vehicles/{vehicleNumber}/locations/{placeOfLocation}" +
                "?newPlaceOfLocation={newPlaceOfLocation}",vehicle.getVehicleNumber(),
                location.getPlaceOfLocation(), "cpr127"));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}