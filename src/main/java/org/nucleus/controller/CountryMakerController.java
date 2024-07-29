package org.nucleus.controller;

import org.nucleus.dto.CountryDTO;
import org.nucleus.service.address.CountryService;
import org.nucleus.service.address.CountryTempService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

        /****
         Unzala
         *****/

@Controller
@RequestMapping("maker/")
@PreAuthorize("hasRole('MAKER')")
public class CountryMakerController {
    private final CountryTempService countryTempService;
    private final CountryService countryService;
    @Autowired
    public CountryMakerController(CountryTempService countryTempService , CountryService countryService){
        this.countryTempService=countryTempService;
        this.countryService = countryService;

    }
    @ModelAttribute("countryGroups")
    public List<String> countryGroups(Model model){
        List<String> countryGroups = new ArrayList<>(List.of(new String[]{"A","B","C","D","E","F","G","I","L","M","N","O","P","Q","R","S","T","U","V","W"}));
        System.out.println(countryGroups);

        return countryGroups;
    }
    @GetMapping("country-form")
    public String showCountryForm(Model model){
        model.addAttribute("countryDto" ,new CountryDTO());
        return "country-form";
    }

    //create new country
    @PostMapping("new-country")
    public ModelAndView createCountry( @ModelAttribute("countryDto")@Valid CountryDTO countryDTO,
                                      BindingResult result,
                                      @RequestParam(name = "action", required = false)
                                      String action ){
        ModelAndView mv = new ModelAndView();

        if(result.hasErrors()){
            mv.addObject("countryDTO" ,countryDTO );
            mv.setViewName("country-form");
            return mv;
        }
        if("cancel".equalsIgnoreCase(action)){
            mv.addObject("countryDTO", new CountryDTO());
            mv.setViewName("country-form");
            return mv;
        }
        String message ="Some error occurred.";

        try{
            message = countryTempService.saveCountry(countryDTO , action);
        }
        catch (NumberFormatException ex){
            result.rejectValue("countryIsdCode", "invalid.countryIsdCode","ISD Code must be an integer.");
            mv.setViewName("country-form");
            return mv;
        }
        mv.addObject("message" , message);
        mv.setViewName("country-form");
        return mv;
    }

    //display countries to the maker
    @GetMapping("show-country-list")
    public ModelAndView displayCountriesList(Model model){

        List<CountryDTO> countryDTOList = countryTempService.getAllCountries();
        List<CountryDTO> countryDTOS = countryService.getAllCountries();

        ModelAndView mv = new ModelAndView();

        //list of countries from temporary table
        mv.addObject("tempCountries", countryDTOList);

        //list of countries from permanent table
        mv.addObject("permCountries", countryDTOS);
        mv.setViewName("maker-country-list");

        return mv;
    }

    //delete country
    @PostMapping("/deleteCountry/{id}")
    public ModelAndView deleteCountry(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView();
        String message = countryTempService.deleteCountry(id);
        mv.addObject("message" , message);
        mv.setViewName("redirect:/maker/show-country-list");
        return mv;
    }

    //display update country form
    @GetMapping("show-update-country-form")
    public String showUpdateCountryForm(@RequestParam(name= "id", required = false ) Integer id ,
                                        @RequestParam(name="status" , required = false) String recordStatus,
                                        Model model){
        if(RecordStatus.valueOf(recordStatus)==RecordStatus.A){

            CountryDTO countryDTO = countryService.getCountryById(id);

            model.addAttribute("countryDTO" , countryDTO);
        }
        if(RecordStatus.valueOf(recordStatus)==RecordStatus.N || RecordStatus.valueOf(recordStatus)==RecordStatus.M){
            CountryDTO countryDTO = countryTempService.getCountryById(id);
            model.addAttribute("countryDTO", countryDTO);
        }
        return "update-country-form";
    }

    //submit updated country
    @PostMapping("edit-country")
    public String updateCountry(@ModelAttribute("countryDTO")CountryDTO countryDTO,
                                String action,
                                Model model){

        String message="";
        if("save".equalsIgnoreCase(action)){
            message = countryTempService.updateCountryForm(countryDTO);
        }
        if("cancel".equalsIgnoreCase(action)){
            return "redirect:show-country-list";
        }
        model.addAttribute("countryDto" , countryDTO);
        model.addAttribute("message", message);
        return "redirect:show-country-list";
    }


}
