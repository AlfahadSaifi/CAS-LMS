package org.nucleus.dao;/*
Developer: Vibhav Sehrawat
*/


import org.nucleus.dto.ProductTypeDTO;

import java.util.List;

public interface ProductTypeDAO {
    boolean addProductType(ProductTypeDTO productType);

    boolean updateProductType(ProductTypeDTO productType);

    boolean deleteProductType(Long id);

    ProductTypeDTO getProductTypeByID(Long id);

    ProductTypeDTO getProductTypeByCode(String code);

    List<ProductTypeDTO> getAllProductTypes();

    List<ProductTypeDTO> getAllValidProductTypes();

    List<String> getAllProductTypeCodes();
}
