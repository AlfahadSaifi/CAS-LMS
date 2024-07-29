package org.nucleus.utility.dtomapper;

import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.ChargeDefinitionTempDTO;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.permanent.ChargeDefinition;
import org.nucleus.entity.temporary.ChargeDefinitionTemp;
import org.springframework.stereotype.Component;

@Component
public class ChargeDefinitionDTOToChargeDefinitionMapper {
    public ChargeDefinitionTemp tempDtoToTempEntityConvertor(ChargeDefinitionTempDTO chargeDefinitionTempDTO){
        ChargeDefinitionTemp chargeDefinitionTemp = new ChargeDefinitionTemp();
        chargeDefinitionTemp.setChargeDefinitionCode(chargeDefinitionTempDTO.getChargeDefinitionCode());
        chargeDefinitionTemp.setDescription(chargeDefinitionTempDTO.getDescription());
        chargeDefinitionTemp.setChargeName(chargeDefinitionTempDTO.getChargeName());
        chargeDefinitionTemp.setMinimumAmount(chargeDefinitionTempDTO.getMinimumAmount());
        chargeDefinitionTemp.setMaximumAmount(chargeDefinitionTempDTO.getMaximumAmount());
        chargeDefinitionTemp.setMetaData(chargeDefinitionTempDTO.getMetaData());

        return chargeDefinitionTemp;

    }
    public ChargeDefinitionTempDTO tempEntityToTempDtoConvertor(ChargeDefinitionTemp chargeDefinitionTemp){
        ChargeDefinitionTempDTO chargeDefinitionTempDTO = new ChargeDefinitionTempDTO();
        chargeDefinitionTempDTO.setMetaData(chargeDefinitionTemp.getMetaData());
        chargeDefinitionTempDTO.setChargeDefinitionCode(chargeDefinitionTemp.getChargeDefinitionCode());
        chargeDefinitionTempDTO.setChargeName(chargeDefinitionTemp.getChargeName());
        chargeDefinitionTempDTO.setDescription(chargeDefinitionTemp.getDescription());
        chargeDefinitionTempDTO.setMaximumAmount(chargeDefinitionTemp.getMaximumAmount());
        chargeDefinitionTempDTO.setMinimumAmount(chargeDefinitionTemp.getMinimumAmount());

        return chargeDefinitionTempDTO;
    }
    public ChargeDefinition dtoToEntityConvertor(ChargeDefinitionDTO chargeDefinitionDTO){
        ChargeDefinition chargeDefinition = new ChargeDefinition();
        MetaData metaData = chargeDefinitionDTO.getMetaData();
        chargeDefinition.setChargeDefinitionCode(chargeDefinitionDTO.getChargeDefinitionCode());
        chargeDefinition.setDescription(chargeDefinitionDTO.getDescription());
        chargeDefinition.setChargeName(chargeDefinitionDTO.getChargeName());
        chargeDefinition.setMinimumAmount(chargeDefinitionDTO.getMinimumAmount());
        chargeDefinition.setMaximumAmount(chargeDefinitionDTO.getMaximumAmount());
        chargeDefinition.setMetaData(metaData);

        return chargeDefinition;

    }
    public ChargeDefinitionDTO entityToDtoConvertor(ChargeDefinition chargeDefinition){
        ChargeDefinitionDTO chargeDefinitionDTO = new ChargeDefinitionDTO();
        chargeDefinitionDTO.setMetaData(chargeDefinition.getMetaData());
        chargeDefinitionDTO.setChargeDefinitionCode(chargeDefinition.getChargeDefinitionCode());
        chargeDefinitionDTO.setChargeName(chargeDefinition.getChargeName());
        chargeDefinitionDTO.setDescription(chargeDefinition.getDescription());
        chargeDefinitionDTO.setMaximumAmount(chargeDefinition.getMaximumAmount());
        chargeDefinitionDTO.setMinimumAmount(chargeDefinition.getMinimumAmount());

        return chargeDefinitionDTO;
    }

}
