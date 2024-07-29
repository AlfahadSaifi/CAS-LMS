package org.nucleus.service;

import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;

import javax.transaction.Transactional;
import java.util.List;


//AUTHOR: HARKIRAT SINGH
public interface ChargeDefinitionService {


    boolean saveNewRecordToMasterTable(ChargeDefinitionDTO chargeDefinitionDTO) throws ChargeDefinitionCodeAlreadyExist;

    List<ChargeDefinitionDTO> fetchDetails();
    boolean isChargeDefinitionCodeExists(String chargeDefinitionCode);


    List<ChargeDefinitionDTO> fetchDetailsFromTheMasterTable();

    ChargeDefinitionDTO getChargeDefinitionFromTheMasterTableByCode(String chargeDefinitionCode);

    boolean deleteSavedRecordFromTheMasterTable(ChargeDefinitionDTO chargeDefinitionDTO);
}
