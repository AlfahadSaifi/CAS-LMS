package org.nucleus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.service.ProductTypeService;
import org.nucleus.utility.enums.RecordStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@PreAuthorize("hasRole('MAKER')")
@RequestMapping("/maker")
public class ProductTypeMakerController {

    ProductTypeService productTypeService;

    @Autowired
    public ProductTypeMakerController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping("/product-type-dashboard")
    public String showProductTypeDashboard(Model model) {
        List<ProductTypeDTO> productTypeDTOList = productTypeService.getAllProductTypesForMaker();
        model.addAttribute("productTypeList", productTypeDTOList);
        return "product_type_maker_dash";
    }

    @GetMapping("/product-type-form")
    public String showProductTypeForm(Model model) {
        model.addAttribute("productTypeTemp", productTypeService.getProductTypeWithSaveFlag());
        return "product_type_new_form";
    }

    // Method to Save OR SaveAndApprove ProductType in TEMP table
    @PostMapping("/save-and-approve-product-type")
    public String saveAndApproveProductType(@ModelAttribute("productTypeTemp")@Valid ProductTypeDTO productType, BindingResult result, @RequestParam("action") String action, Model model) {
        if(result.hasErrors()){
            return "product_type_new_form";
        }

        if ("saveAndRequestApproval".equals(action)) {
            productType.getMetaData().setSaveFlag(false);
        } else if ("save".equals(action)) {
            productType.getMetaData().setSaveFlag(true);
        }
        String message = productTypeService.saveProductType(productType);
        model.addAttribute("message", message);
        return "product_type_new_form";
    }

    @GetMapping("/show-update-product-type")
    public String showUpdateForm(@RequestParam("id") Long id, @RequestParam("status") String recordStatus, Model model) {
        ProductTypeDTO productTypeDTO = productTypeService.getProductTypeByIDAndStatus(id, RecordStatus.valueOf(recordStatus));
        model.addAttribute("productType", productTypeDTO);

        return "product_type_update_form";
    }

    @PostMapping("/update-product-type")
    public String updateProductType(@ModelAttribute("productType") ProductTypeDTO productType, Model model) {
        String message = productTypeService.updateProductType(productType);
        model.addAttribute("message", message);
        return "redirect:/maker/product-type-dashboard";
    }

    @GetMapping("/delete-product-type")
    public String deleteProductType(@RequestParam("id") Long id, @RequestParam("status") String recordStatus, Model model){

        productTypeService.deleteProductType(id, RecordStatus.valueOf(recordStatus));
        return "redirect:/maker/product-type-dashboard";
    }

}
