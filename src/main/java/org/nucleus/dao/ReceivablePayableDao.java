package org.nucleus.dao;


import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.permanent.ReceivablePayable;

import java.util.List;

public interface ReceivablePayableDao {
    boolean insertReceivablePayable(ReceivablePayableDTO receivablePayable);
    boolean updateReceivablePayable(ReceivablePayableDTO receivablePayable);
    boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayable);
    ReceivablePayableDTO getReceivablePayable(Long receivablePayableId);
    List<ReceivablePayableDTO> getAllReceivablePayable();

    List<ReceivablePayableDTO> getReceivablePayableBYLoanAccId(Long loanAccountId);
}
