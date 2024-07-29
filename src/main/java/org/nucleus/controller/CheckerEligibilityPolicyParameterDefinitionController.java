package org.nucleus.controller;

import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.service.CheckerEligibilityPolicyParameterDefinitionService;
import org.nucleus.service.MakerEligibilityPolicyParameterDefinitionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@PreAuthorize("hasRole('CHECKER')")
@RequestMapping("/checker")
public class CheckerEligibilityPolicyParameterDefinitionController {

    //    @Autowired
    private final MakerEligibilityPolicyParameterDefinitionService makerEligibilityParameterService;

    //    @Autowired
    private final CheckerEligibilityPolicyParameterDefinitionService checkerEligibilityParameterService;

    @Autowired
    public CheckerEligibilityPolicyParameterDefinitionController(MakerEligibilityPolicyParameterDefinitionService makerEligibilityParameterService, CheckerEligibilityPolicyParameterDefinitionService checkerEligibilityParameterService) {
        this.makerEligibilityParameterService = makerEligibilityParameterService;
        this.checkerEligibilityParameterService = checkerEligibilityParameterService;
    }

    @RequestMapping("/eligibility-parameter") //do must be removed main jsp
    public String customerForm(Model model){
        List<EligibilityPolicyParameterDefinitionDTO> tempParameters = makerEligibilityParameterService.getAllReqEligibilityParameter();
        model.addAttribute("tempParameters", tempParameters);
        return "checker-eligibility-policy-parameter-definition";
    }



    @RequestMapping ("reject/{id}")
    public ModelAndView rejectItemById(@PathVariable("id") Long id
            ,RedirectAttributes redirectAttributes
    ) {
        ModelAndView modelAndView = new ModelAndView();
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = makerEligibilityParameterService.getTempEligibilityParameterById(id);


        makerEligibilityParameterService.checkerUpdateTempEligibilityParameter(checkerEligibilityParameterService.rejectEligibilityPolicyParameterDefinition(eligibilityParameterDTO));
//        modelAndView.addObject("message","parameter rejected");
        redirectAttributes.addFlashAttribute("message", "parameter rejected");


        modelAndView.setViewName("redirect:/checker/eligibility-parameter");
        return modelAndView;

    }


    @RequestMapping ("approve/{id}")
    public ModelAndView approveItemById(@PathVariable("id") Long id
            ,RedirectAttributes redirectAttributes
    ) {
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = makerEligibilityParameterService.getTempEligibilityParameterById(id);


        checkerEligibilityParameterService.approveEligibilityParameter(eligibilityParameterDTO);
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("message","parameter approved");
        redirectAttributes.addFlashAttribute("message", "parameter approved");

        modelAndView.setViewName("redirect:/checker/eligibility-parameter");
        return modelAndView;

    }


}
