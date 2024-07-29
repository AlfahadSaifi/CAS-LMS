package org.nucleus.service;


import org.nucleus.dto.CheckerEarlyClosureDTO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.dto.MakerEarlyClosureDTO;

public interface EarlyClosureService
{

    // Create in temporary table
    Long createLoanClosureTemp(LoanClosureDTO loanClosureDTO);

    // Create in permanent table
    boolean createLoanClosure(LoanClosureDTO loanClosureDTO);

    // Read from temporary table
    LoanClosureDTO getLoanClosureTempById(Long id);

    // Delete from permanent table
    boolean deleteLoanClosure(Long id);

    MakerEarlyClosureDTO getAllMakerFields(String loanAccountNumber);

    LoanAccountDTO getLoanAccount(String loanAccountNumber);

    LoanClosureDTO getLoanClosureByAccountId(Long loanAccountId);

    LoanClosureDTO getLoanClosureByAccountNumber(String loanAccountNumber);

    CheckerEarlyClosureDTO getAllCheckerFields(String loanAccountNumber);

    boolean updateLoanClosureTemp(LoanClosureDTO loanClosureDTO);

    Long findLoanClosureByLoanAccountId(Long loanClosureId);

    boolean updateLoanAccount(LoanAccountDTO loanAccountDTO);

    boolean deleteLoanClosureByDTO(LoanClosureDTO loanClosureDTO);
}
