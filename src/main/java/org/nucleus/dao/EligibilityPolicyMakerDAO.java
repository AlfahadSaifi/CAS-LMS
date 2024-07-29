package org.nucleus.dao;

import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface EligibilityPolicyMakerDAO {
    boolean insertEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicies);
    boolean deleteEligibilityPolicy(Long policyId);
    boolean deleteEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicyTempDTO);
    boolean deleteEligibilityPolicyById(Long policyId);

    boolean updateEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicies);
    EligibilityPolicyTempDTO getEligibilityPolicyDTO(Long policyId);
    EligibilityPolicyTemp getEligibilityPolicy(Long policyId);
    List<EligibilityPolicyTemp> viewAllEligibilityPolicy();
}
