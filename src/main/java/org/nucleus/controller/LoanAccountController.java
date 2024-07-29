package org.nucleus.controller;

import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanAccountRequiredFieldDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.service.LoanAccountService;
import org.nucleus.service.LoanApplicationService;
import org.nucleus.service.RepayScheduleService;
import org.nucleus.utility.dtomapper.LoanApplicationMapper;
import org.nucleus.utility.enums.LoanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("checker")
public class LoanAccountController {
    @Autowired
    private LoanApplicationMapper loanApplicationMapper;
    private final LoanAccountService loanAccountService;
    private final LoanApplicationService loanApplicationService;
    private final RepayScheduleService repayScheduleService;
    private String loanAccountNumberSearch = "";
    @Autowired
    public LoanAccountController(LoanAccountService loanAccountService, LoanApplicationService loanApplicationService, RepayScheduleService repayScheduleService) {
        this.loanAccountService = loanAccountService;
        this.loanApplicationService=loanApplicationService;
        this.repayScheduleService=repayScheduleService;

    }
    @GetMapping("/loan-account")
    public String disburse(){
        return "loan-account";
        }

    @RequestMapping("/disburse-form")
    public ModelAndView disburse(@RequestParam("action") String action,
                                 @RequestParam String loanAccountNumberSearch,
                                 @ModelAttribute LoanAccountDTO loanAccountDTO){
        ModelAndView mv=new ModelAndView();
        if("search".equalsIgnoreCase(action)){
            this.loanAccountNumberSearch = loanAccountNumberSearch;
            LoanApplication loanApplication = loanApplicationService.getApplicationByAccountNumber(loanAccountNumberSearch);

            LoanAccountRequiredFieldDTO requiredField = loanAccountService.getAllFields(loanAccountNumberSearch);

            if (loanApplication == null) {
                // If loan application is not found, add an error message to the ModelAndView object
                mv.addObject("errorMessage", "This account number is not present!");
            } else if (loanAccountService.isLoanAccountPresent(loanAccountNumberSearch)) {
                mv.addObject("errorMessage", "Loan has been disbursed for this account number!");
            }
            else if (!loanApplication.getLoanStatus().toString().equalsIgnoreCase("APPROVED")) {
                // If loan application status is not approved, add an error message to the ModelAndView object
                mv.addObject("errorMessage", "This loan application is not approved.");
            }
            else {
                mv.addObject("loanApplication", loanApplication);
                mv.addObject("requiredField", requiredField);
            }
            mv.setViewName("loan-account");
        }
        else if("submit".equalsIgnoreCase(action)) {
            LoanApplication loanApplication = loanApplicationService.getApplicationByAccountNumber(this.loanAccountNumberSearch);


            loanApplication.setLoanStatus(LoanStatus.APPROVED);
            loanAccountDTO.setLoanAmountPaidByCustomer(0.0);
            loanAccountDTO.setLoanApplication(loanApplication);
            System.out.println("loan Account: "+loanAccountDTO);
            loanAccountService.insertLoanAccount(loanAccountDTO);
            mv.addObject("loanAccount", loanAccountDTO);
            mv.setViewName("loan-disburse-success");
        }
        return mv;
    }
}
