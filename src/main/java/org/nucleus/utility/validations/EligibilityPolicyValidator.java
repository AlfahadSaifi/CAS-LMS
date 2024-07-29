package org.nucleus.utility.validations;

import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.service.EligibilityPolicyMakerService;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class EligibilityPolicyValidator implements Validator {

    @Autowired
    private EligibilityPolicyMakerService eligibilityPolicyMakerService;
    @Autowired
    private EligibilityPolicyTempDTOMapper eligibilityPolicyDTOMapper;
    public boolean validateUniqueness(EligibilityPolicyTempDTO eligibilityPolicyDTO){
        if(eligibilityPolicyDTO == null || eligibilityPolicyDTO.getPolicyCode()==null)
            return false;
        List<EligibilityPolicyTemp> allEligibilityPolicy=eligibilityPolicyMakerService.viewAllEligibilityPolicy();
        if (allEligibilityPolicy==null || allEligibilityPolicy.isEmpty())
            return false;
        return allEligibilityPolicy.contains(eligibilityPolicyDTOMapper.mapPolicy(eligibilityPolicyDTO));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EligibilityPolicyTempDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o==null)
            return;
        EligibilityPolicyTempDTO eligibilityPolicyDTO=(EligibilityPolicyTempDTO) o;
        if(validateUniqueness(eligibilityPolicyDTO)){
            errors.rejectValue("policyCode","policyCode.UNIQUE","Policy Code already exists..");
        }
        else {
            if (!eligibilityPolicyDTO.getPolicyCode().matches("[a-zA-Z0-9]*")){
                errors.rejectValue("policyCode","policyCode.ALPHANUMERIC","Policy Code must only be alphanumeric");
            }
        }
        List<EligibilityPolicyTemp> allEligibilityPolicy=eligibilityPolicyMakerService.viewAllEligibilityPolicy();
        if (allEligibilityPolicy!=null && !allEligibilityPolicy.isEmpty() && allEligibilityPolicy.stream().filter((eligibilityPolicyDTO1)->eligibilityPolicyDTO1.getEligibilityPolicyName().equalsIgnoreCase(eligibilityPolicyDTO.getPolicyName())).count()>0){
            errors.rejectValue("policyName","policyName.UNIQUE","Policy Name already exists..");
        }
    }

}
