package org.nucleus.service;

import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;

import java.util.List;

public interface CheckerEligibilityPolicyParameterDefinitionService {
    boolean insertPerEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO);
    boolean updateEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO);

    boolean deleteEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO);
    List<EligibilityPolicyParameterDefinitionDTO> getAllEligibilityParameter();
    EligibilityPolicyParameterDefinitionDTO getEligibilityParameterById(Long eligibilityParameterPerId);

    boolean deleteEligibilityParameterById( Long id);

    boolean approveEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO);

    List<EligibilityPolicyParameterDefinitionDTO> getAllPerEligibilityParameter();

    EligibilityPolicyParameterDefinitionDTO searchEligibilityParameterPerByCode(String code);

    EligibilityPolicyParameterDefinitionDTO rejectEligibilityPolicyParameterDefinition(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO);
}
