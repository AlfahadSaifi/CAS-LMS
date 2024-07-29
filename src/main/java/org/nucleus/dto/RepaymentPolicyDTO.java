package org.nucleus.dto;

import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.ContractType;
import org.nucleus.utility.enums.RecoveryType;
import org.nucleus.utility.enums.RepaymentFrequency;
import org.nucleus.utility.enums.RepaymentType;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
public class RepaymentPolicyDTO {
    private Long repayPolicyId;
 @Pattern(regexp = "^[a-zA-Z1-9 ]+$", message = "Only alphabets, numbers  and spaces are allowed")
    private String repaymentPolicyCode;
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets and spaces are allowed")
    private String repaymentPolicyName;
    private ContractType contractType;
    private RepaymentType repaymentType;
    private RepaymentFrequency repaymentFrequency;
    private RecoveryType recoveryType;
    private String installmentDueDate;
    private TempMetaData metaData;

    public Long getRepayPolicyId() {
        return repayPolicyId;
    }

    public void setRepayPolicyId(Long repayPolicyId) {
        this.repayPolicyId = repayPolicyId;
    }

    public TempMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TempMetaData metaData) {
        this.metaData = metaData;
    }

    public String getRepaymentPolicyCode() {
        return repaymentPolicyCode;
    }

    public void setRepaymentPolicyCode(String repaymentPolicyCode) {
        this.repaymentPolicyCode = repaymentPolicyCode;
    }

    public String getRepaymentPolicyName() {
        return repaymentPolicyName;
    }

    public void setRepaymentPolicyName(String repaymentPolicyName) {
        this.repaymentPolicyName = repaymentPolicyName;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public RepaymentType getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(RepaymentType repaymentType) {
        this.repaymentType = repaymentType;
    }

    public RepaymentFrequency getRepaymentFrequency() {
        return repaymentFrequency;
    }

    public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
        this.repaymentFrequency = repaymentFrequency;
    }

    public RecoveryType getRecoveryType() {
        return recoveryType;
    }

    public void setRecoveryType(RecoveryType recoveryType) {
        this.recoveryType = recoveryType;
    }

    public String getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(String installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }

    @Override
    public String toString() {
        return "RepaymentPolicyDTO{" +
                "repaymentPolicyCode='" + repaymentPolicyCode + '\'' +
                ", repaymentPolicyName='" + repaymentPolicyName + '\'' +
                ", contractType=" + contractType +
                ", repaymentType=" + repaymentType +
                ", repaymentFrequency=" + repaymentFrequency +
                ", recoveryType=" + recoveryType +
                ", installmentDueDate='" + installmentDueDate + '\'' +
                ", metaData=" + metaData +
                '}';
    }
    public RepaymentPolicyDTO()
    {
        metaData=new TempMetaData();
    }
}
