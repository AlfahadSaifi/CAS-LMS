package org.nucleus.utility.dtomapper;

import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.entity.permanent.LoanAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanAccountDTOMapper {


    public LoanAccountDTO mapObjectToDTO(LoanAccount loanAccount){
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        loanAccountDTO.setLoanAccountId(loanAccount.getLoanAccountId());
        loanAccountDTO.setLoanAccountNumber(loanAccount.getLoanAccountNumber());
        loanAccountDTO.setLoanSanctionDate(loanAccount.getLoanSanctionDate());
        loanAccountDTO.setDisbursalDate(loanAccount.getDisbursalDate());
        loanAccountDTO.setCifNumber(loanAccount.getCifNumber());
        loanAccountDTO.setFinalSanctionedAmount(loanAccount.getFinalSanctionedAmount());
        loanAccountDTO.setLoanAmountDisbursed(loanAccount.getLoanAmountDisbursed());
        loanAccountDTO.setLoanProduct(loanAccount.getLoanProduct());
        loanAccountDTO.setProductType(loanAccount.getProductType());
        loanAccountDTO.setLoanAmountPaidByCustomer(loanAccount.getLoanAmountPaidByCustomer());
        loanAccountDTO.setNumberOfInstallment(loanAccount.getNumberOfInstallment());
        loanAccountDTO.setNumberOfInstallmentBilled(loanAccount.getNumberOfInstallmentBilled());
        loanAccountDTO.setNumberOfInstallmentUnbilled(loanAccount.getNumberOfInstallmentUnbilled());
        loanAccountDTO.setOriginalEmiAmount(loanAccount.getOriginalEmiAmount());
        loanAccountDTO.setOriginalTenure(loanAccount.getOriginalTenure());
        loanAccountDTO.setInterestRate(loanAccount.getInterestRate());
        loanAccountDTO.setRepaymentFrequency(loanAccount.getRepaymentFrequency());
        loanAccountDTO.setLoanStatus(loanAccount.getLoanStatus());
        loanAccountDTO.setLoanApplication(loanAccount.getLoanApplication());
        return loanAccountDTO;
    }

    public LoanAccount mapDTOToObject(LoanAccountDTO loanAccountDTO){
        LoanAccount loanAccount = new LoanAccount();
        loanAccount.setLoanAccountId(loanAccountDTO.getLoanAccountId());
        loanAccount.setLoanAccountNumber(loanAccountDTO.getLoanAccountNumber());
        loanAccount.setLoanSanctionDate(loanAccountDTO.getLoanSanctionDate());
        loanAccount.setDisbursalDate(loanAccountDTO.getDisbursalDate());
        loanAccount.setCifNumber(loanAccountDTO.getCifNumber());
        loanAccount.setFinalSanctionedAmount(loanAccountDTO.getFinalSanctionedAmount());
        loanAccount.setLoanAmountDisbursed(loanAccountDTO.getLoanAmountDisbursed());
        loanAccount.setLoanProduct(loanAccountDTO.getLoanProduct());
        loanAccount.setProductType(loanAccountDTO.getProductType());
        loanAccount.setLoanAmountPaidByCustomer(loanAccountDTO.getLoanAmountPaidByCustomer());
        loanAccount.setNumberOfInstallment(loanAccountDTO.getNumberOfInstallment());
        loanAccount.setNumberOfInstallmentBilled(loanAccountDTO.getNumberOfInstallmentBilled());
        loanAccount.setNumberOfInstallmentUnbilled(loanAccountDTO.getNumberOfInstallmentUnbilled());
        loanAccount.setOriginalEmiAmount(loanAccountDTO.getOriginalEmiAmount());
        loanAccount.setOriginalTenure(loanAccountDTO.getOriginalTenure());
        loanAccount.setInterestRate(loanAccountDTO.getInterestRate());
        loanAccount.setRepaymentFrequency(loanAccountDTO.getRepaymentFrequency());
        loanAccount.setLoanStatus(loanAccountDTO.getLoanStatus());
        loanAccount.setLoanApplication(loanAccountDTO.getLoanApplication());
        return loanAccount;
    }

}
