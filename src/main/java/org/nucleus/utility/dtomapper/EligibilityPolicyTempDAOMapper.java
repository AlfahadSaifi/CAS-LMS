package org.nucleus.utility.dtomapper;

import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.springframework.stereotype.Component;

@Component
public class EligibilityPolicyTempDAOMapper {
    public EligibilityPolicyTempDTO mapPolicy(EligibilityPolicyTemp eligibilityPoliciesTemp){
        EligibilityPolicyTempDTO eligibilityPolicies=new EligibilityPolicyTempDTO();
        eligibilityPolicies.setId(eligibilityPoliciesTemp.getEligibilityPolicyId());
        eligibilityPolicies.setPolicyCode(eligibilityPoliciesTemp.getEligibilityPolicyCode());
        eligibilityPolicies.setPolicyName(eligibilityPoliciesTemp.getEligibilityPolicyName());
        eligibilityPolicies.setEligibilityCriteria(eligibilityPoliciesTemp.getEligibilityCriteria());
        eligibilityPolicies.setEligibilityParameterMappingList(eligibilityPoliciesTemp.getEligibilityParameterMappingList());
        eligibilityPolicies.setMetaData(eligibilityPoliciesTemp.getMetaData());
        eligibilityPolicies.setFlag(eligibilityPoliciesTemp.getMetaData().getRecordStatus());
        return eligibilityPolicies;
    }
}
