package org.nucleus.dao;

import org.nucleus.dto.EmiParameterDTO;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.entity.temporary.LoanApplicationTemp;

import java.util.List;

public interface LoanApplicationDAO {
    LoanApplication getApplicationByAccountNumber(String loanAccountNumber);
    Long create(LoanApplicationDTO loanApplicationDTO);
    List<LoanApplicationDTO> readingAll();
    LoanApplicationDTO read(Long loanId);
    boolean merge(LoanApplicationDTO loanApplicationDTO);
    boolean merge(LoanApplication loanApplication);
    boolean update(LoanApplicationDTO loanApplicationDTO);
    boolean delete(String longApplicationID);
    LoanApplicationDTO readByLoanApplicationId(String loanApplicationId);
    LoanApplicationDTO readByLoanAccountNumber(String loanAccountNumber);

    LoanApplication getLoanIdAgainstApplication(String loanApplicationId);
    LoanApplicationTemp getLoanApplicationAgainstLoanId(Long loanId);

}

