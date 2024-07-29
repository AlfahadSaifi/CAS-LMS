package org.nucleus.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.service.EligibilityPolicyCheckerService;
import org.nucleus.service.EligibilityPolicyMakerService;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@PreAuthorize("hasRole('CHECKER')")
@RequestMapping("checker")
public class EligibilityPolicyCheckerController {

    private static final Logger log = LogManager.getLogger(EligibilityPolicyCheckerController.class);
    @Autowired
    EligibilityPolicyCheckerService checkerService;
    @Autowired
    EligibilityPolicyMakerService makerService;
    @Autowired
    private EligibilityPolicyTempDTOMapper eligibilityPolicyTempDTOMapper;
    @RequestMapping("show-all-eligibility-policies")
    public String hello(Model model){
        List<EligibilityPolicyTemp> parameters = makerService.viewAllEligibilityPolicy();
        model.addAttribute("parameters", parameters);
        return "checker-eligibility-policy";
    }

    @PostMapping("/approve-aditya")
    public String approvePolicy(@RequestParam("eligibilityPolicyId") Long eligibilityPolicyId) {
        log.info("inside checker controller approve");
        EligibilityPolicyTemp policiesTemp=eligibilityPolicyTempDTOMapper.mapPolicy(makerService.getEligibilityPolicy(eligibilityPolicyId));
        log.info(policiesTemp);
        checkerService.approveEligibilityPolicy(policiesTemp);
        log.info("Policy Approved");
        return "redirect:show-all-eligibility-policies";
    }

    @RequestMapping("/reject-aditya")
    public String rejectPolicy(@RequestParam("eligibilityPolicyId") String eligibilityPolicyIdString){
        Long eligibilityPolicyId = Long.parseLong(eligibilityPolicyIdString);
        log.info("in checker controller method rejectPolicy");
        EligibilityPolicyTemp policiesTemp=eligibilityPolicyTempDTOMapper.mapPolicy(makerService.getEligibilityPolicy(eligibilityPolicyId));
        log.info("after getpolicy called");
        log.info(policiesTemp);
        checkerService.rejectEligibilityPolicy(policiesTemp);
        log.info("Policy Rejected");
        return "redirect:show-all-eligibility-policies";
    }
    @GetMapping("/details")
    public String viewPolicy(Model model){
        return "redirect:show-all-eligibility-policies";
    }
}
