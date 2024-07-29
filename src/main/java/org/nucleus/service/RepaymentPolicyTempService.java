package org.nucleus.service;

import org.nucleus.dto.RepaymentPolicyDTO;

import java.util.List;

public interface RepaymentPolicyTempService {
    boolean insertRepaymentPolicy(RepaymentPolicyDTO repaymentPolicy);
    boolean updateRepaymentPolicy(RepaymentPolicyDTO repaymentPolicy);
    boolean deleteRepaymentPolicy(String code);
    RepaymentPolicyDTO getRepaymentPolicyById(String id);
    List<RepaymentPolicyDTO> getAllRepaymentPolicy();
    RepaymentPolicyDTO checkSave();
    List<RepaymentPolicyDTO> getAllRepaymentPolicyRejeted();

}
