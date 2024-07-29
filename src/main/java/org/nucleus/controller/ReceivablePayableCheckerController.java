package org.nucleus.controller;

import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.permanent.LoanAccount;
import org.nucleus.service.LoanAccountService;
import org.nucleus.service.ReceivablePayableService;
import org.nucleus.service.ReceivablePayableTempService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("checker")
public class ReceivablePayableCheckerController {

    @Autowired
    LoanAccountService loanAccountService;
    @Autowired
    ReceivablePayableTempService receivablePayableTempService;
    @Autowired
    ReceivablePayableService receivablePayableService;
    @RequestMapping("receivable-payable")
    public String showApproval(){
        return "receivable-payable-checker";
    }
    @RequestMapping("/generate-table")
    public ModelAndView generateTable(@RequestParam("loanAccountNumber") String loanAccountNumber,
                                      @RequestParam("fromDate") String startDate,
                                      @RequestParam("toDate") String endDate){
        LoanAccountDTO loanAccountDTO = loanAccountService.getByAccountNumber(loanAccountNumber);
        List<ReceivablePayableDTO> receivableDtoList = receivablePayableTempService.getReceivablePayableAgainstLoanIdAndDateRange(loanAccountDTO,Date.valueOf(startDate),Date.valueOf(endDate));


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("receivablePayableDTOList",receivableDtoList);
        modelAndView.setViewName("receivable-payable-temp-table");
        return modelAndView;
    }


}
