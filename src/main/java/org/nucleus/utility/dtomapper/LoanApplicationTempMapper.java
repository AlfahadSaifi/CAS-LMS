package org.nucleus.utility.dtomapper;


import org.nucleus.entity.temporary.LoanApplicationTemp;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.dto.LoanApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Developer : Monu Kumar*/
@Component
public class LoanApplicationTempMapper {

    private final LoanProductDTOMapper loanProductDTOMapper;

    @Autowired
    public LoanApplicationTempMapper(LoanProductDTOMapper loanProductDTOMapper){
        this.loanProductDTOMapper=loanProductDTOMapper;
    }


    public LoanApplicationDTO toDTO(LoanApplicationTemp loanApplication){
        if(loanApplication ==null)return null;
        LoanApplicationDTO loanApplicationDTO=new LoanApplicationDTO();

        loanApplicationDTO.setLoanId(loanApplication.getLoanId());
        loanApplicationDTO.setLoanApplicationId(loanApplication.getLoanApplicationId());
        loanApplicationDTO.setApplicationCreationDate(loanApplication.getApplicationCreationDate());
        loanApplicationDTO.setLoanAmount(loanApplication.getLoanAmount());
        loanApplicationDTO.setTenure(loanApplication.getTenure());
        loanApplicationDTO.setLoanAccountNumber(loanApplication.getLoanAccountNumber());
        loanApplicationDTO.setLoanStatus(loanApplication.getLoanStatus());
        loanApplicationDTO.setBranch(loanApplication.getBranch());
        loanApplicationDTO.setTenureIn(loanApplication.getTenureIn());
        loanApplicationDTO.setCustomerDTO(CustomerMapper.toDTO(loanApplication.getCustomer()));

        loanApplicationDTO.setRecordStatus(loanApplication.getTempMetaData().getRecordStatus());
        loanApplicationDTO.setCreationDate(loanApplication.getTempMetaData().getCreationDate());
        loanApplicationDTO.setCreatedBy(loanApplication.getTempMetaData().getCreatedBy());
        loanApplicationDTO.setModifiedBy(loanApplication.getTempMetaData().getModifiedBy());
        loanApplicationDTO.setModifiedDate(loanApplication.getTempMetaData().getModifiedDate());
        loanApplicationDTO.setAuthorizedDate(loanApplication.getTempMetaData().getAuthorizedDate());
        loanApplicationDTO.setAuthorizedBy(loanApplication.getTempMetaData().getAuthorizedBy());

        loanApplicationDTO.setLoanProductDTO(loanProductDTOMapper.loanProductToDto(loanApplication.getLoanProduct()));

        return loanApplicationDTO;
    }

    public LoanApplicationTemp toEntity(LoanApplicationDTO loanApplicationDTO){
        if(loanApplicationDTO==null)return null;
        LoanApplicationTemp loanApplication =new LoanApplicationTemp();

        loanApplication.setLoanId(loanApplicationDTO.getLoanId());
        loanApplication.setLoanApplicationId(loanApplicationDTO.getLoanApplicationId());
        loanApplication.setApplicationCreationDate(loanApplicationDTO.getApplicationCreationDate());
        loanApplication.setLoanAmount(loanApplicationDTO.getLoanAmount());
        loanApplication.setTenure(loanApplicationDTO.getTenure());
        loanApplication.setLoanAccountNumber(loanApplicationDTO.getLoanAccountNumber());
        loanApplication.setLoanStatus(loanApplicationDTO.getLoanStatus());
        loanApplication.setBranch(loanApplicationDTO.getBranch());
        loanApplication.setLoanStatus(loanApplicationDTO.getLoanStatus());
        loanApplication.setTenureIn(loanApplicationDTO.getTenureIn());
        loanApplication.setCustomer(CustomerMapper.toEntity(loanApplicationDTO.getCustomerDTO()));


        TempMetaData tempMetaData = new TempMetaData();
        tempMetaData.setCreationDate(loanApplicationDTO.getCreationDate());
        tempMetaData.setAuthorizedBy(loanApplicationDTO.getAuthorizedBy());
        tempMetaData.setActiveInactiveFlag(loanApplicationDTO.getActiveInactiveFlag());
        tempMetaData.setAuthorizedDate(loanApplicationDTO.getAuthorizedDate());
        tempMetaData.setModifiedBy(loanApplicationDTO.getModifiedBy());
        tempMetaData.setModifiedDate(loanApplicationDTO.getModifiedDate());
        tempMetaData.setRecordStatus(loanApplicationDTO.getRecordStatus());
        tempMetaData.setCreatedBy(loanApplicationDTO.getCreatedBy());

        loanApplication.setTempMetaData(tempMetaData);


        loanApplication.setLoanProduct(loanProductDTOMapper.dtoToLoanProduct(loanApplicationDTO.getLoanProductDTO()));

        return loanApplication;
    }
}
