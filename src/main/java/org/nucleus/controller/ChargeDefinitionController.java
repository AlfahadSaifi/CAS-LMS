package org.nucleus.controller;

import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.ChargeDefinitionTempDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;
import org.nucleus.service.ChargeDefinitionService;
import org.nucleus.service.ChargeDefinitionTempService;
import org.nucleus.utility.dtomapper.ChargeDefinitionDTOToChargeDefinitionTempDTOMapper;
import org.nucleus.utility.enums.RecordStatus;
//import org.nucleus.utility.validations.ChargeDefinitionValidation;
import org.nucleus.utility.validations.ChargeDefinitionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//AUTHOR: HARKIRAT SINGH
@Controller
@PreAuthorize("hasRole('MAKER')")
@RequestMapping("maker")
public class ChargeDefinitionController {
    @Autowired
    ChargeDefinitionTempService chargeDefinitionTempService;
    @Autowired
    ChargeDefinitionService chargeDefinitionService;
    TempMetaData tempMetaData;
    @Autowired
    ChargeDefinitionDTOToChargeDefinitionTempDTOMapper tempEntityToEntityMapper;

    @GetMapping("charge-definition")
    public String showChargeForm(Model model) {
        // Fetch the list of charge definitions from the service
        List<ChargeDefinitionTempDTO> chargeDefinitionTempDTOList = chargeDefinitionTempService.fetchDetailsByFlag();
        List<ChargeDefinitionDTO> chargeDefinitions = chargeDefinitionService.fetchDetailsFromTheMasterTable();
        if(chargeDefinitionTempDTOList!=null && chargeDefinitions!=null) {
            chargeDefinitions = chargeDefinitions.stream().filter(
                    charge -> !chargeDefinitionTempDTOList.stream().anyMatch(chargeTemp -> chargeTemp.getChargeDefinitionCode().equals(charge.getChargeDefinitionCode())
                    )).collect(Collectors.toList());

        }

        // Add the list of charge definitions to the model
        model.addAttribute("chargeDefinitionData", chargeDefinitionTempDTOList);
        model.addAttribute("chargeDefinitionsDataFromMasterTable",chargeDefinitions);
        if(model.containsAttribute("messageWhileUpdate")){
            model.addAttribute("messageWhileUpdate","Update Successfully");
        }
        if(model.containsAttribute("messageDeleteRequest")){
            model.addAttribute("messageDeleteRequest","Delete Request Successfully Run");
        }
        if(model.containsAttribute("chargeNameError")){
            model.addAttribute("chargeNameError","Charge Name contains Only Alphabets,Modification Failure");
        }
        if(model.containsAttribute("chargeDefinitionAmountError")){
            model.addAttribute("chargeDefinitionAmountError","Charge Minimum Amount Can't be Greater than Maximum Amount,Modification Failure");
        }


        // Return the name of the JSP page
        return "chargeForm";
    }
    @PostMapping("charge-definition")
    public String saveChargeDefinition(@ModelAttribute("chargeDefinitionDTO")@Valid ChargeDefinitionTempDTO chargeDefinitionTempDTO,
                                       BindingResult bindingResult,
                                       @RequestParam("action") String action, Model model){
        if(bindingResult.hasErrors()){

            return "chargeDefinition";
        }

        if (action != null) {
            switch (action) {
                case "save":
                    tempMetaData = new TempMetaData();
                    tempMetaData.setSaveFlag(true);
                    chargeDefinitionTempDTO.setMetaData(tempMetaData);
                    try {
                        boolean savedResultFlagForSave = chargeDefinitionTempService.saveChargeDefinition(chargeDefinitionTempDTO);
                        if(!savedResultFlagForSave){
                            model.addAttribute("amountComparisonError","Minimum Can't be greater than maximum Amount");
                            return "chargeDefinition";
                        }
                    } catch (ChargeDefinitionCodeAlreadyExist e) {
                        model.addAttribute("error",e.getMessage());
                        return "chargeDefinition";
                    }

                    break;
                case "saveAndRequest":
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    String username = authentication.getName();
                    tempMetaData = new TempMetaData();
                    tempMetaData.setRecordStatus(RecordStatus.N);
                    tempMetaData.setSaveFlag(false);
                    tempMetaData.setCreatedBy(username);
                    chargeDefinitionTempDTO.setMetaData(tempMetaData);
                    if(!chargeDefinitionService.isChargeDefinitionCodeExists(chargeDefinitionTempDTO.getChargeDefinitionCode())) {
                        try {
                            boolean savedResultFlag = chargeDefinitionTempService.saveChargeDefinition(chargeDefinitionTempDTO);
                            if(!savedResultFlag){
                                model.addAttribute("amountComparisonError","Minimum Can't be greater than maximum Amount");
                                return "chargeDefinition";
                            }
                        } catch (ChargeDefinitionCodeAlreadyExist e) {

                            model.addAttribute("error", e.getMessage());
                            return "chargeDefinition";
                        }
                    }else{
                        model.addAttribute("error","Charge Definition Already Exist in the Record");
                        return "chargeDefinition";
                    }
                    break;
                case "cancel":
                    // Redirect to cancel page or perform cancel action
                default:
                    // Default action if no button matched
                    break;
            }
        }
        return "redirect:/maker/charge-definition";
    }
    @GetMapping("addNewChargeDefinition")
    public String addChargeDefinition(Model model){
        ChargeDefinitionTempDTO chargeDefinitionTempDTO = chargeDefinitionTempService.fetchSave();
        if(chargeDefinitionTempDTO !=null){
            model.addAttribute("chargeDefinitionDTO", chargeDefinitionTempDTO);
        }else {
            model.addAttribute("chargeDefinitionDTO", new ChargeDefinitionTempDTO());
        }
        return "chargeDefinition";
    }


