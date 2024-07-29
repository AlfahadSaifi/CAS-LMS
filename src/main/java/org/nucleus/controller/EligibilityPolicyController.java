package org.nucleus.controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.nucleus.entity.temporary.EligibilityPolicyParameterTemp;
import org.nucleus.service.CheckerEligibilityPolicyParameterDefinitionService;
import org.nucleus.service.EligibilityPolicyCheckerService;
import org.nucleus.service.EligibilityPolicyMakerService;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDTOMapper;
import org.nucleus.utility.enums.Operator;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.validations.EligibilityPolicyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.nucleus.utility.enums.RecordStatus.*;

@Controller
@PreAuthorize("hasRole('MAKER')")
@SessionAttributes("eligibilityPolicy")
@RequestMapping("maker")
public class EligibilityPolicyController {
    private static final Logger log = LogManager.getLogger(EligibilityPolicyController.class);
    @Autowired
    private EligibilityPolicyMakerService makerService;
    @Autowired
    private EligibilityPolicyCheckerService checkerService;
    @Autowired
    private EligibilityPolicyValidator eligibilityPolicyValidator;
    @Autowired
    private EligibilityPolicyTempDTOMapper eligibilityPolicyTempDTOMapper;
    @Autowired
    private CheckerEligibilityPolicyParameterDefinitionService service;
    @InitBinder
    public void bind(WebDataBinder webDataBinder)
    {
        webDataBinder.setValidator(eligibilityPolicyValidator);
    }
    @ModelAttribute("listParameters")
    public List<EligibilityPolicyParameterDefinitionDTO> eligibilityParameters(){
        return service.getAllEligibilityParameter();
    }

