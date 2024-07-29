package org.nucleus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.AddressDTO;
import org.nucleus.dto.CountryDTO;
import org.nucleus.dto.CustomerDTO;
import org.nucleus.service.CustomerService;
import org.nucleus.service.address.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/** Developer : Monu Kumar*/
@Controller
@RequestMapping("/maker")
@PreAuthorize("hasRole('MAKER')")
public class CustomerMakerController {
    private final CustomerService customerService;
    private final CountryService countryService;
    @Autowired
    public CustomerMakerController(CustomerService customerService, CountryService countryService){
        this.customerService = customerService;
        this.countryService=countryService;
    }

    // get customer search form
    @GetMapping("/applicant")
    public String searchCustomerFormRequest(){
        return "searchCustomer";
    }


    private final List<AddressDTO> addressDTOS=new ArrayList<>();

    // fetch all countries

    @ModelAttribute("countryDTOS")
    public List<CountryDTO> countryDTOS(){
        return countryService.getCountryWithApprovedStatus();
    }

    // get customer registration form
    @GetMapping("/registerForm")
    public ModelAndView registerFormRequest(){
        // make addressDTOS empty
        addressDTOS.clear();

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("customer", new CustomerDTO());
        modelAndView.addObject("addressDTOS", addressDTOS);
        modelAndView.setViewName("registerCustomerForm");

        return modelAndView;
    }


    // Register new Customer and redirect to loan form
    @PostMapping("/register")
    public ModelAndView registerCustomer(@ModelAttribute("customer") @Valid CustomerDTO customerDto,
                                   BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            // send current added address also
            return new ModelAndView("registerCustomerForm", "addressDTOS", addressDTOS);
        }

        customerDto.setAddressDTOS(addressDTOS);

        Long customerId = customerService.create(customerDto);
        addressDTOS.clear();

        return new ModelAndView("redirect:loanForm","customerId", customerId);
    }

    @PostMapping(path = "/add-address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addAddress(@RequestBody AddressDTO addressDTO) throws JsonProcessingException {
        addressDTOS.add(addressDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(addressDTO);

        return ResponseEntity.ok(jsonData);
    }

    @PostMapping("/delete-address")
    @ResponseBody
    public ResponseEntity<String> deleteAddress(@RequestParam int index) {
        if (index >= 0 && index < addressDTOS.size()) {
            addressDTOS.remove(index);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid index");
        }
    }

}
