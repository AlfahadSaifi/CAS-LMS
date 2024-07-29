package org.nucleus.utility.dtomapper;


import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.permanent.ReceivablePayable;
import org.nucleus.entity.temporary.ReceivablePayableTemp;

public class ReceivablePayableDtoMapper {
    public ReceivablePayable mapObjectToReceivablePayable(ReceivablePayableDTO receivablePayableDTO){
        ReceivablePayable receivablePayable = new ReceivablePayable();
        MetaDataMapper metaDataMapper = new MetaDataMapper();
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
        receivablePayable.setMetaData(metaDataMapper.tempToMetaData(receivablePayableDTO.getTempMetaData()));
        return receivablePayable;
    }
    public ReceivablePayableTemp mapObjectToReceivablePayableTemp(ReceivablePayableDTO receivablePayableDTO){
        ReceivablePayableTemp receivablePayableTemp = new ReceivablePayableTemp();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();
        receivablePayableTemp.setReceivablePayableId(receivablePayableDTO.getReceivablePayableId());
        receivablePayableTemp.setCurrency(receivablePayableDTO.getCurrency());
        receivablePayableTemp.setReceivablePayableType(receivablePayableDTO.getReceivablePayableType());
        receivablePayableTemp.setReceivablePayableDueDate(receivablePayableDTO.getReceivablePayableDueDate());

//        if(receivablePayableDTO.getLoanAccount() == null)
//            receivablePayableTemp.setLoanAccount(null);
//        else
            receivablePayableTemp.setLoanAccount(loanAccountDTOMapper.mapDTOToObject(receivablePayableDTO.getLoanAccount()));
        receivablePayableTemp.setReceivablePayableAmount(receivablePayableDTO.getReceivablePayableAmount());
        receivablePayableTemp.setPrincipalComponent(receivablePayableDTO.getPrincipalComponent());
        receivablePayableTemp.setPrincipalComponentReceived(receivablePayableDTO.getPrincipalComponentReceived());
        receivablePayableTemp.setInterestComponent(receivablePayableDTO.getInterestComponent());
        receivablePayableTemp.setInterestComponentReceived(receivablePayableDTO.getInterestComponentReceived());
        receivablePayableTemp.setReceivablePayableDate(receivablePayableDTO.getReceivablePayableDate());
        receivablePayableTemp.setReceivablePayableStatus(receivablePayableDTO.getReceivablePayableStatus());
        receivablePayableTemp.setPenalty(receivablePayableDTO.getPenalty());
        receivablePayableTemp.setMetaData(receivablePayableDTO.getTempMetaData());
        return receivablePayableTemp;
    }
    public ReceivablePayableDTO mapTempObjectToreceivablePayableDTO(ReceivablePayableTemp receivablePayableTemp){
        ReceivablePayableDTO receivablePayableDTO = new ReceivablePayableDTO();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();
        receivablePayableDTO.setReceivablePayableId(receivablePayableTemp.getReceivablePayableId());
        receivablePayableDTO.setCurrency(receivablePayableTemp.getCurrency());
        receivablePayableDTO.setReceivablePayableType(receivablePayableTemp.getReceivablePayableType());
        receivablePayableDTO.setReceivablePayableDueDate(receivablePayableTemp.getReceivablePayableDueDate());
        receivablePayableDTO.setLoanAccount(loanAccountDTOMapper.mapObjectToDTO(receivablePayableTemp.getLoanAccount()));
        receivablePayableDTO.setReceivablePayableAmount(receivablePayableTemp.getReceivablePayableAmount());
        receivablePayableDTO.setPrincipalComponent(receivablePayableTemp.getPrincipalComponent());
        receivablePayableDTO.setPrincipalComponentReceived(receivablePayableTemp.getPrincipalComponentReceived());
        receivablePayableDTO.setInterestComponent(receivablePayableTemp.getInterestComponent());
        receivablePayableDTO.setInterestComponentReceived(receivablePayableTemp.getInterestComponentReceived());
        receivablePayableDTO.setReceivablePayableDate(receivablePayableTemp.getReceivablePayableDate());
        receivablePayableDTO.setReceivablePayableStatus(receivablePayableTemp.getReceivablePayableStatus());
        receivablePayableDTO.setPenalty(receivablePayableTemp.getPenalty());
        receivablePayableDTO.setTempMetaData(receivablePayableTemp.getMetaData());
        return receivablePayableDTO;
    }
    public ReceivablePayableDTO mapObjectToreceivablePayableDTO(ReceivablePayable receivablePayable){
        ReceivablePayableDTO receivablePayableDTO = new ReceivablePayableDTO();
        ReceivablePayableTemp receivablePayableTemp = new ReceivablePayableTemp();
        MetaDataMapper metaDataMapper = new MetaDataMapper();
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
        receivablePayableDTO.setTempMetaData(metaDataMapper.metaDataToTemp(receivablePayable.getMetaData()));
        return receivablePayableDTO;
    }
}