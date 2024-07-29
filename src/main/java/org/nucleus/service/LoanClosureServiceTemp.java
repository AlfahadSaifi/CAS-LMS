package org.nucleus.service;
import javassist.NotFoundException;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import java.util.List;
public interface LoanClosureServiceTemp {
    boolean performLoanClosure();
    List<ReceivablePayableDTO> getLoansNotClosed();
    List<LoanClosureDTO> getAllRegularClosureData();
    List<LoanClosureDTO> getAllLoanClosureDataPermanentTable();
    LoanClosureDTO getLoanClosureDetailPermanent(Long closureLoanId);

    boolean save(LoanClosureDTO loanClosureDTO);
    boolean update(LoanClosureDTO loanClosureDTO);
    boolean delete (Long loanClosureId);
    boolean delete (LoanClosureDTO loanClosureDTO);
}