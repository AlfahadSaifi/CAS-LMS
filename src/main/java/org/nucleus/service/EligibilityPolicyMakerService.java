package org.nucleus.service;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;

import java.util.List;

public interface EligibilityPolicyMakerService {
    boolean insertEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPoliciesDTO);
    boolean deleteEligibilityPolicy(Long policyId,String flag);
    boolean deleteEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicyTempDTO);
    boolean deleteEligibilityPolicyById(Long policyId);
    boolean updateEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicies);
    EligibilityPolicyTempDTO getEligibilityPolicy(Long policyId);
    List<EligibilityPolicyTemp> viewAllEligibilityPolicy();
}
