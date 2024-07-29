package org.nucleus.service;


import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;

import java.sql.Date;
import java.util.List;

public interface ReceivablePayableTempService {
    boolean insertReceivablePayable(ReceivablePayableDTO receivablePayableDTO);
    boolean updateReceivablePayable(ReceivablePayableDTO receivablePayableDTO);
    boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayableDTO);
    ReceivablePayableDTO getReceivablePayable(Long receivablePayableId);
    List<ReceivablePayableDTO> getAllReceivablePayable();
    List<ReceivablePayableDTO> getReceivablePayableAgainstLoanIdAndDateRange(LoanAccountDTO loanAccount, Date startDate, Date endDate);
}
