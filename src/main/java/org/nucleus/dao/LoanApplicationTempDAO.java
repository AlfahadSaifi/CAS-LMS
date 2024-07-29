package org.nucleus.dao;


import org.nucleus.dto.LoanApplicationDTO;

import java.util.List;

public interface LoanApplicationTempDAO {
    Long create(LoanApplicationDTO loanApplicationDTO);

    LoanApplicationDTO read(Long loanId);

    boolean update(LoanApplicationDTO loanApplicationDTO);

    boolean merge(LoanApplicationDTO loanApplicationDTO);

    boolean delete(String loanApplicationId);

    List<LoanApplicationDTO> readingAll();

    Long getNextSequenceNumber();

    LoanApplicationDTO readByLoanApplicationId(String loanApplicationId);
}
