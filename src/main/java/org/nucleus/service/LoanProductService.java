package org.nucleus.service;
import org.springframework.transaction.annotation.Transactional;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.utility.enums.RecordStatus;

import java.util.List;

public interface LoanProductService {

    List<LoanProductDTO> getAllLoanProductsForMaker();

    LoanProductDTO getLoanProductWithSaveFlag();

    String saveLoanProduct(LoanProductDTO loanProduct);

    LoanProductDTO getLoanProductByIDAndStatus(Long id, RecordStatus status);

    String updateLoanProduct(LoanProductDTO loanProduct);

    String deleteLoanProduct(Long id, RecordStatus status);

    List<LoanProductDTO> getAllLoanProductForChecker();

    String approveLoanProduct(Long id);

    String rejectLoanProduct(Long id);

    @Transactional(readOnly = true)
    List<String> getAllLoanProductCodes();

    @Transactional(readOnly = true)
    LoanProductDTO getLoanProductByCode(String code);

    @Transactional(readOnly = true)
    LoanProductDTO getLoanProductByID(Long id);

    @Transactional(readOnly = true)
    List<LoanProductDTO> getAllActiveLoanProducts();
    List<LoanProductDTO> getAllLoanProductsByProductTypeId(Long productTypeId);

}
