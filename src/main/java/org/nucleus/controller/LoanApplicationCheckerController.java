package org.nucleus.controller;


import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/checker")
@PreAuthorize("hasRole('CHECKER')")
public class LoanApplicationCheckerController {

    private final LoanApplicationService loanApplicationService;

    @Autowired
    public LoanApplicationCheckerController(LoanApplicationService loanApplicationService){
        this.loanApplicationService = loanApplicationService;
    }

    @RequestMapping("/view-loan-applications")
    public ModelAndView getAll(){

        List<LoanApplicationDTO> loanApplicationDTOS = loanApplicationService.getAllNotRejected();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loanDetailsTablePer");
        modelAndView.addObject("loanApplicationDTOS", loanApplicationDTOS);

        return modelAndView;
    }

    // approved loan application
    @RequestMapping("/approve-loan-application/{loanApplicationId}")
    public String approveLoanApplication(@PathVariable("loanApplicationId") String loanApplicationId) {
        loanApplicationService.approveLoanApplication(loanApplicationId);

        return "redirect:../view-loan-applications";

    }

    @RequestMapping("/reject-loan-application/{loanApplicationId}")
    public String rejectLoanApplication(@PathVariable("loanApplicationId") String loanApplicationId) {
        loanApplicationService.rejectLoanApplication(loanApplicationId);

        return "redirect:../view-loan-applications";
    }

    @GetMapping("/loanDetails/{loanApplicationId}")
    public ModelAndView getLoanDetails(@PathVariable("loanApplicationId") String loanApplicationId) {
        LoanApplicationDTO loanApplicationDTO = loanApplicationService.readTemporaryByLoanApplicationId(loanApplicationId);

        return new ModelAndView("loanDetails", "loanApplication", loanApplicationDTO);
    }
}
