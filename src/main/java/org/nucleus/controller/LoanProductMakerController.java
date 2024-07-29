package org.nucleus.controller;/*
Developer: Vibhav Sehrawat
*/

import org.nucleus.dto.ChargePolicyDto;
import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.permanent.EligibilityPolicy;
import org.nucleus.service.ChargePolicyService;
import org.nucleus.service.EligibilityPolicyCheckerService;
import org.nucleus.service.RepaymentPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.service.LoanProductService;
import org.nucleus.service.ProductTypeService;
import org.nucleus.utility.enums.RecordStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasRole('MAKER')")
@RequestMapping("/maker")
public class LoanProductMakerController {

    private LoanProductService loanProductService;
    private ProductTypeService productTypeService;
    private RepaymentPolicyService repaymentPolicyService;
    private ChargePolicyService chargePolicyService;
    private EligibilityPolicyCheckerService eligibilityPolicyCheckerService;

    @Autowired
    public LoanProductMakerController(LoanProductService loanProductService, ProductTypeService productTypeService, RepaymentPolicyService repaymentPolicyService, ChargePolicyService chargePolicyService, EligibilityPolicyCheckerService eligibilityPolicyCheckerService) {
        this.loanProductService = loanProductService;
        this.productTypeService = productTypeService;
        this.repaymentPolicyService = repaymentPolicyService;
        this.chargePolicyService = chargePolicyService;
        this.eligibilityPolicyCheckerService = eligibilityPolicyCheckerService;
    }

    @GetMapping("/loan-product-dashboard")
    public String showLoanProductDashboard(Model model) {
        List<LoanProductDTO> loanProductDTOList = loanProductService.getAllLoanProductsForMaker();
        model.addAttribute("loanProductList", loanProductDTOList);
        return "loan_product_maker_dash";
    }

    @GetMapping("/loan-product-form")
    public String showLoanProductNewForm(Model model) {
        model.addAttribute("loanProductTemp", loanProductService.getLoanProductWithSaveFlag());
        List<String> productTypeCodes = productTypeService.getAllProductTypeCodes();

        List<RepaymentPolicyDTO> repaymentPolicyDTOs = repaymentPolicyService.getAllRepaymentPolicy();
        List<String> repaymentPolicyCodes = null;
        if (repaymentPolicyDTOs != null) {
            repaymentPolicyCodes = repaymentPolicyDTOs
                    .stream()
                    .map(RepaymentPolicyDTO::getRepaymentPolicyCode)
                    .collect(Collectors.toList());
        }

        List<ChargePolicyDto> chargePolicyDtos = chargePolicyService.getAllChargePolicy();
        List<String> chargePolicyCodes = null;
        if (chargePolicyDtos != null) {
            chargePolicyCodes = chargePolicyDtos
                    .stream()
                    .map(ChargePolicyDto::getPolicyCode)
                    .collect(Collectors.toList());
        }

        List<EligibilityPolicy> eligibilityPolicyDTOs = eligibilityPolicyCheckerService.viewAllEligibilityPolicy();
        List<String> eligibilityPolicyCodes = null;
        if (eligibilityPolicyDTOs != null) {
            eligibilityPolicyCodes = eligibilityPolicyDTOs
                    .stream()
                    .map(EligibilityPolicy::getEligibilityPolicyCode)
                    .collect(Collectors.toList());
        }
        model.addAttribute("productTypeCodes", productTypeCodes);
        model.addAttribute("chargePolicyCodes", chargePolicyCodes);
        model.addAttribute("eligibilityPolicyCodes", eligibilityPolicyCodes);
        model.addAttribute("repaymentPolicyCodes", repaymentPolicyCodes);
        return "loan_product_new_form";
    }

    @GetMapping("/fetch-product-type-description")
    @ResponseBody
    public String fetchProductTypeDescription(@RequestParam("selectedValue") String productTypeCode) {
        return productTypeService.getProductTypeByCode(productTypeCode).getDescription();
    }

    @GetMapping("/fetch-charge-policy-name")
    @ResponseBody
    public String fetchChargePolicyName(@RequestParam("selectedValue") String chargePolicyCode) {
        return chargePolicyService.getChargePolicy(chargePolicyCode).getPolicyName();
    }

