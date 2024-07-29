package org.nucleus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.CityDTO;
import org.nucleus.dto.CountryDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.address.CityService;
import org.nucleus.service.address.CityTempService;
import org.nucleus.service.address.CountryService;
import org.nucleus.service.address.StateService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

//Unzala
@Controller
@RequestMapping("maker/")
@PreAuthorize("hasRole('MAKER')")
public class CityMakerController {
    @Autowired
    private CityTempService cityTempService;

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private StateService stateService;
                        /****
                         * UNZALA
                         * ***/


    @GetMapping(value = "/getStates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getStatesByCountry(@RequestParam("countryName") String countryName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(stateService.getStatesByCountryName(countryName));
        return ResponseEntity.ok(jsonData);
    }

    @ModelAttribute("countries")
    public List<CountryDTO> getCountries(Model model){
        List<CountryDTO> countryDTOS= countryService.getCountryWithApprovedStatus();
        return countryDTOS;
    }


    @GetMapping("city-form")
    public ModelAndView showCityForm(){
        CityDTO cityDTO = new CityDTO();
        ModelAndView modelAndView=new ModelAndView();

        modelAndView.addObject("cityDTO", cityDTO);
        modelAndView.setViewName("city-form");
        return modelAndView;
    }

    @PostMapping("new-city")
    public ModelAndView saveCity(@ModelAttribute("cityDTO") @Valid CityDTO cityDto,
                                 BindingResult errors, @RequestParam(value = "action") String action ){
        ModelAndView mv = new ModelAndView();

        if(errors.hasErrors())
        {
            mv.addObject("cityDTO",cityDto);
            mv.setViewName("city-form");
            return mv;
        }
        if("cancel".equalsIgnoreCase(action)){
            mv.setViewName("redirect:/city-form");
            return mv;
        }
        String message ="Some error occurred.";
        message = cityTempService.saveCity(cityDto , action);

        mv.addObject("message" , message);
        mv.setViewName("city-form");
        return mv;

    }

    @GetMapping("show-city-list")
    public String displayCitiesList(Model model ){

        List<CityDTO> cityTemps = cityTempService.getAllCities();
        List<CityDTO> cityPerms = cityService.getAllCities();

        model.addAttribute("cityTemps" , cityTemps);
        model.addAttribute("cityPerms", cityPerms);

        return "maker-city-list";


    }

    @GetMapping("delete-city")
    public ModelAndView deleteCity(@RequestParam("id")Integer id,
                                   @RequestParam("status")String recordStatus) {
        ModelAndView mv = new ModelAndView();
        String message = cityTempService.deleteCity(id, recordStatus);

        mv.addObject("message" , message);
        mv.setViewName("redirect:/maker/show-city-list");
        return mv;
    }

    @GetMapping("show-update-city-form")
    public String showUpdateCityForm(@RequestParam(name="id", required = false) Integer id,
                                     @RequestParam(name="status", required = false)String recordStatus,
                                     Model model){



        if(RecordStatus.valueOf(recordStatus)==RecordStatus.A){

            CityDTO cityDTO = cityService.getCityById(id);

            model.addAttribute("cityDTO" ,cityDTO);

        }
        if(RecordStatus.valueOf(recordStatus)==RecordStatus.N || RecordStatus.valueOf(recordStatus)==RecordStatus.M){
            CityDTO cityDTO =cityTempService.getCityById(id);
            model.addAttribute("cityDTO",cityDTO);
        }

        return "update-city-form";

    }


    @GetMapping("edit-city")
    public String updateCity(@ModelAttribute("cityDTO")CityDTO cityDTO,
                             String action,
                             Model model){
        String message="";
        if("save".equalsIgnoreCase(action)){
            message = cityTempService.updateCityForm(cityDTO);
        }
        if("cancel".equalsIgnoreCase(action)){
            return "redirect:show-update-city-form";
        }
        model.addAttribute("cityDTO" , cityDTO);
        model.addAttribute("message", message);

        return "redirect:show-city-list";
    }




}
