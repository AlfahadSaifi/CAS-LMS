package org.nucleus.dao;

import org.nucleus.dto.ChargePolicyDto;

import java.util.List;

public interface ChargePolicyDao {

    boolean saveChargePolicy(ChargePolicyDto chargePolicyDto);

    boolean editChargePolicy(ChargePolicyDto chargePolicyTempDto);

    ChargePolicyDto getChargePolicy(String policyCode);

    boolean deleteChargePolicy(String policyCode);

    List<ChargePolicyDto> getAllChargePolicy();

}
