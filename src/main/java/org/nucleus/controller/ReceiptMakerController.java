package org.nucleus.controller;

import org.nucleus.dto.ReceiptDTO;
import org.nucleus.service.ReceiptService;
import org.nucleus.service.ReceivablePayableService;
import org.nucleus.utility.OldDateEditor;
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

@Controller
@RequestMapping("/maker")
@PreAuthorize("hasRole('MAKER')")
public class ReceiptMakerController {
    @InitBinder
    public void bind(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class,"transactionDate", new OldDateEditor());
        binder.registerCustomEditor(Date.class,"dateOfReceipt", new OldDateEditor());
        binder.registerCustomEditor(Date.class,"instrumentDate", new OldDateEditor());
    }
    @Autowired
    ReceiptService receiptService;
    @GetMapping("receipt")
    public String showReceivableForm(Model model) {  // show jsp form to enter details of receivable
        model.addAttribute("receiptMaker", new ReceiptDTO());
        return "forward:../views/receipt-maker-final.jsp";
    }

    @PostMapping("receipt-Submit")
    public ModelAndView handelReceiptSearchAndSubmit(@ModelAttribute("receiptMaker")@Valid ReceiptDTO receiptDTO, BindingResult errors,
                                                        @RequestParam("action")String action,Model model) {   //Handle Receivable search and submit on given receivablePayable-id
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("receipt-maker-final");
        if(errors.hasErrors()) {
            return modelAndView;
        }
        if (receiptDTO.getTransactionAmount() != null && receiptDTO.getLoanAccountNumber()!=null && receiptDTO.getRequiredAmount()!=null) {
            boolean flag = receiptService.submitReceipt(receiptDTO);
            if (flag) {
                modelAndView.addObject("receiptMaker", new ReceiptDTO());
                model.addAttribute("successMessage", "Payment submitted successfully!,click cancel to go back");
            } else {
                modelAndView.addObject("receiptMaker", receiptDTO);
                model.addAttribute("errorMessage", "Failed to submit payment,Transaction amount can not be greater than required amount. Please try again.");
            }
        } else {
            modelAndView.addObject("receiptMaker",new ReceiptDTO());
            model.addAttribute("errorMessage", "Failed to submit payment, Please try again.");
        }
        return modelAndView;
    }
}


