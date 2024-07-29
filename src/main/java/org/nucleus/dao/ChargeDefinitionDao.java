package org.nucleus.dao;

import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;

import java.util.List;
//AUTHOR: HARKIRAT SINGH
public interface ChargeDefinitionDao {

    boolean saveNewRecordToMasterTable(ChargeDefinitionDTO chargeDefinitionDTO) throws ChargeDefinitionCodeAlreadyExist;

    List<ChargeDefinitionDTO> fetchRecordsFromMasterTable();
    boolean isChargeDefinitionCodeExists(String chargeDefinitionCode);

    List<ChargeDefinitionDTO> fetchAllRecordsOfTheMasterTable();

    ChargeDefinitionDTO getChargeDefinitionFromTheMasterTableByCode(String chargeDefinitionCode);

    boolean deleteSavedRecordFromTheMasterTable(ChargeDefinitionDTO chargeDefinitionDTO);
}
