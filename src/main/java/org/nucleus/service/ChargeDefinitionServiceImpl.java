package org.nucleus.service;

import org.nucleus.dao.ChargeDefinitionDao;
import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;
import org.nucleus.utility.dtomapper.ChargeDefinitionDTOToChargeDefinitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
//AUTHOR: HARKIRAT SINGH
@Service
public class ChargeDefinitionServiceImpl implements ChargeDefinitionService {

    @Autowired
    ChargeDefinitionDao chargeDefinitionDao;
    @Autowired
    ChargeDefinitionDTOToChargeDefinitionMapper chargeDefinitionDTOMapping;

    @Override
    public boolean saveNewRecordToMasterTable(ChargeDefinitionDTO chargeDefinitionDTO) throws ChargeDefinitionCodeAlreadyExist {
   //     ChargeDefinition chargeDefinition = chargeDefinitionDTOMapping.dtoToEntityConvertor(chargeDefinitionDTO);
        return chargeDefinitionDao.saveNewRecordToMasterTable(chargeDefinitionDTO);
    }

    @Override
    public List<ChargeDefinitionDTO> fetchDetails() {
        return chargeDefinitionDao.fetchRecordsFromMasterTable();
    }

    @Override
    public boolean isChargeDefinitionCodeExists(String chargeDefinitionCode) {
        return chargeDefinitionDao.isChargeDefinitionCodeExists(chargeDefinitionCode);
    }

    @Override
    public List<ChargeDefinitionDTO> fetchDetailsFromTheMasterTable() {
        return chargeDefinitionDao.fetchAllRecordsOfTheMasterTable();
    }

    @Override
    public ChargeDefinitionDTO getChargeDefinitionFromTheMasterTableByCode(String chargeDefinitionCode) {
        return chargeDefinitionDao.getChargeDefinitionFromTheMasterTableByCode(chargeDefinitionCode);
    }

    @Override
    public boolean deleteSavedRecordFromTheMasterTable(ChargeDefinitionDTO chargeDefinitionDTO) {
        return chargeDefinitionDao.deleteSavedRecordFromTheMasterTable(chargeDefinitionDTO);
    }


}
