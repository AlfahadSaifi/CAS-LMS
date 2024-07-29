package org.nucleus.controller;


import org.nucleus.dto.CheckerEarlyClosureDTO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.service.EarlyClosureService;
import org.nucleus.service.LoanAccountService;
import org.nucleus.utility.enums.LoanStatus;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@PreAuthorize("hasRole('CHECKER')")
@RequestMapping("/checker")
public class CheckerEarlyClosureController
{
    private final EarlyClosureService earlyClosureService;
    private final LoanAccountService loanAccountService;
    @Autowired
    public CheckerEarlyClosureController(EarlyClosureService earlyClosureService, LoanAccountService loanAccountService)
    {
        this.earlyClosureService = earlyClosureService;
        this.loanAccountService = loanAccountService;
    }
    @RequestMapping("/early-closure")
    public ModelAndView displayCheckerTable()
    {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("errorMessage"," ");
        modelAndView.setViewName("early-closure-checker-dash");
        return modelAndView;
    }
    @RequestMapping("/fetchLoanClosureData")
    public ModelAndView fetchLoanClosureData(@RequestParam("loanAccountNumber") String loanAccountNumber)
    {
        ModelAndView modelAndView=new ModelAndView();
        final String ERROR_MESSAGE="errorMessage";

        CheckerEarlyClosureDTO checkerEarlyClosureDTO=earlyClosureService.getAllCheckerFields(loanAccountNumber);
        if(checkerEarlyClosureDTO==null)
        {
            modelAndView.addObject(ERROR_MESSAGE,"No request found for this Account_Number!");
        }
        else if (checkerEarlyClosureDTO.getMetaData().getRecordStatus()== RecordStatus.NR)
        {
            modelAndView.addObject(ERROR_MESSAGE,"Request for loan Closure is already rejected");
        }
        else
        {
            modelAndView.addObject("checkerEarlyClosureDTO",checkerEarlyClosureDTO);
        }
        modelAndView.setViewName("early-closure-checker-dash");
        return modelAndView;
    }

    @RequestMapping("/delete-request")
    public ModelAndView deleteRequest(@RequestParam String loanClosureId)
    {
        LoanClosureDTO loanClosureDTO=earlyClosureService.getLoanClosureTempById(Long.parseLong(loanClosureId));
        earlyClosureService.updateLoanClosureTemp(loanClosureDTO);
        return new ModelAndView("early-closure-checker-dash");
    }

    @RequestMapping("/approve-request")
    public ModelAndView approveRequest(@RequestParam String loanClosureId)
    {
        System.out.println("approve");

        LoanClosureDTO loanClosureDTO=earlyClosureService.getLoanClosureTempById(Long.parseLong(loanClosureId));

        System.out.println("creating");
        // creating in loan closure permanent table
        earlyClosureService.createLoanClosure(loanClosureDTO);

        // Setting status in loanAccount to closed
        Long loanAccountId=earlyClosureService.findLoanClosureByLoanAccountId(Long.parseLong(loanClosureId));
        System.out.println("ID  "+loanAccountId);
        LoanAccountDTO loanAccountDTO=loanAccountService.getByLoanAccountId(loanAccountId);
        System.out.println("checker  "+loanAccountDTO);
        loanAccountDTO.setLoanStatus(LoanStatus.CLOSED);
        earlyClosureService.updateLoanAccount(loanAccountDTO);

        System.out.println("deleting from temp");
        // deleting from loan closure temporary table
//        LoanClosureDTO loanClosureDTO1=earlyClosureService.getLoanClosureTempById(Long.parseLong(loanClosureId));
//        earlyClosureService.deleteLoanClosureByDTO(loanClosureDTO1);
        earlyClosureService.deleteLoanClosure(Long.parseLong(loanClosureId));
        System.out.println("Deleted");
        return new ModelAndView("early-closure-checker-dash");
    }
}
