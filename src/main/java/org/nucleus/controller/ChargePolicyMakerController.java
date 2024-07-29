package org.nucleus.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.ChargePolicyDto;
import org.nucleus.dto.ChargePolicyParameterTempDto;
import org.nucleus.dto.ChargePolicyTempDto;
import org.nucleus.exception.PolicyCodeAlreadyExistException;
import org.nucleus.service.ChargeDefinitionService;
import org.nucleus.service.ChargePolicyService;
import org.nucleus.service.ChargePolicyTempService;
import org.nucleus.service.ChargePolicyTempServiceImpl;
import org.nucleus.utility.enums.Operator;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.temptoperm.ChargePolicyConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("maker")
public class ChargePolicyMakerController {
    private ChargePolicyTempService chargePolicyTempService;
    private ChargePolicyService chargePolicyService;
    private ChargePolicyConvertor chargePolicyConvertor;
    private ChargeDefinitionService chargeDefinitionService ;

    private final static Logger logger = LogManager.getLogger(ChargePolicyMakerController.class);

    public ChargePolicyMakerController(){}
    @Autowired
    public ChargePolicyMakerController(ChargeDefinitionService chargeDefinitionService,ChargePolicyConvertor chargePolicyConvertor,ChargePolicyService chargePolicyService,ChargePolicyTempService chargePolicyTempService)
    {
        this.chargePolicyService=chargePolicyService;
        this.chargeDefinitionService=chargeDefinitionService;
        this.chargePolicyConvertor=chargePolicyConvertor;
        this.chargePolicyTempService = chargePolicyTempService;
    }



    @RequestMapping("charge-policy-table")
    public ModelAndView displayChargePolicies()
    {
        ModelAndView model = new ModelAndView();
// get all charge policy from the temporary table
        List<ChargePolicyTempDto> chargePoliciesTempDtoList = chargePolicyTempService.getAllChargePolicy();

// get all charge policy form permanent table
        List<ChargePolicyDto> chargePolicyDtoList = chargePolicyTempService.filterAndRemoveDuplicates(chargePoliciesTempDtoList);

// add charge policies of permanent table

        if(chargePolicyDtoList!=null)
            model.addObject("chargePoliciesPerm",chargePolicyDtoList);
        else
            model.addObject("chargePoliciesPerm",new ArrayList<>());

// add charge policies of temporary table
        model.addObject("chargePolicies",chargePoliciesTempDtoList);
        model.setViewName("charge-policy");

        return model;
    }

    @GetMapping("getMakerDetails")
    public String getPolicyDetails(@RequestParam("code")String policyCode ,Model model)
    {
        ChargePolicyTempDto chargePolicy = chargePolicyTempService.getChargePolicy(policyCode);
        List<ChargeDefinitionDTO> chargeNameDtoList = chargeDefinitionService.fetchDetailsFromTheMasterTable();
        model.addAttribute("chargeList",chargeNameDtoList);
        if(chargePolicy==null)
        {
            ChargePolicyDto chargePolicyDto = chargePolicyService.getChargePolicy(policyCode);
            model.addAttribute("selectedPolicy",chargePolicyDto);
        }
        else
        {
            model.addAttribute("selectedPolicy", chargePolicy);
        }
        return "display-maker";
    }

    @GetMapping("charge-policy")
    public String createChargePolicy(Model model)
    {

//         charge list
        List<ChargeDefinitionDTO> chargeNameDtoList = chargeDefinitionService.fetchDetailsFromTheMasterTable();
//        operator list
        List<Operator> operatorList = Arrays.asList(Operator.values());
        model.addAttribute("operatorList",operatorList);
        model.addAttribute("charges",chargeNameDtoList);

//        check whether the policy code exist in the database or not
        ChargePolicyTempDto chargePolicyTempDtoFlag = chargePolicyTempService.getChargePolicyByEditFlag(true);

//        if policy code exist then autofill the details of that policy code in the form(in case of save and update)
        if(chargePolicyTempDtoFlag !=null)
        {
            model.addAttribute("chargePolicy", chargePolicyTempDtoFlag);
            List<ChargePolicyParameterTempDto> chargePolicyParameterTempDtos = chargePolicyTempDtoFlag.getChargePolicyParameterList();
            model.addAttribute("chargePolicyParameter",chargePolicyParameterTempDtos);
            model.addAttribute("edit","true");
        }

//        If policy code does not exist then add a new object in the model and view
        else
        {
            ChargePolicyTempDto chargePolicyTempDto = new ChargePolicyTempDto();
            model.addAttribute("chargePolicy", chargePolicyTempDto);
        }
        return "charge-policy-form";
    }

