package org.nucleus.dao;/*
Developer: Vibhav Sehrawat
*/

import org.springframework.transaction.annotation.Transactional;
import org.nucleus.dto.ProductTypeDTO;

import java.util.List;

public interface ProductTypeTempDAO {

    boolean addProductType(ProductTypeDTO productType);

    boolean updateProductType(ProductTypeDTO productType);

    boolean deleteProductType(Long id);

    ProductTypeDTO getProductTypeByID(Long id);

    @Transactional(readOnly = true)
    ProductTypeDTO getProductTypeByCode(String code);

    List<ProductTypeDTO> getAllProductTypes();

    List<ProductTypeDTO> getAllValidProductTypes();

    ProductTypeDTO getProductTypeWithSaveFlag();
}
