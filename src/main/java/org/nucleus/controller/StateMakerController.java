package org.nucleus.controller;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.nucleus.dto.CountryDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.service.address.CountryService;
import org.nucleus.service.address.StateService;
import org.nucleus.service.address.StateTempService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
//Author: Ashish Goyal

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("maker")
@PreAuthorize("hasRole('MAKER')")
@DynamicInsert
@DynamicUpdate
public class StateMakerController {


    private final StateTempService stateTempService;
    private final StateService stateService;
    private final CountryService countryService;

    @Autowired
    public StateMakerController(StateTempService stateTempService, CountryService countryTempService, StateService stateService) {
        this.stateTempService = stateTempService;
        this.countryService = countryTempService;
        this.stateService = stateService;
    }

    @ModelAttribute("stateListAll")
    public List<StateDTO> statesList() {
        return stateService.getAllStates();
    }


    @ModelAttribute("countries")
    public List<CountryDTO> countryNames() {
        return countryService.getAllCountries();
    }


    @GetMapping("/state-form")
    public String showStateForm(Model model) {
        model.addAttribute("stateDto", new StateDTO());
        return "state-form";
    }

    @PostMapping("/submit-state-form")
    public ModelAndView createState(@ModelAttribute("stateDto") @Valid StateDTO stateDto, BindingResult errors,
                                    @RequestParam(name = "saveButton", required = false) String approvalRequest
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        ModelAndView mv = new ModelAndView();

        if(errors.hasErrors())
        {
            mv.addObject("stateDto",stateDto);
            mv.setViewName("state-form");
           return mv;
        }
        String message = "message";

        if (stateTempService.doesStateExist(stateDto.getStateName())) {
            mv.addObject(message, "State already exists!");

        } else {
            if (approvalRequest.equalsIgnoreCase("saveAndRequest")) {


                stateDto.getMetaDataTemp().setCreatedBy(username);
                stateDto.getMetaDataTemp().setCreationDate(Date.valueOf(LocalDate.now()));

                stateDto.getMetaDataTemp().setSaveFlag(Boolean.FALSE);
                stateDto.getMetaDataTemp().setRecordStatus(RecordStatus.N);

                if (stateTempService.saveState(stateDto)) {
                    mv.addObject(message, "State form submitted and request sent for approval");
                } else {
                    mv.addObject(message, "State cannot be saved.");
                }

            }
            if (approvalRequest.equalsIgnoreCase("save")) {



                stateDto.getMetaDataTemp().setCreatedBy(username);
                stateDto.getMetaDataTemp().setCreationDate(Date.valueOf(LocalDate.now()));
                stateDto.getMetaDataTemp().setSaveFlag(Boolean.TRUE);
                stateDto.getMetaDataTemp().setRecordStatus(RecordStatus.N);



                if (stateTempService.saveState(stateDto)) {
                    mv.addObject(message, "State form submitted ");
                } else {
                    mv.addObject(message, "State cannot be saved.");
                }
            }
        }

        mv.setViewName("state-form");
        return mv;

    }

    @ModelAttribute("stateList")
    public List<StateDTO> states() {
        return stateTempService.getAllStates();
    }

    @GetMapping("/edit-form")
    public String displayStateForm(Model model) {

        model.addAttribute("stateDto", new StateDTO());
        return "view-states";
    }

    @GetMapping("/editApprovedState/{id}")
    public String editState(Model model, @PathVariable Integer id) {
        StateDTO stateDto = stateService.getStateDtoById(id);
        if(stateDto==null)
        {
            stateDto = stateTempService.getStateDtoById(id);
        }
        stateDto.setId(id);
        model.addAttribute("stateDto",stateDto);
        return "edit-state-form";
    }

    @PostMapping("edit-state-form/{id}")
    public String editState(@ModelAttribute("stateDto") StateDTO stateDto, @RequestParam(name = "saveButton", required = false) String approvalRequest, @PathVariable Integer id) {
        StateDTO stateServiceFromId = stateService.getStateDtoById(id);
        if(stateServiceFromId==null)
        {
            stateServiceFromId = stateTempService.getStateDtoById(id);
        }

        stateDto.setMetaDataTemp(stateServiceFromId.getMetaDataTemp());
        if (approvalRequest.equalsIgnoreCase("saveAndRequest")) {
            if(RecordStatus.A.equals(stateDto.getMetaDataTemp().getRecordStatus()))
            {
                stateDto.getMetaDataTemp().setRecordStatus(RecordStatus.M);
                stateDto.getMetaDataTemp().setSaveFlag(Boolean.FALSE);
            }
            else
            {

                stateTempService.deleteState(id);
                stateDto.getMetaDataTemp().setRecordStatus(RecordStatus.N);

            }
            stateDto.getMetaDataTemp().setSaveFlag(Boolean.FALSE);
            stateTempService.saveState(stateDto);
        }
        return "redirect:../edit-form";
    }

    @GetMapping("/deleteApprovedState/{id}")
    public String deleteState(@PathVariable Integer id) {

        StateDTO stateDTO = stateTempService.getStateDtoById(id);

        try {
            if(stateDTO!=null) {
                stateTempService.deleteState(id);
            }

            else{
                stateDTO=stateService.getStateDtoById(id);
                stateDTO.getMetaDataTemp().setRecordStatus(RecordStatus.D);
                stateTempService.saveState(stateDTO);
            }


            return "redirect:/maker/edit-form";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

}






