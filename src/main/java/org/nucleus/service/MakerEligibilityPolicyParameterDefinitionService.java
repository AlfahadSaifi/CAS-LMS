package org.nucleus.service;

import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;

import java.util.List;

public interface MakerEligibilityPolicyParameterDefinitionService {

    List<EligibilityPolicyParameterDefinitionDTO> getAllReqEligibilityParameter();

    boolean deleteEligibilityParameterById( Long id);


    boolean deleteTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);
    boolean insertTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);
    boolean updateTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);
    boolean checkerUpdateTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);
    EligibilityPolicyParameterDefinitionDTO getTempEligibilityParameterById(Long eligibilityParameterId);
    List<EligibilityPolicyParameterDefinitionDTO> getAllTempEligibilityParameter();

    EligibilityPolicyParameterDefinitionDTO searchEligibilityParameterPerByCode(String code);
    EligibilityPolicyParameterDefinitionDTO getSavedParameter();

    public boolean deleteTempEligibilityParameterByCode(String code);

    boolean saveTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);

}
