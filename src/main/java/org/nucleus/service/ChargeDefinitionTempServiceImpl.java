package org.nucleus.service;

import org.nucleus.dao.ChargeDefinitionTempDao;
import org.nucleus.dto.ChargeDefinitionTempDTO;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;
import org.nucleus.utility.dtomapper.ChargeDefinitionDTOToChargeDefinitionMapper;
import org.nucleus.utility.validations.ChargeDefinitionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//AUTHOR: HARKIRAT SINGH
@Service
public class ChargeDefinitionTempServiceImpl implements ChargeDefinitionTempService {
    @Autowired
    ChargeDefinitionTempDao chargeDefinitionTempDao;
    @Autowired
    ChargeDefinitionDTOToChargeDefinitionMapper chargeDefinitionDTOMapping;

    @Override
    public boolean saveChargeDefinition(ChargeDefinitionTempDTO chargeDefinitionTempDTO) throws ChargeDefinitionCodeAlreadyExist {
       // ChargeDefinitionTemp chargeDefinitionTemp1 = chargeDefinitionDTOMapping.tempDtoToTempEntityConvertor(chargeDefinitionTempDTO);
        if(ChargeDefinitionValidation.chargeDefinitionAmountComparison(chargeDefinitionTempDTO)){
            return chargeDefinitionTempDao.saveChargeDefinition(chargeDefinitionTempDTO);
        }
        return false;

    }

    @Override
    public ChargeDefinitionTempDTO fetchSave() {

        return chargeDefinitionTempDao.fetchSaveData();
    }

    @Override
    public List<ChargeDefinitionTempDTO> fetchDetailsByFlag() {

        return chargeDefinitionTempDao.fetchSaveDataByFlag();
    }

    @Override
    public ChargeDefinitionTempDTO getChargeDefinitionByCode(String chargeDefinitionCode) {

        return chargeDefinitionTempDao.fetchChargeDefinitionByCode(chargeDefinitionCode);
    }

    @Override
    public boolean updateChargeDefinition(ChargeDefinitionTempDTO chargeDefinitionTempDTO) {
      //  ChargeDefinitionTemp chargeDefinitionTemp = chargeDefinitionDTOMapping.tempDtoToTempEntityConvertor(chargeDefinitionTempDTO);
        if(ChargeDefinitionValidation.chargeDefinitionAmountComparison(chargeDefinitionTempDTO)) {
            return chargeDefinitionTempDao.updateChargeDefinition(chargeDefinitionTempDTO);
        }
        return false;
    }

    @Override
    public boolean deleteChargeDefinition(String chargeDefinitionCode) {
        return chargeDefinitionTempDao.deleteChargeDefinition(chargeDefinitionCode);
    }

    @Override
    public List<ChargeDefinitionTempDTO> fetchChargeDefinitionFromTemporaryTable() {

        return chargeDefinitionTempDao.fetchChargeDefinitionFromTemporaryTable();
    }

    @Override
    public ChargeDefinitionTempDTO approveChargeDefinition(String chargeDefinitionCode) {
        return chargeDefinitionTempDao.approveChargeDefinition(chargeDefinitionCode);
    }

    @Override
    public boolean rejectChargeDefinition(String chargeDefinitionCode) {
        return chargeDefinitionTempDao.rejectChargeDefinition(chargeDefinitionCode);
    }

    @Override
    public boolean saveModifyRequestDetails(ChargeDefinitionTempDTO chargeDefinitionTempDTO) {
        if(ChargeDefinitionValidation.chargeDefinitionAmountComparison(chargeDefinitionTempDTO)) {
            return chargeDefinitionTempDao.saveModifyRequestDetails(chargeDefinitionTempDTO);
        }
        return false;
    }

    @Override
    public boolean saveDeleteRequestDetails(ChargeDefinitionTempDTO chargeDefinitionTempDTO) {
        return chargeDefinitionTempDao.saveDeleteRequestDetails(chargeDefinitionTempDTO);
    }
//
//    @Override
//    public ChargeDefinitionTempDTO rejectChargeDefinition(String chargeDefinitionCode) {
//        ChargeDefinitionTemp chargeDefinitionTemp = chargeDefinitionTempDao.reject
//    }


}
