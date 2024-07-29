package org.nucleus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.service.ProductTypeService;

import java.util.List;

@Controller
@PreAuthorize("hasRole('CHECKER')")
@RequestMapping("/checker")
public class ProductTypeCheckerController {
    private ProductTypeService productTypeService;

    @Autowired
    public ProductTypeCheckerController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @RequestMapping("/")
    public String homePage() {
        return "initial_product_dash";
    }

    @GetMapping("/product-type-dashboard")
    public String showProductTypeDashboard(Model model) {
        List<ProductTypeDTO> productTypeDTOList = productTypeService.getAllProductTypesForChecker();
        model.addAttribute("productTypeList", productTypeDTOList);
        return "product_type_checker_dash";
    }

    @GetMapping("/approve-product-type/{productTypeId}")
    public String approveProductType(@PathVariable("productTypeId") Long id, Model model) {
        String message = productTypeService.approveProductType(id);
        model.addAttribute("message", message);
        return "redirect:/checker/product-type-dashboard";
    }

    @GetMapping("/reject-product-type/{productTypeId}")
    public String rejectProductType(@PathVariable("productTypeId") Long id, Model model) {
        String message = productTypeService.rejectProductType(id);
        model.addAttribute("message", message);
        return "redirect:/checker/product-type-dashboard";
    }

}