    @PostMapping("charge-policy")
    public String saveChargePolicy(@RequestParam("chargeCodes") List<String> chargeDefinitionCode,
                                         @RequestParam("operator") List<String> operator,
                                         @RequestParam("value") List<String> value,
                                         @RequestParam("action") String action,
                                         @ModelAttribute("chargePolicy")@Valid ChargePolicyTempDto chargePolicyTempDto, BindingResult errors,Model model) {
        List<ChargeDefinitionDTO> chargeNameDtoList = chargeDefinitionService.fetchDetailsFromTheMasterTable();
        if (errors.hasErrors()) {
            List<Operator> operatorList = Arrays.asList(Operator.values());
            model.addAttribute("operatorList", operatorList);
            model.addAttribute("charges", chargeNameDtoList);
            model.addAttribute("chargePolicy", chargePolicyTempDto);
            return "charge-policy-form";
        }

// create a list of ChargePolicyParameterDto objects
        chargePolicyTempDto.setChargePolicyParameterList( chargePolicyTempService.setChargePolicyParameters(chargeDefinitionCode,operator,value));


// set created date and created by
        chargePolicyTempDto = chargePolicyTempService.setCreatedDateAndCreatedBy(chargePolicyTempDto);

//  set record status
        chargePolicyTempDto = chargePolicyTempService.setRecordStatus(chargePolicyTempDto);


//        In case of save
        if ("save".equalsIgnoreCase(action)) {

            //          set the save flag to true
            chargePolicyTempDto.setSaveFlag(true);

            List<ChargeDefinitionDTO> chargeNameDtoList1 = chargeDefinitionService.fetchDetailsFromTheMasterTable();
            List<Operator> operatorList = Arrays.asList(Operator.values());
            model.addAttribute("operatorList", operatorList);
            model.addAttribute("charges", chargeNameDtoList1);
            model.addAttribute("edit", "true");

            String resultForSaveCase = chargePolicyTempService.saveCase(chargePolicyTempDto);
            if ("true".equalsIgnoreCase(resultForSaveCase)) {

                model.addAttribute("chargePolicy", chargePolicyTempDto);
                List<ChargePolicyParameterTempDto> chargePolicyParameterTempDtos = chargePolicyTempDto.getChargePolicyParameterList();
                model.addAttribute("chargePolicyParameter", chargePolicyParameterTempDtos);
                return "charge-policy-form";
            } else if ("exist".equalsIgnoreCase(resultForSaveCase)) {
                model.addAttribute("chargePolicy", chargePolicyTempDto);
                model.addAttribute("errorMessage", "This Policy Code Already Exist");
                return "charge-policy-form";
            } else {
                model.addAttribute("errorMessage", resultForSaveCase);
                return "error";
            }
        }

        else if("cancel".equalsIgnoreCase(action))
        {
            return "redirect:charge-policy-table";
        }

//        in case of save and request
        else  {

//                set the saveFlag to false
                chargePolicyTempDto.setSaveFlag(false);
                String resultForSaveAndRequestCase = chargePolicyTempService.saveAndRequestCase(chargePolicyTempDto);

                if ("true".equalsIgnoreCase(resultForSaveAndRequestCase)) {
                    model.addAttribute("successMessage", "Charge Policy ( " + chargePolicyTempDto.getPolicyCode() + " ) is successfully added");
                }

                else if ("updateTrue".equalsIgnoreCase(resultForSaveAndRequestCase)) {
                    model.addAttribute("successMessage", "Charge Policy ( " + chargePolicyTempDto.getPolicyCode() + " ) is successfully updated");
                }

                else if ("exist".equalsIgnoreCase(resultForSaveAndRequestCase)) {
                    List<ChargeDefinitionDTO> chargeNameDtoList2 = chargeDefinitionService.fetchDetailsFromTheMasterTable();

                    List<Operator> operatorList = Arrays.asList(Operator.values());
                    model.addAttribute("operatorList", operatorList);
                    model.addAttribute("chargePolicy", chargePolicyTempDto);
                    model.addAttribute("charges", chargeNameDtoList2);
                    model.addAttribute("errorMessage", "This Policy Code Already exist");
                    return "charge-policy-form";
                } else {
                    model.addAttribute("errorMessage", resultForSaveAndRequestCase);
                    return "error";
                }
                return "redirect:charge-policy-table";
            }
        }



    // controller to delete charge policy
    @RequestMapping("delete-charge-policy")
    public ModelAndView deleteChargePolicy(@RequestParam("policyCode") String policyCode,
                                           @RequestParam("record") String recordStatus)
    {
        ModelAndView model = new ModelAndView();

        if(chargePolicyTempService.deleteCase(policyCode,recordStatus))
        {
            model.addObject("successMessage","Delete request successfully submitted for policy code " + policyCode);
            logger.info("Delete request successfully submitted for policy code " + policyCode);
        }
        else
        {
            model.addObject("successMessage","Charge Policy successfully deleted for policy code " + policyCode);
            logger.info("Charge Policy successfully deleted for policy code " + policyCode);
        }

        List<ChargePolicyTempDto> chargePoliciesTempDtoList = chargePolicyTempService.getAllChargePolicy();

//      get all charge policy form permanent table
        List<ChargePolicyDto> chargePolicyDtoList = chargePolicyTempService.filterAndRemoveDuplicates(chargePoliciesTempDtoList);

// add charge policies of permanent table
        model.addObject("chargePoliciesPerm",chargePolicyDtoList);
// add charge policies of temporary table
        model.addObject("chargePolicies",chargePoliciesTempDtoList);
        model.setViewName("redirect:charge-policy-table");
        return model;
    }


    // controller to update charge policy
    @RequestMapping("edit-charge-policy-form")
    public String editChargePolicy(@RequestParam("policy") String policyCode,
                                         @RequestParam("record") String recordStatus,Model model)
    {
        ChargePolicyTempDto chargePolicyTempDto = chargePolicyTempService.editCase(policyCode,recordStatus);
        model.addAttribute("chargePolicy", chargePolicyTempDto);

        List<ChargePolicyParameterTempDto> chargePolicyParameterTempDtos = chargePolicyTempDto.getChargePolicyParameterList();
        model.addAttribute("chargePolicyParameter",chargePolicyParameterTempDtos);
        List<ChargeDefinitionDTO> chargeNameDtoList = chargeDefinitionService.fetchDetailsFromTheMasterTable();
        List<Operator> operatorList = Arrays.asList(Operator.values());
        model.addAttribute("operatorList",operatorList);
        model.addAttribute("charges",chargeNameDtoList);
        model.addAttribute("edit","true");
        return "charge-policy-form";
    }

}