package org.nucleus.utility.dtomapper;

import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.permanent.RepaymentPolicy;
import org.nucleus.entity.temporary.RepaymentPolicyTemp;

public class RepaymentPolicyDtoMapper {
    public static RepaymentPolicyTemp dtoToTempConvertor(RepaymentPolicyDTO repaymentPolicyDTO)
    {
        RepaymentPolicyTemp repaymentPolicyTemp=new RepaymentPolicyTemp();
        repaymentPolicyTemp.setRepaymentPolicyCode(repaymentPolicyDTO.getRepaymentPolicyCode());
        repaymentPolicyTemp.setRepaymentPolicyName(repaymentPolicyDTO.getRepaymentPolicyName());
        repaymentPolicyTemp.setContractType(repaymentPolicyDTO.getContractType());
        repaymentPolicyTemp.setRepaymentType(repaymentPolicyDTO.getRepaymentType());
        repaymentPolicyTemp.setRepaymentFrequency(repaymentPolicyDTO.getRepaymentFrequency());
        repaymentPolicyTemp.setRecoveryType(repaymentPolicyDTO.getRecoveryType());
        repaymentPolicyTemp.setInstallmentDueDate(repaymentPolicyDTO.getInstallmentDueDate());
        repaymentPolicyTemp.setMetaData(repaymentPolicyDTO.getMetaData());
        System.out.println(repaymentPolicyTemp);
        return repaymentPolicyTemp;
    }
    public static RepaymentPolicy dtoToMasterConvertor(RepaymentPolicyDTO repaymentPolicyDTO)
    {
        RepaymentPolicy repaymentPolicy=new RepaymentPolicy();
        repaymentPolicy.setRepaymentPolicyId(repaymentPolicyDTO.getRepayPolicyId());
        repaymentPolicy.setRepaymentPolicyCode(repaymentPolicyDTO.getRepaymentPolicyCode());
        repaymentPolicy.setRepaymentPolicyName(repaymentPolicyDTO.getRepaymentPolicyName());
        repaymentPolicy.setContractType(repaymentPolicyDTO.getContractType());
        repaymentPolicy.setRepaymentType(repaymentPolicyDTO.getRepaymentType());
        repaymentPolicy.setRepaymentFrequency(repaymentPolicyDTO.getRepaymentFrequency());
        repaymentPolicy.setRecoveryType(repaymentPolicyDTO.getRecoveryType());
        repaymentPolicy.setInstallmentDueDate(repaymentPolicyDTO.getInstallmentDueDate());
        repaymentPolicy.setMetaData(MetaDataMapper.convertToMetaData(repaymentPolicyDTO.getMetaData()));
        return repaymentPolicy;
    }
    public static RepaymentPolicyDTO tempToDTOConvertor(RepaymentPolicyTemp repaymentPolicyTemp) {
        RepaymentPolicyDTO repaymentPolicyDTO = new RepaymentPolicyDTO();
        repaymentPolicyDTO.setRepayPolicyId(repaymentPolicyTemp.getRepaymentPolicyId());
        repaymentPolicyDTO.setRepaymentPolicyCode(repaymentPolicyTemp.getRepaymentPolicyCode());
        repaymentPolicyDTO.setRepaymentPolicyName(repaymentPolicyTemp.getRepaymentPolicyName());
        repaymentPolicyDTO.setContractType(repaymentPolicyTemp.getContractType());
        repaymentPolicyDTO.setRepaymentType(repaymentPolicyTemp.getRepaymentType());
        repaymentPolicyDTO.setRepaymentFrequency(repaymentPolicyTemp.getRepaymentFrequency());
        repaymentPolicyDTO.setRecoveryType(repaymentPolicyTemp.getRecoveryType());
        repaymentPolicyDTO.setInstallmentDueDate(repaymentPolicyTemp.getInstallmentDueDate());
        repaymentPolicyDTO.setMetaData(repaymentPolicyTemp.getMetaData());
        return repaymentPolicyDTO;
    }
    public static RepaymentPolicyDTO masterToDTOConvertor(RepaymentPolicy repaymentPolicy) {
        RepaymentPolicyDTO repaymentPolicyDTO = new RepaymentPolicyDTO();
        repaymentPolicyDTO.setRepayPolicyId(repaymentPolicy.getRepaymentPolicyId());
        repaymentPolicyDTO.setRepaymentPolicyCode(repaymentPolicy.getRepaymentPolicyCode());
        repaymentPolicyDTO.setRepaymentPolicyName(repaymentPolicy.getRepaymentPolicyName());
        repaymentPolicyDTO.setContractType(repaymentPolicy.getContractType());
        repaymentPolicyDTO.setRepaymentType(repaymentPolicy.getRepaymentType());
        repaymentPolicyDTO.setRepaymentFrequency(repaymentPolicy.getRepaymentFrequency());
        repaymentPolicyDTO.setRecoveryType(repaymentPolicy.getRecoveryType());
        repaymentPolicyDTO.setInstallmentDueDate(repaymentPolicy.getInstallmentDueDate());
        repaymentPolicyDTO.setMetaData(MetaDataMapper.convertToTempMetaData(repaymentPolicy.getMetaData()));
        return repaymentPolicyDTO;
    }
}
