package org.nucleus.controller;
//Author: Ashish Goyal

import org.nucleus.dto.CityDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.service.address.CityService;
import org.nucleus.service.address.CityTempService;
import org.nucleus.service.address.StateService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("checker")
@PreAuthorize("hasRole('CHECKER')")
public class CityCheckerController {
    @Autowired
    private CityTempService cityTempService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @ModelAttribute("citiesTemp")
    public List<CityDTO> getCitiesList() {
        return cityTempService.getPendingCities();
    }

    @ModelAttribute("citiesPerm")
    public List<CityDTO> getPermCitiesList() {
        return cityService.getAllCities();
    }

    @GetMapping("/city-form")
    public String showCitiesList(Model model) {
        model.addAttribute("cityDto",new CityDTO());
        return "display-city-requests";
    }


    @GetMapping("/approveCity/{id}")
    public String approveCity(Model model, @PathVariable Integer id) {

        CityDTO cityDTO = cityTempService.getCityById(id);

        System.out.println(cityDTO);

        try {
            if (cityDTO != null && (cityDTO.getMetaData().getRecordStatus() == RecordStatus.N || cityDTO.getMetaData().getRecordStatus() == RecordStatus.M || cityDTO.getMetaData().getRecordStatus() == RecordStatus.D)) {
                cityService.approveCity(cityDTO);
            }

            return "redirect:/checker/city-form";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/rejectCity/{id}")
    public String rejectCity(@PathVariable Integer id) {

        CityDTO cityDTO = cityTempService.getCityById(id);
        System.out.println("line 77: "+cityDTO);
        try {
            if (cityDTO != null && cityDTO.getMetaData().getRecordStatus() == RecordStatus.N) {
                cityDTO.getMetaData().setRecordStatus(RecordStatus.NR);
                System.out.println("line 81: "+cityDTO);

            } else if (cityDTO != null && cityDTO.getMetaData().getRecordStatus() == RecordStatus.M) {
                cityDTO.getMetaData().setRecordStatus(RecordStatus.MR);
            } else {
               assert cityDTO != null;
               cityDTO.getMetaData().setRecordStatus(RecordStatus.DR);
            }
            System.out.println("line 89: "+cityDTO);
            cityTempService.updateCity(cityDTO);
            return "redirect:/checker/city-form";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }


    }
}
