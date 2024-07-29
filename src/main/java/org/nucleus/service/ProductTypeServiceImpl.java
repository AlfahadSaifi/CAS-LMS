package org.nucleus.service;/*
Developer: Vibhav Sehrawat
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.nucleus.dao.ProductTypeDAO;
import org.nucleus.dao.ProductTypeTempDAO;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.utility.enums.RecordStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService
{
    private final ProductTypeDAO productTypeDAO;
    private final ProductTypeTempDAO productTypeTempDAO;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeDAO productTypeDAO, ProductTypeTempDAO productTypeTempDAO) {
        this.productTypeDAO = productTypeDAO;
        this.productTypeTempDAO = productTypeTempDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductTypeDTO> getAllProductTypesForMaker() {
        List<ProductTypeDTO> productTypeTempList = productTypeTempDAO.getAllProductTypes();
        List<ProductTypeDTO> productTypeList = productTypeDAO.getAllValidProductTypes();

        List<ProductTypeDTO> filteredProductTypeList = new ArrayList<>(productTypeList);
        for (ProductTypeDTO temp : productTypeTempList)
            filteredProductTypeList.removeIf(productType -> productType.getProductTypeCode().equals(temp.getProductTypeCode()));

        filteredProductTypeList.addAll(productTypeTempList);

        return filteredProductTypeList;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductTypeDTO getProductTypeWithSaveFlag() {
        ProductTypeDTO productTypeTemp =  productTypeTempDAO.getProductTypeWithSaveFlag();
        if (productTypeTemp == null) {
            return new ProductTypeDTO();
        } else {
            return productTypeTemp;
        }
    }

    @Override
    public String saveProductType(ProductTypeDTO productType) {
        String code = productType.getProductTypeCode();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ProductTypeDTO existingProductType = productTypeTempDAO.getProductTypeByCode(code);
        ProductTypeDTO existingProductTypeMaster = productTypeDAO.getProductTypeByCode(code);
        if (existingProductType == null && existingProductTypeMaster == null) {
            productType.getMetaData().setRecordStatus(RecordStatus.N);
            productType.getMetaData().setCreatedBy(username);
            productType.getMetaData().setCreationDate(Date.valueOf(LocalDate.now()));

            if (productTypeTempDAO.addProductType(productType)) {
                return "Product type requested to be added!";
            } else {
                return "Request Failed!";
            }
        } else if (existingProductType != null && Boolean.TRUE.equals(existingProductType.getMetaData().getSaveFlag())) {
            existingProductType.getMetaData().setRecordStatus(RecordStatus.N);
            existingProductType.getMetaData().setSaveFlag(productType.getMetaData().getSaveFlag());
            existingProductType.getMetaData().setCreatedBy(username);
            existingProductType.getMetaData().setCreationDate(Date.valueOf(LocalDate.now()));
            return "Product type requested to be Added!";
        } else {
            return "Product Type Already Exists!";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductTypeDTO getProductTypeByIDAndStatus(Long id, RecordStatus status) {
        if(status.equals(RecordStatus.A)) {
            return productTypeDAO.getProductTypeByID(id);
        }
        else {
            return productTypeTempDAO.getProductTypeByID(id);
        }
    }

    @Override
    public String updateProductType(ProductTypeDTO productType) {
        RecordStatus status = productType.getMetaData().getRecordStatus();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if(status.equals(RecordStatus.A)) {
            ProductTypeDTO fullProductType = productTypeDAO.getProductTypeByID(productType.getProductTypeId());
            productType.setMetaData(fullProductType.getMetaData());
            productType.getMetaData().setRecordStatus(RecordStatus.M);

            productType.getMetaData().setModifiedBy(username);
            productType.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));

            if (productTypeTempDAO.addProductType(productType)) {
                return "Product type requested to be updated!";
            } else {
                return "Request Failed!";
            }
        }
        else {
            Long id = productType.getProductTypeId();
            ProductTypeDTO existingProductType = productTypeTempDAO.getProductTypeByID(id);
            if(status.equals(RecordStatus.NR)) {
                existingProductType.getMetaData().setRecordStatus(RecordStatus.N);
            }
            else if(status.equals(RecordStatus.MR)) {
                existingProductType.getMetaData().setRecordStatus(RecordStatus.M);
            }
            existingProductType.setDescription(productType.getDescription());
            existingProductType.setSecuredFlag(productType.getSecuredFlag());
            existingProductType.setFundBasedFlag(productType.getFundBasedFlag());

            existingProductType.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            existingProductType.getMetaData().setModifiedBy(username);

            if (productTypeTempDAO.updateProductType(existingProductType)) {
                return "Product Type Updated Successfully!";
            } else {
                return "Updation Unsuccessful!";
            }
        }
    }

    @Override
    public String deleteProductType(Long id, RecordStatus status) {

        if (status.equals(RecordStatus.A)) {
            ProductTypeDTO productType = productTypeDAO.getProductTypeByID(id);
            productType.getMetaData().setRecordStatus(RecordStatus.D);
            if (productTypeTempDAO.addProductType(productType)) {
                return "Product type requested for deletion!";
            } else {
                return "Request failed";
            }
        } else {
            if (productTypeTempDAO.deleteProductType(id)) {
                return "Product type deleted successfully!";
            } else {
                return "Request Failed";
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductTypeDTO> getAllProductTypesForChecker() {
        List<ProductTypeDTO> productTypeTempList = productTypeTempDAO.getAllValidProductTypes();
        List<ProductTypeDTO> productTypeList = productTypeDAO.getAllProductTypes();

        List<ProductTypeDTO> filteredProductTypeList = new ArrayList<>(productTypeList);
        for (ProductTypeDTO temp : productTypeTempList)
            filteredProductTypeList.removeIf(productType -> productType.getProductTypeCode().equals(temp.getProductTypeCode()));

        filteredProductTypeList.addAll(productTypeTempList);

        return filteredProductTypeList;
    }

    @Override
    public String approveProductType(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ProductTypeDTO productType = productTypeTempDAO.getProductTypeByID(id);
        RecordStatus status = productType.getMetaData().getRecordStatus();

        if (status.equals(RecordStatus.M)) {
            ProductTypeDTO productTypeMaster = productTypeDAO.getProductTypeByCode(productType.getProductTypeCode());
            productType.setProductTypeId(productTypeMaster.getProductTypeId());
            productType.getMetaData().setRecordStatus(RecordStatus.A);
            productType.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
            productType.getMetaData().setAuthorizedBy(username);

            if (productTypeTempDAO.deleteProductType(id) && productTypeDAO.updateProductType(productType)) {
                return "Modification Successfully Approved!";
            }
            else {
                return "Approval Unsuccessful!";
            }
        }
        else if(status.equals(RecordStatus.D)) {
            ProductTypeDTO productTypeMaster = productTypeDAO.getProductTypeByCode(productType.getProductTypeCode());
            productTypeMaster.getMetaData().setActiveInactiveFlag(Boolean.FALSE);

            if (productTypeDAO.updateProductType(productTypeMaster) && productTypeTempDAO.deleteProductType(id)) {
                return "Deletion Successfully Approved!";
            }
            else {
                return "Approval Unsuccessful!";
            }
        }
        else if(status.equals(RecordStatus.N)) {
            productType.getMetaData().setRecordStatus(RecordStatus.A);
            productType.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
            productType.getMetaData().setAuthorizedBy(username);

            if(productTypeDAO.addProductType(productType) && productTypeTempDAO.deleteProductType(id)) {
                return "Successfully Approved!";
            }
            else {
                return  "Approval Unsuccessful!";
            }
        }
        return null;
    }

    @Override
    public String rejectProductType(Long id) {
        ProductTypeDTO productType = productTypeTempDAO.getProductTypeByID(id);
        RecordStatus status = productType.getMetaData().getRecordStatus();

        if(status.equals(RecordStatus.N))
            productType.getMetaData().setRecordStatus(RecordStatus.NR);
        else if(status.equals(RecordStatus.M))
            productType.getMetaData().setRecordStatus(RecordStatus.MR);
        else if(status.equals(RecordStatus.D))
            productType.getMetaData().setRecordStatus(RecordStatus.DR);

        if(productTypeTempDAO.updateProductType(productType)) {
            return "Successfully Rejected!";
        }
        else {
            return "Rejection Unsuccessful!";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllProductTypeCodes() {
        return productTypeDAO.getAllProductTypeCodes();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductTypeDTO getProductTypeByCode(String code) {
        return productTypeDAO.getProductTypeByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductTypeDTO> getAllValidProductTypes() {
        return productTypeDAO.getAllValidProductTypes();
    }
}