    @ModelAttribute("listOperators")
    public List<Operator> operators(){
        return Arrays.asList(Operator.values());
    }
    @RequestMapping("create-eligibility-policy")
    public String transferToPolicy( Model model){
        EligibilityPolicyTempDTO eligibilityPolicyTempDTO=null;
        if (model.getAttribute("eligibilityPolicy")!=null){
            eligibilityPolicyTempDTO=(EligibilityPolicyTempDTO) model.getAttribute("eligibilityPolicy");
            model.addAttribute("parameters",eligibilityPolicyTempDTO.getEligibilityParameterMappingList());
        }
        else {
            eligibilityPolicyTempDTO=new EligibilityPolicyTempDTO();
            eligibilityPolicyTempDTO.setEligibilityParameterMappingList(new ArrayList<>());
            eligibilityPolicyTempDTO.setMetaData(new TempMetaData());
        }
        model.addAttribute("eligibilityPolicyTempDTO",eligibilityPolicyTempDTO);
        return "maker-eligibility-policy";
    }
    @PostMapping("add-eligibility")
    public String addPolicy(@ModelAttribute @Validated EligibilityPolicyTempDTO eligibilityPolicyTempDTO,BindingResult bindingResult, @RequestParam(required = false) List<String> parameter, @RequestParam(required = false) List<Operator> operator
            , @RequestParam(required = false) List<Integer> value, @RequestParam(required = false) String submit,Model model){
        List<EligibilityPolicyParameterTemp> eligibilityParameterMappingList = new ArrayList<>();
        EligibilityPolicyParameterTemp policyEligibilityParameter;
        for (int i = 0; i < parameter.size(); i++) {
            policyEligibilityParameter = new EligibilityPolicyParameterTemp();
            policyEligibilityParameter.setParameter(parameter.get(i));
            policyEligibilityParameter.setOperator(operator.get(i));
            policyEligibilityParameter.setValue(value.get(i));
            eligibilityParameterMappingList.add(policyEligibilityParameter);
        }
        eligibilityPolicyTempDTO.setEligibilityParameterMappingList(eligibilityParameterMappingList);
        log.info("kk"+eligibilityPolicyTempDTO.getFlag());
        log.info("jj"+ eligibilityPolicyTempDTO.getFlag());

        if (submit.equalsIgnoreCase("Save And Request Approval")) {
            if (eligibilityPolicyTempDTO.getFlag()!=null){
                RecordStatus flag=eligibilityPolicyTempDTO.getFlag();
                log.info("mm"+eligibilityPolicyTempDTO.getFlag());
//                if (flag.equals(NR))
//                    eligibilityPolicyTempDTO.setFlag(RecordStatus.N);
//                else if(flag.equals(MR)){
//                    eligibilityPolicyTempDTO.setFlag(RecordStatus.M);
//                }
                if(makerService.updateEligibilityPolicy(eligibilityPolicyTempDTO)){
                    model.addAttribute("message","Updation is successful");
                }
                else {
                    model.addAttribute("message","Some Internal Error Occured while updating the record. Please contact the system administrator.");
                    return "error";
                }
            }
            else {
                if (bindingResult.hasErrors()){
                    return "maker-eligibility-policy";
                }
//                eligibilityPolicyTempDTO.setFlag(N);
                if(makerService.insertEligibilityPolicy(eligibilityPolicyTempDTO)) {
                    model.addAttribute("message", "Insertion is successful");
                }
                else {
                    model.addAttribute("message","Some Internal Error Occured while inserting the record. Please contact the system administrator.");
                    return "error";
                }
            }
        }
        else {
            model.addAttribute("eligibilityPolicy",eligibilityPolicyTempDTO);
        }
        return "forward:show-all-eligibility-policies-dummy";
    }

//    @PostMapping("add-eligibility")
//    public String addPolicy(@ModelAttribute EligibilityPolicyTempDTO eligibilityPolicyTempDTO, @RequestParam String submit,Model model){
////        eligibilityPolicyTempDTO.setEligibilityParameterMappingList(eligibilityParameterMappingList);
//        log.info("kk"+eligibilityPolicyTempDTO.getMetaData().getRecordStatus());
//        log.info("jj"+ eligibilityPolicyTempDTO.getMetaData().getRecordStatus());
//        if (submit.equalsIgnoreCase("Save & Request Approval")) {
//            if (eligibilityPolicyTempDTO.getFlag()!=null){
//                log.info("mm"+eligibilityPolicyTempDTO.getFlag());
//                eligibilityPolicyTempDTO.setFrom("maker");
//                log.info(makerService.updateEligibilityPolicy(eligibilityPolicyTempDTO));
//                model.addAttribute("message","Updation is successful");
//            }
//            else {
//                eligibilityPolicyTempDTO.getMetaData().setRecordStatus(N);
//                eligibilityPolicyTempDTO.setFrom("maker");
//                log.info(makerService.insertEligibilityPolicy(eligibilityPolicyTempDTO));
//                model.addAttribute("message", "Insertion is successful");
//            }
//        }
//        else {
//            model.addAttribute("eligibilityPolicy",eligibilityPolicyTempDTO);
//        }
//        return "redirect:show-all-eligibility-policies";
//    }
    @RequestMapping("show-all-eligibility-policies")
    public String showAllPolicies(Model model){
        log.info(makerService.viewAllEligibilityPolicy());
        model.addAttribute("allPolicies",makerService.viewAllEligibilityPolicy());
        model.addAttribute("allPermPolicies",checkerService.viewAllEligibilityPolicyFiltered());
        return "display-eligibility-policy";
    }
    @RequestMapping("show-all-eligibility-policies-dummy")
    public String showAllPoliciesDummy(Model model, HttpServletResponse httpServletResponse){
        log.info(makerService.viewAllEligibilityPolicy());
        model.addAttribute("allPolicies",makerService.viewAllEligibilityPolicy());
        model.addAttribute("allPermPolicies",checkerService.viewAllEligibilityPolicyFiltered());
        httpServletResponse.addHeader("refresh","5,URL=show-all-eligibility-policies");
        return "display-eligibility-policy";
    }
    @RequestMapping("update-eligibility")
    public String updatePolicy(@RequestParam Long policyId,@RequestParam String flag,Model model){
        EligibilityPolicyTempDTO eligibilityPoliciesDTO= makerService.getEligibilityPolicy(policyId);
        if (eligibilityPoliciesDTO==null){
            eligibilityPoliciesDTO=checkerService.getEligibilityPolicy(policyId);
        }
        eligibilityPoliciesDTO.setFlag(RecordStatus.valueOf(flag));
        log.info("to update"+eligibilityPoliciesDTO.getFlag());
        log.info("to update again"+eligibilityPoliciesDTO.getMetaData());
        model.addAttribute("eligibilityPolicyTempDTO",eligibilityPoliciesDTO);
        model.addAttribute("parameters",eligibilityPoliciesDTO.getEligibilityParameterMappingList());
        return "maker-eligibility-policy";
    }
    @RequestMapping("delete-eligibility")
    public String deletePolicy(@RequestParam Long policyId,@RequestParam String flag,Model model){
        log.info("delete me");
        if(makerService.deleteEligibilityPolicy(policyId,flag)) {
            log.info(policyId + " and " + flag);
            model.addAttribute("message", "Deletion at maker side is successful");
        }
        else {
            model.addAttribute("message","Some Internal Error Occured while deleting the record. Please contact the system administrator.");
            return "error";
        }
        return "forward:show-all-eligibility-policies-dummy";
    }
//    @RequestMapping("")
}
