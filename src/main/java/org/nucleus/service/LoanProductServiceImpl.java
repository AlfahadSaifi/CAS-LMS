package org.nucleus.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.nucleus.dao.LoanProductDAO;
import org.nucleus.dao.LoanProductTempDAO;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.utility.enums.RecordStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class LoanProductServiceImpl implements LoanProductService{

    private final LoanProductDAO loanProductDAO;
    private final LoanProductTempDAO loanProductTempDAO;

    @Autowired
    public LoanProductServiceImpl(LoanProductDAO loanProductDAO, LoanProductTempDAO loanProductTempDAO) {
        this.loanProductDAO = loanProductDAO;
        this.loanProductTempDAO = loanProductTempDAO;
    }


    @Override
    @Transactional(readOnly = true)
    public List<LoanProductDTO> getAllLoanProductsForMaker() {
        List<LoanProductDTO> loanProductTempList = loanProductTempDAO.getAllLoanProducts();
        List<LoanProductDTO> loanProductList = loanProductDAO.getAllValidLoanProducts();

        List<LoanProductDTO> filteredLoanProductList = new ArrayList<>(loanProductList);
        for (LoanProductDTO list : loanProductTempList) {
            filteredLoanProductList.removeIf(loanProduct -> loanProduct.getProductCode().equals(list.getProductCode()));
        }

        filteredLoanProductList.addAll(loanProductTempList);
        return filteredLoanProductList;
    }

    @Override
    @Transactional(readOnly = true)
    public LoanProductDTO getLoanProductWithSaveFlag() {
        LoanProductDTO loanProductTemp =  loanProductTempDAO.getLoanProductWithSaveFlag();
        if (loanProductTemp == null) {
            return new LoanProductDTO();
        } else {
            return loanProductTemp;
        }
    }

    @Override
    public String saveLoanProduct(LoanProductDTO loanProduct) {
        String code = loanProduct.getProductCode();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        LoanProductDTO existingLoanProduct = loanProductTempDAO.getLoanProductByCode(code);
        LoanProductDTO existingLoanProductMaster = loanProductDAO.getLoanProductByCode(code);
        if (existingLoanProduct == null && existingLoanProductMaster == null) {
            loanProduct.getMetaData().setRecordStatus(RecordStatus.N);
            loanProduct.getMetaData().setCreatedBy(username);
            loanProduct.getMetaData().setCreationDate(Date.valueOf(LocalDate.now()));

            if (loanProductTempDAO.addLoanProduct(loanProduct)) {
                return "Loan Product requested to be added!";
            }
            else {
                return "Request Failed!";
            }
        }
        else if (existingLoanProduct != null && Boolean.TRUE.equals(existingLoanProduct.getMetaData().getSaveFlag())) {
            existingLoanProduct.getMetaData().setRecordStatus(RecordStatus.N);
            existingLoanProduct.getMetaData().setSaveFlag(loanProduct.getMetaData().getSaveFlag());
            existingLoanProduct.getMetaData().setCreatedBy(username);
            existingLoanProduct.getMetaData().setCreationDate(Date.valueOf(LocalDate.now()));
            return "Loan Product requested to be Added!";
        }
        else {
            return "Loan Product Already Exists!";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LoanProductDTO getLoanProductByIDAndStatus(Long id, RecordStatus status) {
        if(status.equals(RecordStatus.A)) {
            return loanProductDAO.getLoanProductByID(id);
        }
        else {
            return loanProductTempDAO.getLoanProductByID(id);
        }
    }

    @Override
    public String updateLoanProduct(LoanProductDTO loanProduct) {
        RecordStatus status = loanProduct.getMetaData().getRecordStatus();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if(status.equals(RecordStatus.A)) {
            LoanProductDTO fullLoanProduct = loanProductDAO.getLoanProductByID(loanProduct.getProductId());
            loanProduct.setMetaData(fullLoanProduct.getMetaData());
            loanProduct.getMetaData().setRecordStatus(RecordStatus.M);

            loanProduct.getMetaData().setModifiedBy(username);
            loanProduct.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));

            if (loanProductTempDAO.addLoanProduct(loanProduct)) {
                return "Loan Product requested to be updated!";
            } else {
                return "Request Failed!";
            }
        }
        else {
            Long id = loanProduct.getProductId();
            LoanProductDTO existingLoanProduct = loanProductTempDAO.getLoanProductByID(id);
            if(status.equals(RecordStatus.NR)) {
                existingLoanProduct.getMetaData().setRecordStatus(RecordStatus.N);
            }
            else if(status.equals(RecordStatus.MR)) {
                existingLoanProduct.getMetaData().setRecordStatus(RecordStatus.M);
            }
            existingLoanProduct.setProductDescription(loanProduct.getProductDescription());
            existingLoanProduct.setProductTypeCode(loanProduct.getProductTypeCode());
            existingLoanProduct.setRateOfInterest(loanProduct.getRateOfInterest());
            existingLoanProduct.setSecurityType(loanProduct.getSecurityType());
            existingLoanProduct.setProductName(loanProduct.getProductName());

            existingLoanProduct.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            existingLoanProduct.getMetaData().setModifiedBy(username);

            if (loanProductTempDAO.updateLoanProduct(existingLoanProduct)) {
                return "Loan Product Updated Successfully!";
            } else {
                return "Updation Unsuccessful!";
            }
        }
    }

    @Override
    public String deleteLoanProduct(Long id, RecordStatus status) {
        if (status.equals(RecordStatus.A)) {
            LoanProductDTO loanProduct = loanProductDAO.getLoanProductByID(id);
            loanProduct.getMetaData().setRecordStatus(RecordStatus.D);
            if (loanProductTempDAO.addLoanProduct(loanProduct)) {
                return "Loan Product requested for deletion!";
            } else {
                return "Request failed";
            }
        } else {
            if (loanProductTempDAO.deleteLoanProduct(id)) {
                return "Loan Product deleted successfully!";
            } else {
                return "Request Failed";
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanProductDTO> getAllLoanProductForChecker() {
        List<LoanProductDTO> loanProductTempList = loanProductTempDAO.getAllValidLoanProducts();
        List<LoanProductDTO> loanProductList = loanProductDAO.getAllLoanProducts();

        List<LoanProductDTO> filteredLoanProductList = new ArrayList<>(loanProductList);
        for (LoanProductDTO temp : loanProductTempList)
            filteredLoanProductList.removeIf(loanProduct -> loanProduct.getProductCode().equals(temp.getProductCode()));

        filteredLoanProductList.addAll(loanProductTempList);

        return filteredLoanProductList;
    }

    @Override
    public String approveLoanProduct(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        LoanProductDTO loanProduct = loanProductTempDAO.getLoanProductByID(id);
        RecordStatus status = loanProduct.getMetaData().getRecordStatus();

        if (status.equals(RecordStatus.M)) {
            LoanProductDTO loanProductMaster = loanProductDAO.getLoanProductByCode(loanProduct.getProductCode());
            loanProduct.setProductId(loanProductMaster.getProductId());
            loanProduct.getMetaData().setRecordStatus(RecordStatus.A);
            loanProduct.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
            loanProduct.getMetaData().setAuthorizedBy(username);

            if (loanProductTempDAO.deleteLoanProduct(id) && loanProductDAO.updateLoanProduct(loanProduct)) {
                return "Modification Successfully Approved!";
            }
            else {
                return "Approval Unsuccessful!";
            }
        }
        else if(status.equals(RecordStatus.D)) {
            LoanProductDTO loanProductMaster = loanProductDAO.getLoanProductByCode(loanProduct.getProductCode());
            loanProductMaster.getMetaData().setActiveInactiveFlag(Boolean.FALSE);

            if (loanProductDAO.updateLoanProduct(loanProductMaster) && loanProductTempDAO.deleteLoanProduct(id)) {
                return "Deletion Successfully Approved!";
            }
            else {
                return "Approval Unsuccessful!";
            }
        }
        else if(status.equals(RecordStatus.N)) {
            loanProduct.getMetaData().setRecordStatus(RecordStatus.A);
            loanProduct.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
            loanProduct.getMetaData().setAuthorizedBy(username);

            if(loanProductDAO.addLoanProduct(loanProduct) && loanProductTempDAO.deleteLoanProduct(id)) {
                return "Successfully Approved!";
            }
            else {
                return  "Approval Unsuccessful!";
            }
        }
        return null;
    }

    @Override
    public String rejectLoanProduct(Long id) {
        LoanProductDTO loanProduct = loanProductTempDAO.getLoanProductByID(id);
        RecordStatus status = loanProduct.getMetaData().getRecordStatus();

        if(status.equals(RecordStatus.N))
            loanProduct.getMetaData().setRecordStatus(RecordStatus.NR);
        else if(status.equals(RecordStatus.M))
            loanProduct.getMetaData().setRecordStatus(RecordStatus.MR);
        else if(status.equals(RecordStatus.D))
            loanProduct.getMetaData().setRecordStatus(RecordStatus.DR);

        if(loanProductTempDAO.updateLoanProduct(loanProduct)) {
            return "Successfully Rejected!";
        }
        else {
            return "Rejection Unsuccessful!";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllLoanProductCodes() {
        return loanProductDAO.getAllLoanProductCodes();
    }

    @Override
    @Transactional(readOnly = true)
    public LoanProductDTO getLoanProductByCode(String code) {
        return loanProductDAO.getLoanProductByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanProductDTO getLoanProductByID(Long id) {
        return loanProductDAO.getLoanProductByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanProductDTO> getAllActiveLoanProducts() {
        return loanProductDAO.getAllValidLoanProducts();
    }

    @Override
    public List<LoanProductDTO> getAllLoanProductsByProductTypeId(Long productTypeId) {
        return loanProductDAO.getAllLoanProductsByProductTypeId(productTypeId);
    }

}
