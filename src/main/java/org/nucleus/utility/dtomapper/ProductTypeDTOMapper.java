package org.nucleus.utility.dtomapper;/*
Developer: Vibhav Sehrawat
*/

import org.springframework.stereotype.Component;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.entity.permanent.ProductType;
import org.nucleus.entity.temporary.ProductTypeTemp;

@Component
public class ProductTypeDTOMapper {

    public ProductTypeTemp dtoToTemp(ProductTypeDTO dto) {
        ProductTypeTemp productTypeTemp = new ProductTypeTemp();
        if(dto != null) {
            productTypeTemp.setProductTypeId(dto.getProductTypeId());
            productTypeTemp.setProductTypeCode(dto.getProductTypeCode());
            productTypeTemp.setDescription(dto.getDescription());
            productTypeTemp.setFundBasedFlag(dto.getFundBasedFlag());
            productTypeTemp.setSecuredFlag(dto.getSecuredFlag());
            productTypeTemp.setMetaData(dto.getMetaData());
            return productTypeTemp;
        }
        return null;
    }

    public ProductTypeDTO tempToDto(ProductTypeTemp productTypeTemp) {
        ProductTypeDTO dto = new ProductTypeDTO();
        if(productTypeTemp != null) {
            dto.setProductTypeId(productTypeTemp.getProductTypeId());
            dto.setProductTypeCode(productTypeTemp.getProductTypeCode());
            dto.setDescription(productTypeTemp.getDescription());
            dto.setFundBasedFlag(productTypeTemp.getFundBasedFlag());
            dto.setSecuredFlag(productTypeTemp.getSecuredFlag());
            dto.setMetaData(productTypeTemp.getMetaData());
            return dto;
        }
        return null;
    }

    public ProductType dtoToMaster(ProductTypeDTO dto) {
        ProductType productType = new ProductType();
        if(dto != null) {
            productType.setProductTypeId(dto.getProductTypeId());
            productType.setProductTypeCode(dto.getProductTypeCode());
            productType.setDescription(dto.getDescription());
            productType.setFundBasedFlag(dto.getFundBasedFlag());
            productType.setSecuredFlag(dto.getSecuredFlag());
            productType.setMetaData(MetaDataMapper.convertToMetaData(dto.getMetaData()));
            return productType;
        }
        return null;
    }

    public ProductTypeDTO masterToDto(ProductType productType) {
        ProductTypeDTO dto = new ProductTypeDTO();
        if(productType != null) {
            dto.setProductTypeId(productType.getProductTypeId());
            dto.setProductTypeCode(productType.getProductTypeCode());
            dto.setDescription(productType.getDescription());
            dto.setFundBasedFlag(productType.getFundBasedFlag());
            dto.setSecuredFlag(productType.getSecuredFlag());
            dto.setMetaData(MetaDataMapper.convertToTempMetaData(productType.getMetaData()));
            return dto;
        }
        return null;
    }



}
