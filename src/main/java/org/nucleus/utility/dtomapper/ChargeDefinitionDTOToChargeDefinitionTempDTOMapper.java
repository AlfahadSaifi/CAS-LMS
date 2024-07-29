package org.nucleus.utility.dtomapper;

import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.ChargeDefinitionTempDTO;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.springframework.stereotype.Component;

@Component
public class ChargeDefinitionDTOToChargeDefinitionTempDTOMapper {
    public ChargeDefinitionDTO chargeDefinitionTempDTOToChargeDefinitionDTOConvertor(ChargeDefinitionTempDTO chargeDefinitionTempDTO){
        ChargeDefinitionDTO chargeDefinitionDTO = new ChargeDefinitionDTO();
        chargeDefinitionDTO.setChargeDefinitionCode(chargeDefinitionTempDTO.getChargeDefinitionCode());
        chargeDefinitionDTO.setChargeName(chargeDefinitionTempDTO.getChargeName());
        chargeDefinitionDTO.setDescription(chargeDefinitionTempDTO.getDescription());
//        chargeDefinitionDTO.setMetaData(chargeDefinitionTempDTO.getMetaData());
        TempMetaData tempMetaData = chargeDefinitionTempDTO.getMetaData();
        MetaData metaData = new MetaData();
        metaData.setRecordStatus(tempMetaData.getRecordStatus());
        metaData.setAuthorizedBy(tempMetaData.getAuthorizedBy());
        metaData.setActiveInactiveFlag(tempMetaData.getActiveInactiveFlag());
        metaData.setAuthorizedDate(tempMetaData.getAuthorizedDate());
        metaData.setModifiedBy(tempMetaData.getModifiedBy());
        metaData.setCreatedBy(tempMetaData.getCreatedBy());
        metaData.setCreationDate(tempMetaData.getCreationDate());
        metaData.setModifiedDate(tempMetaData.getModifiedDate());
        chargeDefinitionDTO.setMetaData(metaData);
        chargeDefinitionDTO.setMinimumAmount(chargeDefinitionTempDTO.getMinimumAmount());
        chargeDefinitionDTO.setMaximumAmount(chargeDefinitionTempDTO.getMaximumAmount());
        return chargeDefinitionDTO;
    }
    public ChargeDefinitionTempDTO chargeDefinitionDTOToChargeDefinitionTempDTOConvertor(ChargeDefinitionDTO chargeDefinitionDTO){
        ChargeDefinitionTempDTO chargeDefinitionTempDTO = new ChargeDefinitionTempDTO();
        chargeDefinitionTempDTO.setChargeDefinitionCode(chargeDefinitionDTO.getChargeDefinitionCode());
        chargeDefinitionTempDTO.setChargeName(chargeDefinitionDTO.getChargeName());
        chargeDefinitionTempDTO.setDescription(chargeDefinitionDTO.getDescription());
        chargeDefinitionTempDTO.setMinimumAmount(chargeDefinitionDTO.getMinimumAmount());
        chargeDefinitionTempDTO.setMaximumAmount(chargeDefinitionDTO.getMaximumAmount());
      //  chargeDefinitionDTO.setMetaData(chargeDefinitionTempDTO.getMetaData());
        if(chargeDefinitionDTO.getMetaData()!=null){
            MetaData metaData = chargeDefinitionDTO.getMetaData();
            System.out.println(metaData);
            TempMetaData tempMetaData = new TempMetaData();
            tempMetaData.setRecordStatus(metaData.getRecordStatus());
            tempMetaData.setAuthorizedBy(metaData.getAuthorizedBy());
            tempMetaData.setActiveInactiveFlag(metaData.getActiveInactiveFlag());
            tempMetaData.setAuthorizedDate(metaData.getAuthorizedDate());
            tempMetaData.setModifiedBy(metaData.getModifiedBy());
            tempMetaData.setCreatedBy(metaData.getCreatedBy());
            tempMetaData.setCreationDate(metaData.getCreationDate());
            tempMetaData.setModifiedDate(metaData.getModifiedDate());
            chargeDefinitionTempDTO.setMetaData(tempMetaData);
        }


        return chargeDefinitionTempDTO;
    }
}
