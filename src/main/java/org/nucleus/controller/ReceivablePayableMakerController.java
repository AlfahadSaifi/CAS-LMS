package org.nucleus.controller;

import org.nucleus.dao.ReceivablePayableTempDao;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.LoanAccountService;
import org.nucleus.service.ReceivablePayableService;
import org.nucleus.service.ReceivablePayableTempService;
import org.nucleus.utility.enums.ReceivablePayableStatus;
import org.nucleus.utility.enums.ReceivablePayableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("maker")
public class ReceivablePayableMakerController {

    @Autowired
    private ReceivablePayableTempDao receivablePayableTempDao;
    @Autowired
    private ReceivablePayableService receivablePayableService;
    @Autowired
    private ReceivablePayableTempService tempService;
    @Autowired
    private LoanAccountService loanAccountService;
    @GetMapping("/receivable-payable")
    String addAttributes(Model model){
        //add the model attribute as ReceivablePayable
        model.addAttribute("receivablePayable",new ReceivablePayableDTO());
        //send the control to receivable-payable maker form.
        return "receivable-payable-maker";
    }
    @RequestMapping("insert-receivable-payable")
    String insertReceivablePayable(
            @ModelAttribute("receivablePayable") ReceivablePayableDTO receivablePayableDto,
            @RequestParam("loan-account-number")String loanAccountNumber,
            Model model
    ){
        //fetch loanId from loanAccountNumber and getLoanAccount object
        LoanAccountDTO loanAccount = loanAccountService.getByAccountNumber(loanAccountNumber);
        if(loanAccount == null){
            model.addAttribute("successMessage", "No entries found for this loan account number ");
            return "receivable-payable-maker";
        }
        //set the loanAccountObject into the Model ReceivablePayable
        receivablePayableDto.setLoanAccount(loanAccount);

        //Initialise meta data
        TempMetaData tempMetaData = new TempMetaData();
        receivablePayableDto.setTempMetaData(tempMetaData);
        System.out.println(receivablePayableDto.getReceivablePayableDueDate());
        //call service and insert the receivable payable object.
        if(tempService.insertReceivablePayable(receivablePayableDto)) {
            model.addAttribute("successMessage", "Receivable/Payable inserted successfully in temporary table");
            return "receivable-payable-maker";
        }
        model.addAttribute("successMessage", "Receivable/Payable not inserted successfully.");
        return "receivable-payable-maker";
    }

}
