package org.nucleus.controller;


import org.nucleus.dto.PaymentDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.PaymentServiceTemp;
import org.nucleus.service.ReceivablePayableService;
import org.nucleus.utility.OldDateEditor;
import org.nucleus.utility.enums.ReceiptStatus;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.DateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/maker")
@PreAuthorize("hasRole('MAKER')")
public class PaymentMakerController {
    @Autowired
    PaymentServiceTemp paymentServiceTemp;
    @Autowired
    ReceivablePayableService receivablePayableService;
    @InitBinder
    public void bind(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class,"paymentDate", new OldDateEditor());
    }
    @GetMapping("payment")
    public String showPayableForm(Model model) {  //show jsp page to enter the records
        model.addAttribute("paymentMaker", new PaymentDTO());
        return "forward:../views/payment-maker-final.jsp";
    }

    @PostMapping("payment-Submit")
    public ModelAndView handelPayableSearchAndSubmit(@ModelAttribute("paymentMaker") @Valid PaymentDTO paymentDTO, BindingResult errors,
                                                     @RequestParam("action")String action,Model model) {  //Handle Payable search and submit on given receivablePayable-id
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("paymentMaker", paymentDTO);
        modelAndView.setViewName("payment-maker-final");
        if(errors.hasErrors())
        {
            modelAndView.setViewName("payment-maker-final");
            return modelAndView;
        }
        if("search".equalsIgnoreCase(action)) {  //for search operation
            ReceivablePayableDTO receivablePayableDTO = receivablePayableService.getReceivablePayable(paymentDTO.getReceivablePayable().getReceivablePayableId());
            //dummy data
            if (receivablePayableDTO!=null) {
                paymentDTO.setLoanAccountNumber(receivablePayableDTO.getLoanAccount().getLoanAccountNumber());
                model.addAttribute("successMessage", "Receivable Payable Id data fetched and submitted successfully!,,click cancel to go back");
            } else {
                model.addAttribute("errorMessage", "Failed to fetch data for the provided Receivable Payable Id. Please ensure the ID is correct and try again.");
            }
            return modelAndView;
        }
        boolean flag = false;
        if(paymentDTO.getLoanAccountNumber()!=null) {
            if (paymentDTO.getPaymentAmount() > 0) {  //check Payable Amount is greater than 0.
                TempMetaData tempMetaData = new TempMetaData(); //setMeta data
                tempMetaData.setCreationDate(Date.valueOf(LocalDate.now()));
                tempMetaData.setRecordStatus(RecordStatus.N);
                tempMetaData.setCreatedBy("Maker1234");
                paymentDTO.setTempMetaData(tempMetaData);
                paymentDTO.setStatus(ReceiptStatus.UNREALISED);
                flag = paymentServiceTemp.createPaymentTemp(paymentDTO);
            }
            modelAndView.addObject("paymentMaker", paymentDTO);
            modelAndView.setViewName("payment-maker-final");
            if (flag) {
                model.addAttribute("successMessage", "Payment submitted successfully!,click cancel to Go back");

            } else {
                model.addAttribute("errorMessage", "Failed to submit payment. Please try again.");
            }
        } else{
            model.addAttribute("errorMessage", "Failed to fetch loanAccount number. Please try again.");

        }
        return modelAndView;
    }
}
