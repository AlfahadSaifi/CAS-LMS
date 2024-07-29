package org.nucleus.service;

import org.nucleus.dto.CustomerDTO;
import org.nucleus.dto.EmiParameterDTO;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.entity.temporary.LoanApplicationTemp;

import java.util.List;

public interface LoanApplicationService {
    LoanApplication getApplicationByAccountNumber(String loanAccountNumber);

    // Permanent Methods
    Long insertPermanent(LoanApplicationDTO loanApplicationDTO);

    LoanApplicationDTO readPermanent(Long loanId);

    LoanApplicationDTO readPermanentByLoanApplicationId(String loanApplicationId);

    LoanApplicationDTO readPermanentByLoanAccountNumber(String loanAccountNumber);

    boolean deleteRequestPermanent(LoanApplicationDTO loanApplicationDTO);

    List<LoanApplicationDTO> readingAllPermanent();

    String generateLoanApplicationId();

    // Temporary Methods

    Long createTemporary(LoanApplicationDTO loanApplicationDTO);

    LoanApplicationDTO readTemporary(Long loanId);

    LoanApplicationDTO readTemporaryByLoanApplicationId(String loanApplicationId);

    boolean updateTemporary(LoanApplicationDTO loanApplicationDTO);

    boolean deleteTemporary(String loanApplicationId);

    List<LoanApplicationDTO> readingAllTemporary();

    List<LoanApplicationDTO> readAll();

    boolean delete(String loanApplicationId);

    boolean approveLoanApplication(String loanApplicationId);

    boolean rejectLoanApplication(String loanApplicationId);

    List<LoanApplicationDTO> getAllNotRejected();

    LoanApplicationDTO readAny(String loanApplicationId);

    String generateLoanAccountNumber();

    LoanApplication getLoanIdAgainstApplication(String loanApplicationId);
    LoanApplicationTemp getLoanApplicationAgainstLoanId(Long loanId);
    boolean updatePermanent(LoanApplication loanApplication);

}
