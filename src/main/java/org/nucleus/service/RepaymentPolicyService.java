package org.nucleus.service;

import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.permanent.RepaymentPolicy;

import java.util.List;

public interface RepaymentPolicyService {
    boolean insertRepaymentPolicy(RepaymentPolicyDTO repaymentPolicy);

    boolean updateRepaymentPolicy(RepaymentPolicyDTO repaymentPolicy);

    boolean deleteRepaymentPolicy(String code);

    RepaymentPolicyDTO getRepaymentPolicyById(String id);

    List<RepaymentPolicyDTO> getAllRepaymentPolicy();

    RepaymentPolicy getRepayPolicyByCode(String code);
}
