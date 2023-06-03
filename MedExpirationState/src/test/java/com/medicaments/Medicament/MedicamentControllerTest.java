package com.medicaments.Medicament;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicaments.Location.Location;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MedicamentController.class)
class MedicamentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    MedicamentService medicamentService;

    private final Location location = new Location(1,new Vehicle(),"bag127",new ArrayList<>());
    private final Medicament medicament = new Medicament(1, new Location(), "Adrenaline", LocalDate.now());

    @Test
    void getAllMedicamentsOfLocation() throws Exception {
        //given
        List<Medicament> listOfMedicaments = new ArrayList<>();
        listOfMedicaments.add(medicament);
        given(medicamentService.medicamentsOfLocation(location.getPlaceOfLocation())).willReturn(listOfMedicaments);
        //when
        ResultActions response = mockMvc.perform(get("/locations/{placeOfLocation}/medicaments", location.getPlaceOfLocation()));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(listOfMedicaments.size()));
    }

    @Test
    void createMedicament() throws Exception {
        //given
        given(medicamentService.addNewMedicament(anyString(), any(Medicament.class))).willReturn(medicament);
        //when
        ResultActions response = mockMvc.perform(post("/locations/{placesOfLocation}/medicaments",location.getPlaceOfLocation())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicament)));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void deleteMedicamentByName() throws Exception {
        //given
        //when
        ResultActions response = mockMvc.perform(delete("/locations/{placesOfLocation}/medicaments/{name}",location.getPlaceOfLocation(), medicament.getName()));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateMedicamentName() throws Exception{
        //given
        //when
        //http://localhost:8080/locations/cpr128/medicaments/adrenaline?newName=dradrenaline
        ResultActions response = mockMvc.perform(put("/locations/{placesOfLocation}/medicaments/{name}?newName={newName}",location.getPlaceOfLocation(),medicament.getName(), "Atropine"));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}