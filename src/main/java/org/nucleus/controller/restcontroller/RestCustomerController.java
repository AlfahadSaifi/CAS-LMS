package org.nucleus.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.CityDTO;
import org.nucleus.dto.CustomerDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.service.CustomerService;
import org.nucleus.service.address.CityService;
import org.nucleus.service.address.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("maker")
public class RestCustomerController {

    private final CustomerService customerService;
    private final StateService stateService;
    private final CityService cityService;

    public RestCustomerController(CustomerService customerService, StateService stateService, CityService cityService){
        this.customerService=customerService;
        this.stateService=stateService;
        this.cityService=cityService;
    }


    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchCustomer(@RequestParam(name = "cifNumber", required = false) String cifNumber,
                                         @RequestParam(name = "loanAccountNumber", required = false) String loanAccountNumber,
                                         @RequestParam(name = "loanApplicationId", required = false) String loanApplicationId
    ) throws JsonProcessingException {
        CustomerDTO customerDto = customerService.search(cifNumber, loanAccountNumber, loanApplicationId);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(customerDto);


        return ResponseEntity.ok(jsonData);
    }


    @GetMapping(value = "/fetch-states", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetchStatesByCountry(@RequestParam("countryName") String countryName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(stateService.getStatesByCountryName(countryName));

        return ResponseEntity.ok().body(jsonData);
    }

    @GetMapping(value = "/fetch-cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetchCitiesByState(@RequestParam("stateName") String stateName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(cityService.getCityByStateName(stateName));

        return ResponseEntity.ok().body(jsonData);
    }
}
