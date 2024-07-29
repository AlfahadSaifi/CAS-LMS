package org.nucleus.service;/*
Developer: Vibhav Sehrawat
*/

import org.springframework.transaction.annotation.Transactional;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.utility.enums.RecordStatus;

import java.util.List;

public interface ProductTypeService {

    List<ProductTypeDTO> getAllProductTypesForMaker();

    ProductTypeDTO getProductTypeWithSaveFlag();

    String saveProductType(ProductTypeDTO productType);

    ProductTypeDTO getProductTypeByIDAndStatus(Long id, RecordStatus status);

    String updateProductType(ProductTypeDTO productType);

    String deleteProductType(Long id, RecordStatus status);

    List<ProductTypeDTO> getAllProductTypesForChecker();

    String approveProductType(Long id);

    String rejectProductType(Long id);

    List<String> getAllProductTypeCodes();

    @Transactional(readOnly = true)
    ProductTypeDTO getProductTypeByCode(String code);
    @Transactional(readOnly = true)
    List<ProductTypeDTO> getAllValidProductTypes();
}
