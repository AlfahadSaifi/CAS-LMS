package org.nucleus.service;

import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.permanent.EligibilityPolicy;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;

import java.util.List;

public interface EligibilityPolicyCheckerService {
    boolean approveEligibilityPolicy(EligibilityPolicyTemp eligibilityPoliciesTemp);
    boolean rejectEligibilityPolicy(EligibilityPolicyTemp eligibilityPoliciesTemp);
    boolean updateEligibilityPolicy(EligibilityPolicyTemp eligibilityPoliciesTemp);
    EligibilityPolicyTempDTO getEligibilityPolicy(Long policyId);
    EligibilityPolicyTempDTO getEligibilityPolicyByCode(String policyCode);

    List<EligibilityPolicy> viewAllEligibilityPolicyFiltered();
    List<EligibilityPolicy> viewAllEligibilityPolicy();

}
