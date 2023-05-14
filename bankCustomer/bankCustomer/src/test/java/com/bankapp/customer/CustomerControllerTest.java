package com.bankapp.customer;

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
@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;

    private final Customer customer = new Customer(1,"Jack", "Sparrow", new ArrayList<>(),"jack.sparrow@gmail.com",2.5);

    @Test
    void canGetAllCustomers() throws Exception {
        //given
        List<Customer> listOfCustomers = new ArrayList<>();
        listOfCustomers.add(customer);
        given(customerService.getCustomers()).willReturn(listOfCustomers);

        //when
        ResultActions response = mockMvc.perform(get("/customers"));

        //then
        response.andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(listOfCustomers.size()));
    }

    @Test
    void canRetrieveDetailsAboutCustomerWithId() throws Exception {
        //given
        given(customerService.getCustomer(customer.getId())).willReturn(customer);

        //when
        ResultActions response = mockMvc.perform(get("/customers/{cid}",customer.getId()));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.first_name").value(customer.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(customer.getLastName()))
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.balance").value(customer.getBalance()));
    }
    
    @Test
    void canCreateCustomer() throws Exception{
        //given
        given(customerService.addNewCustomer(any(Customer.class))).willReturn(customer);

        //when
        ResultActions response = mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customer)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void canDeleteCustomer() throws Exception{
        //given
        given(customerService.getCustomer(customer.getId())).willReturn(customer);

        //when
        ResultActions response = mockMvc.perform(delete("/customers/{cid}",customer.getId()));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void canEditCustomer() throws Exception{
        //given
        Customer editedCustomer = new Customer(customer.getId(), customer.getFirstName(), "Sparrowsa",customer.getAccount(), customer.getEmail(), customer.getBalance());
        given(customerService.getCustomer(customer.getId())).willReturn(customer);
        given(customerService.getCustomer(editedCustomer.getId())).willReturn(editedCustomer);

        //when
        ResultActions response = mockMvc.perform(put("/customers/{cid}", customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editedCustomer)));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}