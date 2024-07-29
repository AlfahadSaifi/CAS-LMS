package org.nucleus.utility.dtomapper;

import org.nucleus.dto.ChargePolicyParameterTempDto;
import org.nucleus.dto.ChargePolicyTempDto;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.temporary.ChargePolicyParameterTemp;
import org.nucleus.entity.temporary.ChargePolicyTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ChargePolicyTempDtoMapper {


    public ChargePolicyTemp dtoToEntityConverter(ChargePolicyTempDto chargePolicyTempDto)
    {
        ChargePolicyTemp chargePolicy = new ChargePolicyTemp();
        chargePolicy.setPolicyCode(chargePolicyTempDto.getPolicyCode());
        chargePolicy.setPolicyName(chargePolicyTempDto.getPolicyName());

        List<ChargePolicyParameterTemp> chargePolicyParameterTempList = new ArrayList<>();
        List<ChargePolicyParameterTempDto> chargePolicyParameterTempDtoList = chargePolicyTempDto.getChargePolicyParameterList();

        for(ChargePolicyParameterTempDto chargePolicyParameterTempDto : chargePolicyParameterTempDtoList)
        {
            ChargePolicyParameterTemp chargePolicyParameterTemp = new ChargePolicyParameterTemp();
            chargePolicyParameterTemp.setChargePolicyParameterId(chargePolicyParameterTempDto.getChargePolicyParameterId());


            chargePolicyParameterTemp.setChargeDefinitionCode(chargePolicyParameterTempDto.getChargeDefinitionCode());
            chargePolicyParameterTemp.setOperator(chargePolicyParameterTempDto.getOperator());
            chargePolicyParameterTemp.setValue(chargePolicyParameterTempDto.getValue());

            chargePolicyParameterTempList.add(chargePolicyParameterTemp);
        }
        chargePolicy.setChargePolicyParameterTempList(chargePolicyParameterTempList);
        TempMetaData tempMetaData = new TempMetaData();

        tempMetaData.setSaveFlag(chargePolicyTempDto.getSaveFlag());
        tempMetaData.setCreationDate(chargePolicyTempDto.getCreationDate());
        tempMetaData.setCreatedBy(chargePolicyTempDto.getCreatedBy());
        tempMetaData.setAuthorizedBy(chargePolicyTempDto.getAuthorizedBy());
        tempMetaData.setAuthorizedDate(chargePolicyTempDto.getAuthorizedDate());
        tempMetaData.setModifiedBy(chargePolicyTempDto.getModifiedBy());
        tempMetaData.setModifiedDate(chargePolicyTempDto.getModifiedDate());
        tempMetaData.setRecordStatus(chargePolicyTempDto.getRecordStatus());
        tempMetaData.setActiveInactiveFlag(chargePolicyTempDto.getActiveInactiveFlag());


        chargePolicy.setTempMetaData(tempMetaData);

        return chargePolicy;
    }

    public ChargePolicyTempDto entityToDtoConverter(ChargePolicyTemp chargePolicy)
    {
        ChargePolicyTempDto chargePolicyTempDto = new ChargePolicyTempDto();
        chargePolicyTempDto.setPolicyCode(chargePolicy.getPolicyCode());
        chargePolicyTempDto.setPolicyName(chargePolicy.getPolicyName());

        List<ChargePolicyParameterTempDto> chargePolicyParameterTempDtoList = new ArrayList<>();
        List<ChargePolicyParameterTemp> chargePolicyParameterTempList = chargePolicy.getChargePolicyParameterTempList();

        for(ChargePolicyParameterTemp chargePolicyParameterTemp : chargePolicyParameterTempList)
        {
            ChargePolicyParameterTempDto chargePolicyParameterTempDto = new ChargePolicyParameterTempDto();

            chargePolicyParameterTempDto.setChargePolicyParameterId(chargePolicyParameterTemp.getChargePolicyParameterId());
            chargePolicyParameterTempDto.setChargeDefinitionCode(chargePolicyParameterTemp.getChargeDefinitionCode());
            chargePolicyParameterTempDto.setOperator(chargePolicyParameterTemp.getOperator());
            chargePolicyParameterTempDto.setValue(chargePolicyParameterTemp.getValue());
            chargePolicyParameterTempDtoList.add(chargePolicyParameterTempDto);
        }

        chargePolicyTempDto.setChargePolicyParameterList(chargePolicyParameterTempDtoList);

        TempMetaData metaData =chargePolicy.getTempMetaData();
        chargePolicyTempDto.setSaveFlag(metaData.getSaveFlag());
        chargePolicyTempDto.setCreationDate(metaData.getCreationDate());
        chargePolicyTempDto.setCreatedBy(metaData.getCreatedBy());
        chargePolicyTempDto.setModifiedBy(metaData.getModifiedBy());
        chargePolicyTempDto.setModifiedDate(metaData.getModifiedDate());
        chargePolicyTempDto.setAuthorizedBy(metaData.getAuthorizedBy());
        chargePolicyTempDto.setAuthorizedDate(metaData.getAuthorizedDate());
        chargePolicyTempDto.setActiveInactiveFlag(metaData.getActiveInactiveFlag());
        chargePolicyTempDto.setRecordStatus(metaData.getRecordStatus());


        return chargePolicyTempDto;
    }
}
