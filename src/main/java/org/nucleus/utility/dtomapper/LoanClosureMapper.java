package org.nucleus.utility.dtomapper;

import org.nucleus.entity.permanent.LoanClosure;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.entity.temporary.LoanClosureTemp;

public class LoanClosureMapper {

    public static LoanClosureTemp toTemporaryEntity(LoanClosureDTO dto) {
        if(dto==null)return null;
        LoanClosureTemp entity = new LoanClosureTemp();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();
        entity.setLoanClosureId(dto.getLoanClosureId());
        entity.setLoanAccount(loanAccountDTOMapper.mapDTOToObject(dto.getLoanAccountDTO()));
        entity.setLoanClosureDate(dto.getLoanClosureDate());
        entity.setClosureStatus(dto.getClosureStatus());
        entity.setMetaData(MetaDataMapper.convertToTempMetaData(dto.getMetaData()));
        return entity;
    }

    public static LoanClosure toPermanentEntity(LoanClosureDTO dto) {
        if(dto==null)return null;
        LoanClosure entity = new LoanClosure();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();
        entity.setLoanClosureId(dto.getLoanClosureId());
        entity.setLoanAccount(loanAccountDTOMapper.mapDTOToObject(dto.getLoanAccountDTO()));
        entity.setLoanClosureDate(dto.getLoanClosureDate());
        entity.setClosureStatus(dto.getClosureStatus());
        entity.setMetaData(dto.getMetaData());
        return entity;
    }

    public static LoanClosureDTO toTemporaryDTO(LoanClosureTemp entity) {
        if(entity==null)return null;
        LoanClosureDTO dto = new LoanClosureDTO();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();
        dto.setLoanClosureId(entity.getLoanClosureId());
        dto.setLoanAccountDTO(loanAccountDTOMapper.mapObjectToDTO(entity.getLoanAccount()));
        dto.setLoanClosureDate(entity.getLoanClosureDate());
        dto.setClosureStatus(entity.getClosureStatus());
        dto.setMetaData(MetaDataMapper.convertToMetaData(entity.getMetaData()));
        return dto;
    }

    public static LoanClosureDTO toPermanentDTO(LoanClosure entity) {
        if(entity==null)return null;
        LoanClosureDTO dto = new LoanClosureDTO();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();
        dto.setLoanClosureId(entity.getLoanClosureId());
        dto.setLoanAccountDTO(loanAccountDTOMapper.mapObjectToDTO(entity.getLoanAccount()));
        dto.setLoanClosureDate(entity.getLoanClosureDate());
        dto.setClosureStatus(entity.getClosureStatus());
        dto.setMetaData(entity.getMetaData());
        return dto;
    }

}

