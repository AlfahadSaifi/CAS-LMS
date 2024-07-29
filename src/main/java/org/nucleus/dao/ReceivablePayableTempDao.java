package org.nucleus.dao;


import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;

import java.sql.Date;
import java.util.List;

public interface ReceivablePayableTempDao {
    boolean insertReceivablePayable(ReceivablePayableDTO receivablePayable);
    boolean updateReceivablePayable(ReceivablePayableDTO receivablePayable);
    boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayable);
    ReceivablePayableDTO getReceivablePayable(Long receivablePayableId);
    List<ReceivablePayableDTO> getAllReceivablePayable();
    List<ReceivablePayableDTO> getReceivablePayableAgainstLoanIdAndDateRange(LoanAccountDTO loanAccount, Date startDate, Date endDate);
}