    @GetMapping("/fetch-repayment-policy-name")
    @ResponseBody
    public String fetchEligibilityPolicyName(@RequestParam("selectedValue") String repaymentPolicyCode) {
        return repaymentPolicyService.getRepaymentPolicyById(repaymentPolicyCode).getRepaymentPolicyName();
    }

    @GetMapping("/fetch-eligibility-policy-name")
    @ResponseBody
    public String fetchRepaymentPolicyName(@RequestParam("selectedValue") String eligibilityPolicyCode) {
        return eligibilityPolicyCheckerService.getEligibilityPolicyByCode(eligibilityPolicyCode).getPolicyName();
    }

    @PostMapping("/save-and-approve-loan-product")
    public String saveLoanProductData(@ModelAttribute("loanProductTemp") @Valid LoanProductDTO loanProductDTO, BindingResult result, @RequestParam("action") String action, Model model) {
        if(result.hasErrors()) {
            return "loan_product_new_form";
        }
        if ("Save And Request Approval".equals(action)) {
            loanProductDTO.getMetaData().setSaveFlag(false);
        } else if ("Save".equals(action)) {
            loanProductDTO.getMetaData().setSaveFlag(true);
        }
        String message = loanProductService.saveLoanProduct(loanProductDTO);
        model.addAttribute("message", message);
        return "loan_product_new_form";
    }

    @GetMapping("/show-update-loan-product-form")
    public String showUpdateLoanProductForm(@RequestParam("id") long id, @RequestParam("status") String status, Model model) {
        LoanProductDTO loanProductDTO = loanProductService.getLoanProductByIDAndStatus(id, RecordStatus.valueOf(status));
        List<String> productTypeCodes = productTypeService.getAllProductTypeCodes();

        List<RepaymentPolicyDTO> repaymentPolicyDTOs = repaymentPolicyService.getAllRepaymentPolicy();
        List<String> repaymentPolicyCodes = null;
        if (repaymentPolicyDTOs != null) {
            repaymentPolicyCodes = repaymentPolicyDTOs
                    .stream()
                    .map(RepaymentPolicyDTO::getRepaymentPolicyCode)
                    .collect(Collectors.toList());
        }

        List<ChargePolicyDto> chargePolicyDtos = chargePolicyService.getAllChargePolicy();
        List<String> chargePolicyCodes = null;
        if (chargePolicyDtos != null) {
            chargePolicyCodes = chargePolicyDtos
                    .stream()
                    .map(ChargePolicyDto::getPolicyCode)
                    .collect(Collectors.toList());
        }

        List<EligibilityPolicy> eligibilityPolicyDTOs = eligibilityPolicyCheckerService.viewAllEligibilityPolicy();
        List<String> eligibilityPolicyCodes = null;
        if (eligibilityPolicyDTOs != null) {
            eligibilityPolicyCodes = eligibilityPolicyDTOs
                    .stream()
                    .map(EligibilityPolicy::getEligibilityPolicyCode)
                    .collect(Collectors.toList());
        }

        model.addAttribute("productTypeCodes", productTypeCodes);
        model.addAttribute("chargePolicyCodes", chargePolicyCodes);
        model.addAttribute("loanProduct", loanProductDTO);
        model.addAttribute("repaymentPolicyCodes", repaymentPolicyCodes);
        model.addAttribute("eligibilityPolicyCodes", eligibilityPolicyCodes);

        return "loan_product_update_form";
    }


    @PostMapping("/update-loan-product")
    public String updateLoanProduct(@ModelAttribute("loanProduct") LoanProductDTO loanProduct, Model model) {
        String message = loanProductService.updateLoanProduct(loanProduct);
        model.addAttribute("message", message);
        return "redirect:/maker/loan-product-dashboard";
    }

    @GetMapping("/delete-loan-product")
    public String deleteLoanProduct(@RequestParam("id") long id, @RequestParam("status") String status, Model model) {
        loanProductService.deleteLoanProduct(id, RecordStatus.valueOf(status));
        return "redirect:/maker/loan-product-dashboard";
    }

}
