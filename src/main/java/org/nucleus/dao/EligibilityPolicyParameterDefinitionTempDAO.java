package org.nucleus.dao;

import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;

import java.util.List;

public interface EligibilityPolicyParameterDefinitionTempDAO {


    List<EligibilityPolicyParameterDefinitionDTO> getAllReqEligibilityParameter();

    boolean deleteEligibilityParameterById( Long id);
    boolean updateEligibilityParameterById( Long id);

//    EligibilityParameterTemp searchEligibilityParameterByCode(String code);


    boolean deleteTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);
    boolean insertTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);
    boolean updateTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto);
    EligibilityPolicyParameterDefinitionDTO getTempEligibilityParameterById(Long eligibilityParameterId);
    List<EligibilityPolicyParameterDefinitionDTO> getAllTempEligibilityParameter();
    EligibilityPolicyParameterDefinitionDTO searchEligibilityParameterPerByCode(String code);
    EligibilityPolicyParameterDefinitionDTO getSavedParameter();


    boolean deleteTempEligibilityParameterByCode(String code);


}
