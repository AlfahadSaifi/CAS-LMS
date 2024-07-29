package org.nucleus.utility.dtomapper;

import org.nucleus.dto.ChargePolicyDto;
import org.nucleus.dto.ChargePolicyParameterDto;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.permanent.ChargePolicy;
import org.nucleus.entity.permanent.ChargePolicyParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ChargePolicyDtoMapper {

    public ChargePolicy dtoToEntityConverter(ChargePolicyDto chargePolicyDto)
    {
        ChargePolicy chargePolicy = new ChargePolicy();
        chargePolicy.setPolicyCode(chargePolicyDto.getPolicyCode());
        chargePolicy.setPolicyName(chargePolicyDto.getPolicyName());

        List<ChargePolicyParameter> chargePolicyParameters = new ArrayList<>();
        List<ChargePolicyParameterDto> chargePolicyParameterDtoList = chargePolicyDto.getChargePolicyParameterList();

        for(ChargePolicyParameterDto chargePolicyParameterDto : chargePolicyParameterDtoList)
        {
            ChargePolicyParameter chargePolicyParameterTemp = new ChargePolicyParameter();
            chargePolicyParameterTemp.setChargePolicyParameterId(chargePolicyParameterDto.getChargePolicyParameterId());

            chargePolicyParameterTemp.setChargeDefinitionCode(chargePolicyParameterDto.getChargeDefinitionCode());
            chargePolicyParameterTemp.setOperator(chargePolicyParameterDto.getOperator());
            chargePolicyParameterTemp.setValue(chargePolicyParameterDto.getValue());

            chargePolicyParameters.add(chargePolicyParameterTemp);
        }

        chargePolicy.setChargePolicyParameterList(chargePolicyParameters);
        MetaData metaData = new MetaData();
//        metaData.setSaveFlag(chargePolicyDto.getSaveFlag());
        metaData.setCreationDate(chargePolicyDto.getCreationDate());
        metaData.setCreatedBy(chargePolicyDto.getCreatedBy());
        metaData.setAuthorizedBy(chargePolicyDto.getAuthorizedBy());
        metaData.setAuthorizedDate(chargePolicyDto.getAuthorizedDate());
        metaData.setModifiedBy(chargePolicyDto.getModifiedBy());
        metaData.setModifiedDate(chargePolicyDto.getModifiedDate());
        metaData.setRecordStatus(chargePolicyDto.getRecordStatus());
        metaData.setActiveInactiveFlag(chargePolicyDto.getActiveInactiveFlag());
        chargePolicy.setMetaData(metaData);
        return chargePolicy;
    }

    public ChargePolicyDto entityToDtoConverter(ChargePolicy chargePolicy)
    {
        ChargePolicyDto chargePolicyDto = new ChargePolicyDto();
        chargePolicyDto.setPolicyCode(chargePolicy.getPolicyCode());
        chargePolicyDto.setPolicyName(chargePolicy.getPolicyName());

        List<ChargePolicyParameterDto> chargePolicyParameterTempDtoList = new ArrayList<>();
        List<ChargePolicyParameter> chargePolicyParameterList = chargePolicy.getChargePolicyParameterList();

        for(ChargePolicyParameter chargePolicyParameter : chargePolicyParameterList)
        {
            ChargePolicyParameterDto chargePolicyParameterTempDto = new ChargePolicyParameterDto();
            chargePolicyParameterTempDto.setChargePolicyParameterId(chargePolicyParameter.getChargePolicyParameterId());


//            set the charge definition code
            chargePolicyParameterTempDto.setChargeDefinitionCode(chargePolicyParameter.getChargeDefinitionCode());
            chargePolicyParameterTempDto.setOperator(chargePolicyParameter.getOperator());
            chargePolicyParameterTempDto.setValue(chargePolicyParameter.getValue());
            chargePolicyParameterTempDtoList.add(chargePolicyParameterTempDto);
        }

        chargePolicyDto.setChargePolicyParameterList(chargePolicyParameterTempDtoList);

        MetaData metaData =chargePolicy.getMetaData();
//        chargePolicyDto.setSaveFlag(metaData.getSaveFlag());
        chargePolicyDto.setCreationDate(metaData.getCreationDate());
        chargePolicyDto.setCreatedBy(metaData.getCreatedBy());
        chargePolicyDto.setModifiedBy(metaData.getModifiedBy());
        chargePolicyDto.setModifiedDate(metaData.getModifiedDate());
        chargePolicyDto.setAuthorizedBy(metaData.getAuthorizedBy());
        chargePolicyDto.setAuthorizedDate(metaData.getAuthorizedDate());
        chargePolicyDto.setActiveInactiveFlag(metaData.getActiveInactiveFlag());
        chargePolicyDto.setRecordStatus(metaData.getRecordStatus());


        return chargePolicyDto;
    }
}