    @GetMapping("editCharge")
    public String editChargeDefinition(@RequestParam(value = "code") String chargeDefinitionCode,Model model){
        ChargeDefinitionTempDTO chargeDefinitionTempDTO = chargeDefinitionTempService.getChargeDefinitionByCode(chargeDefinitionCode);

        if(chargeDefinitionTempDTO !=null){
            model.addAttribute("chargeDefinitionDTO", chargeDefinitionTempDTO);
        }


        return "editChargeDefinition";
    }
    @PostMapping("editCharge")
    public String saveUpdatedChargeDefinition(@ModelAttribute("chargeDefinitionDTO") ChargeDefinitionTempDTO chargeDefinitionTempDTO, @RequestParam(value = "action")String action,RedirectAttributes redirectAttributes){
        boolean chargeNameValidation = ChargeDefinitionValidation.chargeDefinitionTempChargeName(chargeDefinitionTempDTO);
        if(!chargeNameValidation){
            redirectAttributes.addFlashAttribute("chargeNameError", "Charge Name Only Contain Alphabet. Modification Failure");
            return "redirect:/maker/charge-definition";
        }
        ChargeDefinitionTempDTO chargeDefinitionTempDTO1 = chargeDefinitionTempService.getChargeDefinitionByCode(chargeDefinitionTempDTO.getChargeDefinitionCode());
        Date createdDate = chargeDefinitionTempDTO1.getMetaData().getCreationDate();
        String createdBy = chargeDefinitionTempDTO1.getMetaData().getCreatedBy();
        boolean isChargeDefinitionAlreadyExistInMasterTable = chargeDefinitionService.isChargeDefinitionCodeExists(chargeDefinitionTempDTO.getChargeDefinitionCode());
        if(action!=null){
            switch (action){
                case "UpdateAndRequest":
                    if(!isChargeDefinitionAlreadyExistInMasterTable) {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String username = authentication.getName();
                        tempMetaData = new TempMetaData();
                        tempMetaData.setRecordStatus(RecordStatus.N);
                        tempMetaData.setSaveFlag(false);
                        tempMetaData.setCreationDate(createdDate);
                        tempMetaData.setModifiedBy(username);
                        tempMetaData.setCreatedBy(createdBy);
                        chargeDefinitionTempDTO.setMetaData(tempMetaData);
                        boolean chargeUpdateResult = chargeDefinitionTempService.updateChargeDefinition(chargeDefinitionTempDTO);
                        if(!chargeUpdateResult){
                            redirectAttributes.addFlashAttribute("chargeDefinitionAmountError","Charge Minimum Amount can't be greater than Maximum Amount");
                        }
                    }else{
                        redirectAttributes.addFlashAttribute("messageWhileUpdate","Already Exist in the Master Table, If you want to do any modification,do it from the authorized records");

                    }
                    break;
                case "cancel":

                default:
                    // Default action if no button matched
                    break;
            }
        }return "redirect:/maker/charge-definition";
    }
    @GetMapping("deleteChargeFromRecords")
    public String deleteChargeRequest(@RequestParam(value = "code")String chargeDefinitionCode, RedirectAttributes redirectAttributes){
        ChargeDefinitionDTO chargeDefinitionDTO = chargeDefinitionService.getChargeDefinitionFromTheMasterTableByCode(chargeDefinitionCode);

        ChargeDefinitionTempDTO chargeDefinitionTempDTO = tempEntityToEntityMapper.chargeDefinitionDTOToChargeDefinitionTempDTOConvertor(chargeDefinitionDTO);
        if(chargeDefinitionTempService.saveDeleteRequestDetails(chargeDefinitionTempDTO)){
            redirectAttributes.addFlashAttribute("messageDeleteRequest"," Request For Deletion Successfully Run");
        }

        return "redirect:/maker/charge-definition";

    }

