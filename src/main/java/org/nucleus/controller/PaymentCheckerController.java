package org.nucleus.controller;

import org.nucleus.dto.PaymentDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.PaymentService;
import org.nucleus.service.PaymentServiceTemp;
import org.nucleus.utility.OldDateEditor;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.DateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("checker")
@PreAuthorize("hasRole('CHECKER')")
public class PaymentCheckerController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentServiceTemp paymentServiceTemp;
    @InitBinder
    public void bind(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class,"dueDate", new OldDateEditor());
        binder.registerCustomEditor(Date.class,"fromDate", new OldDateEditor());

    }
    @GetMapping("payment")
    public String showPayableForm(Model model) {   // show jsp form to search receivablePayable-id
        model.addAttribute("paymentChecker", new PaymentDTO());
        return "forward:../views/payment-checker-final.jsp";
    }
    @PostMapping("payment-Search")
    public ModelAndView handelPayableSearch(@ModelAttribute PaymentDTO paymentDTO,
                                            @RequestParam("dueDate") Date dueDate,
                                            @RequestParam("fromDate") Date fromDate,
                                            Model model) {  //show List of Receipt on given receivablePayable-id
            ModelAndView modelAndView = new ModelAndView("../payment-checker-final");
             modelAndView.addObject("paymentChecker", paymentDTO);
            if(paymentDTO.getReceivablePayable().getReceivablePayableId()!=null) {
                List<PaymentDTO> paymentDTOList = paymentServiceTemp.getListPaymentTempById(paymentDTO.getReceivablePayable().getReceivablePayableId());  //getList
                if(!paymentDTOList.isEmpty()) {
                    List<PaymentDTO> filteredPaymentList =
                            paymentDTOList
                                    .stream()
                                    .filter(payment -> payment.getPaymentDate().compareTo(fromDate) >= 0 && payment.getPaymentDate().compareTo(dueDate) <= 0)
                                    .collect(Collectors.toList());
                    if(!filteredPaymentList.isEmpty()) {
                        model.addAttribute("successMessage", "Payable records has been successfully fetched.click cancel to Go back");
                        model.addAttribute("paymentList", filteredPaymentList);
                    } else {
                        model.addAttribute("errorMessage", "No payments found between the provided due date and from date. Please ensure the date is correct and try again.");
                    }
                } else {
                    model.addAttribute("errorMessage", "Failed to fetch data for the provided Receivable Payable Id. Please ensure the ID is correct and try again.");
                }
            } else {
                    model.addAttribute("errorMessage", "Failed to fetch data for the provided Receivable Payable Id. Please ensure the ID is correct and try again.");
            }
        return modelAndView;
    }
    @GetMapping("/approve-Payment/{receiptId}")
    public ModelAndView approvePayable(@ModelAttribute PaymentDTO paymentDTO,
            @PathVariable String receiptId,Model model) {  //Approve payable on particular receipt-id
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("paymentChecker", paymentDTO);
        modelAndView.setViewName("../../payment-checker-final");
        if (receiptId != null) {
            PaymentDTO paymentTempById = paymentServiceTemp.getPaymentTempById(Long.valueOf(receiptId)); //Fetch paymentDTO from temp table given receipt-id
            if (paymentTempById != null) {
                boolean flag = paymentServiceTemp.deletePaymentTemp(paymentTempById); //Delete from Temp table
                if (flag) {
                    TempMetaData tempMetaData = paymentTempById.getTempMetaData();  //Set Meta data value
                    tempMetaData.setAuthorizedDate(Date.valueOf(LocalDate.now()));
                    tempMetaData.setRecordStatus(RecordStatus.A);
                    boolean flagSubmit = paymentService.createPaymentDTO(paymentTempById);  //Insert into permanent table of Payment
                    if (flagSubmit) {
                        model.addAttribute("successMessage", "Payable records has been successfully approved.click back to Go back");
                    } else {
                        model.addAttribute("errorMessage", "Payable records is not approved!.Error while Inserting records in permanent table");
                    }
                } else {
                    model.addAttribute("errorMessage", "Payable records is not delete!.Error while deleting records in temporary table");
                }
            } else {
                model.addAttribute("errorMessage", "Payable records is not fetched!.Error while fetching records in temporary table");
            }
        } else {
            model.addAttribute("errorMessage", "Failed to fetch data for the provided Receivable Payable Id. Please ensure the ID is correct and try again.");
        }
        return modelAndView;
    }

    @GetMapping("/reject-Payment/{receiptId}")
    public ModelAndView rejectPayable (@ModelAttribute PaymentDTO paymentDTO,@PathVariable String receiptId, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("paymentChecker", paymentDTO);
        modelAndView.setViewName("../../payment-checker-final");
        if (receiptId != null) {
            PaymentDTO paymentTempById = paymentServiceTemp.getPaymentTempById(Long.valueOf(receiptId)); //Fetch paymentDTO from temp table given receipt-id
            if (paymentTempById != null) {
                TempMetaData tempMetaData = paymentDTO.getTempMetaData();  //set Meta data
                tempMetaData.setRecordStatus(RecordStatus.NR);
                paymentDTO.setTempMetaData(tempMetaData);
                boolean flag = paymentServiceTemp.updatePaymentTemp(paymentTempById); //Update in temp data
                if (flag) {
                    model.addAttribute("successMessage", "Payable records has been successfully Rejected.click back to go back");
                } else {
                    model.addAttribute("errorMessage", "Payable records is not rejected.");
                }
            } else {
                model.addAttribute("errorMessage", "Payable records is not fetched!.Error while fetching records in temporary table");
            }
        } else {
            model.addAttribute("errorMessage", "Failed to fetch data for the provided Receivable Payable Id. Please ensure the ID is correct and try again.");
        }
       return modelAndView;
    }

}
