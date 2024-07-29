package org.nucleus.utility.dtomapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.service.EligibilityPolicyMakerServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class EligibilityPolicyTempDTOMapper {
    private static final Logger log = LogManager.getLogger(EligibilityPolicyTempDTOMapper.class);
    public EligibilityPolicyTemp mapPolicy(EligibilityPolicyTempDTO eligibilityPoliciesDTO) {
        EligibilityPolicyTemp eligibilityPolicies = new EligibilityPolicyTemp();
        eligibilityPolicies.setEligibilityPolicyId(eligibilityPoliciesDTO.getId());
        eligibilityPolicies.setEligibilityPolicyCode(eligibilityPoliciesDTO.getPolicyCode());
        eligibilityPolicies.setEligibilityPolicyName(eligibilityPoliciesDTO.getPolicyName());
        eligibilityPolicies.setEligibilityCriteria(eligibilityPoliciesDTO.getEligibilityCriteria());
        eligibilityPolicies.setEligibilityParameterMappingList(eligibilityPoliciesDTO.getEligibilityParameterMappingList());
        if (eligibilityPoliciesDTO.getMetaData()==null){
            eligibilityPoliciesDTO.setMetaData(new TempMetaData());
        }
        eligibilityPolicies.setMetaData(eligibilityPoliciesDTO.getMetaData());
        eligibilityPolicies.getMetaData().setRecordStatus(eligibilityPoliciesDTO.getFlag());
        return eligibilityPolicies;
    }
}
