package org.nucleus.service;


import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.permanent.ReceivablePayable;

import java.sql.Date;
import java.util.List;

public interface ReceivablePayableService {
    boolean insertReceivablePayable(ReceivablePayableDTO receivablePayable);
    boolean updateReceivablePayable(ReceivablePayableDTO receivablePayable);
    boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayable);
    ReceivablePayableDTO getReceivablePayable(Long receivablePayableId);
    List<ReceivablePayableDTO> getAllReceivablePayable();
    List<ReceivablePayableDTO> getReceivablePayableBYLoanAccId(Long loanAccountId);
    Double calculateUnpaidPenalty(List<ReceivablePayableDTO> receivablePayables);
}
