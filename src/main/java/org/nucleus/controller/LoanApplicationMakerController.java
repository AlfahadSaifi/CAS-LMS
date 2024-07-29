package org.nucleus.controller;


import org.nucleus.dto.CustomerDTO;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.service.LoanApplicationService;
import org.nucleus.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/maker")
@PreAuthorize("hasRole('MAKER')")
public class LoanApplicationMakerController {

    private final LoanApplicationService loanApplicationService;
    private final ProductTypeService productTypeService;
    private CustomerDTO customerDTO;
    @Autowired
    public LoanApplicationMakerController(LoanApplicationService loanApplicationService, ProductTypeService productTypeService){
        this.loanApplicationService = loanApplicationService;
        this.productTypeService=productTypeService;
    }



    // Get Loan Form for loan application for particular customer
    private String loanApplicationId=null;
    @GetMapping("/loanForm")
    public ModelAndView loanFormRequest(@ModelAttribute CustomerDTO customerDTO){
        LoanApplicationDTO loanApplicationDTO=new LoanApplicationDTO();
        loanApplicationDTO.setCustomerDTO(customerDTO);
        this.customerDTO = customerDTO;

        if(loanApplicationId==null)loanApplicationId=loanApplicationService.generateLoanApplicationId();
        loanApplicationDTO.setLoanApplicationId(loanApplicationId);

        return new ModelAndView("loanForm", "loanApplication", loanApplicationDTO);
    }

    @ModelAttribute(name = "productTypeDTOS")
    public List<ProductTypeDTO> getAllProductType(){
        return productTypeService.getAllValidProductTypes();
    }


    @PostMapping("/loan-apply")
    public String applyLoanApplication(@ModelAttribute("loanApplication") @Valid LoanApplicationDTO loanApplicationDTO,BindingResult result,@ModelAttribute CustomerDTO customerDTO, Model model){
        loanApplicationDTO.setCustomerDTO(this.customerDTO);
        if(result.hasErrors()){
            String loanApplicationId=loanApplicationService.generateLoanApplicationId();
            loanApplicationDTO.setLoanApplicationId(loanApplicationId);
            model.addAttribute("loanApplication",loanApplicationDTO);

            return "loanForm";
        }

        Long loanId=loanApplicationService.createTemporary(loanApplicationDTO);
        if(loanId!=null)loanApplicationId=null;

        return "redirect:view-loan-applications";
    }


    // read all loan applications
    @RequestMapping("/view-loan-applications")
    public ModelAndView getAllLoanApplication(){
        // read all loan applications from both table
        List<LoanApplicationDTO> loanApplicationDTOS = loanApplicationService.readAll();

        return new ModelAndView("loanDetailsTable", "loanApplicationDTOS", loanApplicationDTOS);
    }

    // for loan update get loan application by loan id and show update form
    @GetMapping("/update-loan-application/{loanApplicationId}")
    public ModelAndView updateLoanApplicationForm(@PathVariable("loanApplicationId") String loanApplicationId){
        LoanApplicationDTO loanApplication = loanApplicationService.readAny(loanApplicationId);

        return new ModelAndView("loanUpdateForm", "loanApplication",loanApplication);

    }

    // update loan application
    @PostMapping("/update-loan-application")
    public String updateLoan(@ModelAttribute LoanApplicationDTO loanApplicationDTO){
        loanApplicationService.updateTemporary(loanApplicationDTO);

        return "redirect:view-loan-applications";
    }


    // delete loan application from temp table

    @RequestMapping("/delete-loan-application/{loanApplicationId}")
    public String deleteLoanApplication(@PathVariable("loanApplicationId") String loanApplicationId){
        loanApplicationService.delete(loanApplicationId);

        return "redirect:../view-loan-applications";
    }


}
