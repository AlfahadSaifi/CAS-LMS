package org.nucleus.controller;
//Author: Ashish Goyal
import org.nucleus.dto.StateDTO;
import org.nucleus.service.address.CountryService;
import org.nucleus.service.address.StateService;
import org.nucleus.service.address.StateTempService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("checker")
@PreAuthorize("hasRole('CHECKER')")
public class StateCheckerController {
    private CountryService countryService;

    private StateService stateService;

    private StateTempService stateTempService;
    @Autowired
    public StateCheckerController(CountryService countryService, StateService stateService, StateTempService stateTempService){
        this.countryService=countryService;
        this.stateService = stateService;
        this.stateTempService = stateTempService;
    }



    @ModelAttribute("stateListForApproval")
    public List<StateDTO> states(){
        return stateService.getAllPendingStates();
    }

    @GetMapping("state-form")
    public String displayStateForm(Model model){

        model.addAttribute("stateDto",new StateDTO());
        return "display-state-requests";
    }

    @GetMapping("/approveState/{id}")
    public String approveCity(Model model, @PathVariable Integer id) {

        StateDTO stateDTO = stateTempService.getStateDtoById(id);


        try {
            if (stateDTO != null && (stateDTO.getMetaDataTemp().getRecordStatus() == RecordStatus.N || stateDTO.getMetaDataTemp().getRecordStatus() == RecordStatus.M || stateDTO.getMetaDataTemp().getRecordStatus() == RecordStatus.D)) {
                stateService.approveState(stateDTO);
            }
            return "redirect:../state-form";

        } catch (Exception e) {
            e.printStackTrace();
            return "../views/error";
        }
    }

    @GetMapping("/rejectState/{id}")
    public String rejectCity(@PathVariable Integer id) {

        StateDTO stateDTO = stateTempService.getStateDtoById(id);
        try {
            if (stateDTO != null && stateDTO.getMetaDataTemp().getRecordStatus() == RecordStatus.N) {
                stateDTO.getMetaDataTemp().setRecordStatus(RecordStatus.NR);
            }
            else if (stateDTO != null && stateDTO.getMetaDataTemp().getRecordStatus() == RecordStatus.M) {
                stateDTO.getMetaDataTemp().setRecordStatus(RecordStatus.MR);
            }
            else {
                stateDTO.getMetaDataTemp().setRecordStatus(RecordStatus.DR);
            }
            stateTempService.updateState(stateDTO);

            return "redirect:../state-form";
        }catch (Exception e)
        {
            e.printStackTrace();
            return "../views/error";
        }

    }





}
