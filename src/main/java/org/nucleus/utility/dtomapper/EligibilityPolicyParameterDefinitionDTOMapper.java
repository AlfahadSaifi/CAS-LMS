package org.nucleus.utility.dtomapper;

import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.entity.permanent.EligibilityPolicyParameterDefinition;
import org.nucleus.entity.temporary.EligibilityPolicyParameterDefinitionTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EligibilityPolicyParameterDefinitionDTOMapper {

    MetaDataMapper metaDataMapper = new MetaDataMapper();
    public EligibilityPolicyParameterDefinitionDTO tempEligibilityParameterToDTO(EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp){
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO=new EligibilityPolicyParameterDefinitionDTO();
        eligibilityParameterDTO.setEligibilityParameterId(eligibilityParameterTemp.getEligibilityParameterId());
        eligibilityParameterDTO.setEligibilityParameterCode(eligibilityParameterTemp.getEligibilityParameterCode());
        eligibilityParameterDTO.setEligibilityParameterName(eligibilityParameterTemp.getEligibilityParameterName());
        eligibilityParameterDTO.setEligibilityParameterDescription(eligibilityParameterTemp.getEligibilityParameterDescription());
        eligibilityParameterDTO.setTempMetaData(eligibilityParameterTemp.getMetaData());
        return eligibilityParameterDTO;
    }


    public EligibilityPolicyParameterDefinitionTemp dTOToEligibilityParameterTemp(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO){
        EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp = new EligibilityPolicyParameterDefinitionTemp();
       eligibilityParameterTemp.setEligibilityParameterId(eligibilityParameterDTO.getEligibilityParameterId());
       eligibilityParameterTemp.setEligibilityParameterCode(eligibilityParameterDTO.getEligibilityParameterCode());
       eligibilityParameterTemp.setEligibilityParameterName(eligibilityParameterDTO.getEligibilityParameterName());
       eligibilityParameterTemp.setEligibilityParameterDescription(eligibilityParameterDTO.getEligibilityParameterDescription());
       eligibilityParameterTemp.setMetaData(eligibilityParameterDTO.getTempMetaData());
       return eligibilityParameterTemp;
    }



    public EligibilityPolicyParameterDefinitionDTO perEligibilityParameterToDTO(EligibilityPolicyParameterDefinition eligibilityParameterPer){
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO=new EligibilityPolicyParameterDefinitionDTO();
        eligibilityParameterDTO.setEligibilityParameterId(eligibilityParameterPer.getEligibilityParameterId());
        eligibilityParameterDTO.setEligibilityParameterCode(eligibilityParameterPer.getEligibilityParameterCode());
        eligibilityParameterDTO.setEligibilityParameterName(eligibilityParameterPer.getEligibilityParameterName());
        eligibilityParameterDTO.setEligibilityParameterDescription(eligibilityParameterPer.getEligibilityParameterDescription());
        eligibilityParameterDTO.setTempMetaData(metaDataMapper.convertToTempMetaData(eligibilityParameterPer.getMetaData()));
        return eligibilityParameterDTO;
    }

    public EligibilityPolicyParameterDefinition dTOToEligibilityParameterPer(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO){
        EligibilityPolicyParameterDefinition eligibilityParameterPer = new EligibilityPolicyParameterDefinition();
        eligibilityParameterPer.setEligibilityParameterId(eligibilityParameterDTO.getEligibilityParameterId());
        eligibilityParameterPer.setEligibilityParameterCode(eligibilityParameterDTO.getEligibilityParameterCode());
        eligibilityParameterPer.setEligibilityParameterName(eligibilityParameterDTO.getEligibilityParameterName());
        eligibilityParameterPer.setEligibilityParameterDescription(eligibilityParameterDTO.getEligibilityParameterDescription());
        eligibilityParameterPer.setMetaData(metaDataMapper.convertToMetaData(eligibilityParameterDTO.getTempMetaData()));
        return eligibilityParameterPer;
    }



}
