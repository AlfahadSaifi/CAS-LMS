package org.nucleus.dao;
import org.nucleus.dto.LoanClosureDTO;
import java.util.List;
public interface LoanClosureDAO {
    boolean save(LoanClosureDTO loanClosureDTO);
    boolean update(LoanClosureDTO loanClosureDTO);
    boolean delete (Long loanClosureId);
    boolean delete (LoanClosureDTO loanClosureDTO);
    List<LoanClosureDTO> getAllLoanClosureDataPermanentTable();
    LoanClosureDTO getLoanClosureDetailPer(Long closureLoanId);
    boolean addData(List<LoanClosureDTO> loanClosureDTOList, int batchSize);
    List<LoanClosureDTO> getAllRegularClosureData();
}