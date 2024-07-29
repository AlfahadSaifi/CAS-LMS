package org.nucleus.controller;

import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.ChargeDefinitionTempDTO;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;
import org.nucleus.service.ChargeDefinitionService;
import org.nucleus.service.ChargeDefinitionTempService;
import org.nucleus.utility.dtomapper.ChargeDefinitionDTOToChargeDefinitionTempDTOMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


//AUTHOR: HARKIRAT SINGH
@Controller
@PreAuthorize("hasRole('CHECKER')")
@RequestMapping("checker")
public class ChargeDefinitionCheckerController {
    @Autowired
    ChargeDefinitionTempService chargeDefinitionTempService;
    @Autowired
    ChargeDefinitionService chargeDefinitionService;
    @Autowired
    ChargeDefinitionDTOToChargeDefinitionTempDTOMapper tempEntityToEntityMapper;
    @GetMapping("charge-definition")
    public String goToCheckerForm(Model model){
        List<ChargeDefinitionTempDTO> chargeDefinitionList = chargeDefinitionTempService.fetchChargeDefinitionFromTemporaryTable();
        String errorMessage = (String) model.getAttribute("errorMessage");
        String rejectMessage = (String) model.getAttribute("rejectMessage");
        String approvedMessage = (String) model.getAttribute("approvedMessage");
        if(errorMessage!=null){
            model.addAttribute("errorMessage",errorMessage);
        }
        if(rejectMessage!=null){
            model.addAttribute("rejectMessage",rejectMessage);
        }
        if(approvedMessage!=null){
            model.addAttribute("approvedMessage",approvedMessage);
        }
        if(chargeDefinitionList!=null) {
            model.addAttribute("chargeDefinitionData", chargeDefinitionList);
        }else{
            model.addAttribute("chargeDefinitionData",new ArrayList<>());
        }
        return "chargeFormChecker";
    }
    @GetMapping("approveChargeDefinitionRequest")
    public String approveChargeDefinition(@RequestParam(value = "code") String chargeDefinitionCode,RedirectAttributes redirectAttributes){
        ChargeDefinitionTempDTO chargeDefinitionTempDTO = chargeDefinitionTempService.approveChargeDefinition(chargeDefinitionCode);
        if(chargeDefinitionTempDTO!=null && (chargeDefinitionTempDTO.getMetaData().getRecordStatus()== RecordStatus.N || chargeDefinitionTempDTO.getMetaData().getRecordStatus()==RecordStatus.M)){
            ChargeDefinitionDTO chargeDefinitionDTO = tempEntityToEntityMapper.chargeDefinitionTempDTOToChargeDefinitionDTOConvertor(chargeDefinitionTempDTO);


            try {
                chargeDefinitionDTO.getMetaData().setRecordStatus(RecordStatus.A);
                chargeDefinitionService.saveNewRecordToMasterTable(chargeDefinitionDTO);
                redirectAttributes.addFlashAttribute("approvedMessage","Request Approved");
            } catch (ChargeDefinitionCodeAlreadyExist e) {
                redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());

            }
        }else if(chargeDefinitionTempDTO!=null && (chargeDefinitionTempDTO.getMetaData().getRecordStatus()==RecordStatus.D)){
            ChargeDefinitionDTO chargeDefinitionDTO =  tempEntityToEntityMapper.chargeDefinitionTempDTOToChargeDefinitionDTOConvertor(chargeDefinitionTempDTO);
            chargeDefinitionService.deleteSavedRecordFromTheMasterTable(chargeDefinitionDTO);
        }

        return "redirect:/checker/charge-definition";
    }
    @GetMapping("rejectChargeDefinitionRequest")
    public String rejectChargeDefinition(@RequestParam(value = "code") String chargeDefinitionCode,RedirectAttributes redirectAttributes){
        if(chargeDefinitionTempService.rejectChargeDefinition(chargeDefinitionCode)){
            redirectAttributes.addFlashAttribute("rejectMessage","Request Rejected");
        }

        return "redirect:/checker/charge-definition";

    }

}
