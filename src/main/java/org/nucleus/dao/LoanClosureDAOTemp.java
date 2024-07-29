package org.nucleus.dao;



import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;

import java.util.List;

public interface LoanClosureDAOTemp {
    boolean addData(LoanClosureDTO loanClosureDTO);

    boolean update(LoanClosureDTO loanClosureDTO);

    boolean delete(LoanClosureDTO loanClosureDTO);

    boolean delete(Long  loanClosureId);

    List<LoanClosureDTO> getAllLoanClosureData();

    LoanClosureDTO getLoanClosureDetail(Long loanClosureId);

    List<LoanClosureDTO> getCheckerTempData();

    List<LoanClosureDTO> getCheckerTempDataDeleted();
    LoanClosureDTO findLoanClosureByLoanAccountId(Long loanAccountId);

    Long addEarlyClosureData(LoanClosureDTO loanClosureDTO);

}
