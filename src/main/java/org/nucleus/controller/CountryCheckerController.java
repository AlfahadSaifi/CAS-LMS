package org.nucleus.controller;

import org.nucleus.dto.CountryDTO;
import org.nucleus.service.address.CountryService;
import org.nucleus.service.address.CountryTempService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
            /**
             * UNZALA
             **/
@Controller
@RequestMapping("checker")
@PreAuthorize("hasRole('CHECKER')")
public class CountryCheckerController {

    private final CountryService countryService;

    private final CountryTempService countryTempService;
    @Autowired
    public CountryCheckerController(CountryService countryService , CountryTempService countryTempService){
        this.countryTempService =countryTempService;
        this.countryService= countryService;
    }
    @ModelAttribute("countryListForApproval")
    public List<CountryDTO> countries(){
        return countryTempService.getCountriesWithSaveFlag();
    }

    @GetMapping("country-form")
    public String displayCountryForm(Model model){
        CountryDTO countryDTO = new CountryDTO();
        model.addAttribute(countryDTO);
        return "display-country-requests";
    }

    @PostMapping("/approve/{id}")
    public ModelAndView approveCountry(@PathVariable Integer id) {

        ModelAndView mv = new ModelAndView();
        String message =countryService.approveCountry(id);
        mv.addObject("message", message);
        mv.setViewName("redirect:/checker/country-form");
        return mv;
    }

    @GetMapping("/reject/{id}")
    public ModelAndView rejectCountry( @PathVariable Integer id){

        System.out.println("CONTROLLER" + id);
        ModelAndView mv = new ModelAndView();
        String message =countryService.rejectCountry(id);
        mv.addObject("message", message);
        mv.setViewName("redirect:/checker/country-form");
        return mv;

    }




}

