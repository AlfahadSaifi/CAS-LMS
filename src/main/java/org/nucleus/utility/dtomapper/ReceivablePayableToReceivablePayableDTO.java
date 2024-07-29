package org.nucleus.utility.dtomapper;

import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.entity.permanent.ReceivablePayable;
import org.nucleus.dto.ReceivablePayableDTO;

public class ReceivablePayableToReceivablePayableDTO {

    public ReceivablePayableDTO mapObjectToreceivablePayableDTO(ReceivablePayable receivablePayable){


        ReceivablePayableDTO receivablePayableDTO = new ReceivablePayableDTO();

        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();

        receivablePayableDTO.setReceivablePayableId(receivablePayable.getReceivablePayableId());
        receivablePayableDTO.setCurrency(receivablePayable.getCurrency());
        receivablePayableDTO.setReceivablePayableType(receivablePayable.getReceivablePayableType());
        receivablePayableDTO.setReceivablePayableDueDate(receivablePayable.getReceivablePayableDueDate());
        receivablePayableDTO.setLoanAccount(loanAccountDTOMapper.mapObjectToDTO(receivablePayable.getLoanAccount()));
        receivablePayableDTO.setReceivablePayableAmount(receivablePayable.getReceivablePayableAmount());
        receivablePayableDTO.setPrincipalComponent(receivablePayable.getPrincipalComponent());
        receivablePayableDTO.setPrincipalComponentReceived(receivablePayable.getPrincipalComponentReceived());
        receivablePayableDTO.setInterestComponent(receivablePayable.getInterestComponent());
        receivablePayableDTO.setInterestComponentReceived(receivablePayable.getInterestComponentReceived());
        receivablePayableDTO.setReceivablePayableDate(receivablePayable.getReceivablePayableDate());
        receivablePayableDTO.setReceivablePayableStatus(receivablePayable.getReceivablePayableStatus());
        receivablePayableDTO.setPenalty(receivablePayable.getPenalty());
        receivablePayableDTO.setLoanAccount(loanAccountDTOMapper.mapObjectToDTO(receivablePayable.getLoanAccount()));
        //receivablePayableDTO.setRecordStatus(receivablePayable.getRecordStatus());
        return receivablePayableDTO;
    }
}
