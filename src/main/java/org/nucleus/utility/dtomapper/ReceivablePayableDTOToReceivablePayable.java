package org.nucleus.utility.dtomapper;

import org.nucleus.entity.permanent.ReceivablePayable;
import org.nucleus.dto.ReceivablePayableDTO;

public class ReceivablePayableDTOToReceivablePayable {

    public ReceivablePayable mapObjectToReceivablePayable(ReceivablePayableDTO receivablePayableDTO){
        ReceivablePayable receivablePayable = new ReceivablePayable();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();


        receivablePayable.setReceivablePayableId(receivablePayableDTO.getReceivablePayableId());
        receivablePayable.setCurrency(receivablePayableDTO.getCurrency());
        receivablePayable.setReceivablePayableType(receivablePayableDTO.getReceivablePayableType());
        receivablePayable.setReceivablePayableDueDate(receivablePayableDTO.getReceivablePayableDueDate());
        receivablePayable.setLoanAccount(loanAccountDTOMapper.mapDTOToObject(receivablePayableDTO.getLoanAccount()));
        receivablePayable.setReceivablePayableAmount(receivablePayableDTO.getReceivablePayableAmount());
        receivablePayable.setPrincipalComponent(receivablePayableDTO.getPrincipalComponent());
        receivablePayable.setPrincipalComponentReceived(receivablePayableDTO.getPrincipalComponentReceived());
        receivablePayable.setInterestComponent(receivablePayableDTO.getInterestComponent());
        receivablePayable.setInterestComponentReceived(receivablePayableDTO.getInterestComponentReceived());
        receivablePayable.setReceivablePayableDate(receivablePayableDTO.getReceivablePayableDate());
        receivablePayable.setReceivablePayableStatus(receivablePayableDTO.getReceivablePayableStatus());
        receivablePayable.setPenalty(receivablePayableDTO.getPenalty());
        //receivablePayable.setRecordStatus(receivablePayableDTO.getRecordStatus());
        return receivablePayable;
    }
}