    @GetMapping("modifyCharge")
    public String modifyExistingChargeDefinitionRequest(@RequestParam(value="code")String chargeDefinitionCode,Model model){
        ChargeDefinitionDTO chargeDefinitionDTO = chargeDefinitionService.getChargeDefinitionFromTheMasterTableByCode(chargeDefinitionCode);
        if(chargeDefinitionDTO!=null){
            model.addAttribute("chargeDefinitionDto",chargeDefinitionDTO);
        }else {
            model.addAttribute("chargeDefinitionDto",new ChargeDefinitionDTO());
        }

        return "modify-Charge";
    }
    @PostMapping("modifyCharge")
    public String modifyExistingChargeDefinitionForm(@ModelAttribute("chargeDefinitionDTO")ChargeDefinitionDTO chargeDefinitionDTO,@RequestParam(value="action")String action,Model model,RedirectAttributes redirectAttributes){
        ChargeDefinitionDTO chargeDefinitionDTO1 = chargeDefinitionService.getChargeDefinitionFromTheMasterTableByCode(chargeDefinitionDTO.getChargeDefinitionCode());
        Date createdDate = chargeDefinitionDTO1.getMetaData().getCreationDate();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean chargeDefinitionName = ChargeDefinitionValidation.chargeDefinitionChargeName(chargeDefinitionDTO);
        if(!chargeDefinitionName) {
            redirectAttributes.addFlashAttribute("chargeNameError", "Charge Name Only Contain Alphabet. Modification Failure");
            return "redirect:/maker/charge-definition";
        }


        ChargeDefinitionTempDTO chargeDefinitionTempDTO =tempEntityToEntityMapper.chargeDefinitionDTOToChargeDefinitionTempDTOConvertor(chargeDefinitionDTO);
        if(action!=null){
            switch (action){
                case "UpdateAndRequest":
                    if(chargeDefinitionTempService.getChargeDefinitionByCode(chargeDefinitionTempDTO.getChargeDefinitionCode())==null) {
                        TempMetaData metaData = new TempMetaData();
                        String createdBy = chargeDefinitionDTO1.getMetaData().getCreatedBy();
                        metaData.setRecordStatus(RecordStatus.M);
                        metaData.setCreationDate(createdDate);
                        metaData.setCreatedBy(createdBy);
                        metaData.setModifiedBy(username);
                        metaData.setSaveFlag(false);
                        chargeDefinitionTempDTO.setMetaData(metaData);
                        boolean updatedResult = chargeDefinitionTempService.saveModifyRequestDetails(chargeDefinitionTempDTO);
                        if(!updatedResult){
                            redirectAttributes.addFlashAttribute("chargeDefinitionAmountError","Charge Minimum Amount can't be greater than Maximum Amount");
                        }

                    }else {
                        chargeDefinitionTempService.deleteChargeDefinition(chargeDefinitionDTO.getChargeDefinitionCode());
                        TempMetaData metaData = new TempMetaData();
                        metaData.setRecordStatus(RecordStatus.M);
                        metaData.setCreationDate(createdDate);
                        metaData.setSaveFlag(false);
                        chargeDefinitionTempDTO.setMetaData(metaData);
                        chargeDefinitionTempService.saveModifyRequestDetails(chargeDefinitionTempDTO);

                    }
                    break;
                case "cancel":
                    break;
                default:
                    break;


            }
        }
        return "redirect:/maker/charge-definition";
    }




    @GetMapping("deleteCharge")
    public String deleteChargeDefinition(@RequestParam(value = "code") String chargeDefinitionCode,Model model){
        chargeDefinitionTempService.deleteChargeDefinition(chargeDefinitionCode);
        return "redirect:/maker/charge-definition";
    }


    @GetMapping("authorizedChargeDefinitionRecord")
    public String authorizedChargeRecord(Model model){
        List<ChargeDefinitionDTO> chargeDefinitionDTOS = chargeDefinitionService.fetchDetailsFromTheMasterTable();
        if(chargeDefinitionDTOS!=null){
            model.addAttribute("chargeDefinitionDTOList",chargeDefinitionDTOS);
        }else {
            model.addAttribute("chargeDefinitionDTOList",new ArrayList<>());
        }
        return "authorized-record-table";
    }




}
