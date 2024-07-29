package org.nucleus.controller;/*
Developer: Vibhav Sehrawat
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.service.LoanProductService;

import java.util.List;

@Controller
@PreAuthorize("hasRole('CHECKER')")
@RequestMapping("/checker")
public class LoanProductCheckerController {

    private LoanProductService loanProductService;

    @Autowired
    public LoanProductCheckerController(LoanProductService loanProductService) {
        this.loanProductService = loanProductService;
    }


    @GetMapping("/loan-product-dashboard")
    public String showLoanProductDashboard(Model model) {
        List<LoanProductDTO> loanProductDTOList = loanProductService.getAllLoanProductForChecker();
        model.addAttribute("loanProductList", loanProductDTOList);
        return "loan_product_checker_dash";
    }


    @GetMapping("/approve-loan-product/{productId}")
    public String approveLoanProduct(@PathVariable("productId") Long id, Model model){
        String message = loanProductService.approveLoanProduct(id);
        model.addAttribute("message", message);
        return "redirect:/checker/loan-product-dashboard";
    }


    @GetMapping("/reject-loan-product/{productId}")
    public String rejectLoanProduct(@PathVariable("productId") Long id,Model model){
        String message = loanProductService.rejectLoanProduct(id);
        model.addAttribute("message", message);
        return "redirect:/checker/loan-product-dashboard";
    }

}
