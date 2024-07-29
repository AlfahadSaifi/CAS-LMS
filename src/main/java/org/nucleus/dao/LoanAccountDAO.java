package org.nucleus.dao;

import org.nucleus.dto.LoanAccountDTO;

import java.util.List;

public interface LoanAccountDAO {
    boolean insertLoanAccount(LoanAccountDTO loanAccountDTO);
    LoanAccountDTO getByLoanAccountId(Long loanAccountId);
    LoanAccountDTO getByAccountNumber(String accountNumber);
    boolean updateLoanAccount(LoanAccountDTO loanAccount);
    boolean delete(String accountNumber);
    List<LoanAccountDTO> getAllLoanAccounts();
    boolean updateAccountStatus(List<LoanAccountDTO> loanAccountDTOList);

    Long getRowCount();
    List<LoanAccountDTO> getLoanAccountsInBatch(int offset, int batchSize);
    boolean updateBatch(List<LoanAccountDTO> chunk, int batchSize);
}
