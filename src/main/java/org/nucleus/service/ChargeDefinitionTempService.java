package org.nucleus.service;

import org.nucleus.dto.ChargeDefinitionTempDTO;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;

import javax.transaction.Transactional;
import java.util.List;
//AUTHOR: HARKIRAT SINGH
public interface ChargeDefinitionTempService {
    boolean saveChargeDefinition(ChargeDefinitionTempDTO chargeDefinition) throws ChargeDefinitionCodeAlreadyExist;
    ChargeDefinitionTempDTO fetchSave();
    List<ChargeDefinitionTempDTO> fetchDetailsByFlag();
    ChargeDefinitionTempDTO getChargeDefinitionByCode(String chargeDefinitionCode);

    boolean updateChargeDefinition(ChargeDefinitionTempDTO chargeDefinitionTempDTO);

    boolean deleteChargeDefinition(String chargeDefinitionCode);
    List<ChargeDefinitionTempDTO> fetchChargeDefinitionFromTemporaryTable();

    ChargeDefinitionTempDTO approveChargeDefinition(String chargeDefinitionCode);

    boolean rejectChargeDefinition(String chargeDefinitionCode);

    boolean saveModifyRequestDetails(ChargeDefinitionTempDTO chargeDefinitionTempDTO);

    boolean saveDeleteRequestDetails(ChargeDefinitionTempDTO chargeDefinitionTempDTO);


//    ChargeDefinitionTempDTO rejectChargeDefinition(String chargeDefinitionCode);
}
