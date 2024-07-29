package org.nucleus.service;

import org.nucleus.dao.EligibilityPolicyParameterDefinitionTempDAO;
import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Transactional
@Service

public class MakerEligibilityPolicyParameterDefinitionServiceImpl implements MakerEligibilityPolicyParameterDefinitionService{

    private final EligibilityPolicyParameterDefinitionTempDAO eligibilityParametersDAO ;



    @Autowired
    public MakerEligibilityPolicyParameterDefinitionServiceImpl(EligibilityPolicyParameterDefinitionTempDAO eligibilityParametersDAO) {
        this.eligibilityParametersDAO = eligibilityParametersDAO;
    }




    @Override
    public List<EligibilityPolicyParameterDefinitionDTO> getAllReqEligibilityParameter() {
        return eligibilityParametersDAO.getAllReqEligibilityParameter();
    }



    @Override
    public boolean deleteEligibilityParameterById(Long id) {
        return eligibilityParametersDAO.deleteEligibilityParameterById(id);
    }



    //    DTO
    @Override
    public boolean deleteTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        return eligibilityParametersDAO.deleteTempEligibilityParameter(eligibilityParameterDto);
    }

    @Override
    public boolean insertTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        if(eligibilityParameterDto !=null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            if(authentication !=null)
                username = authentication.getName();
            if(eligibilityParameterDto.getTempMetaData()!=null) {
                if (eligibilityParameterDto.getTempMetaData().getRecordStatus().equals(RecordStatus.N)) {
                    eligibilityParameterDto.getTempMetaData().setCreatedBy(username);
                    eligibilityParameterDto.getTempMetaData().setCreationDate(Date.valueOf(LocalDate.now()));
                } else if (eligibilityParameterDto.getTempMetaData().getRecordStatus().equals(RecordStatus.D)) {
                    eligibilityParameterDto.getTempMetaData().setModifiedBy(username);
                    eligibilityParameterDto.getTempMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                } else {
                    eligibilityParameterDto.getTempMetaData().setRecordStatus(RecordStatus.M);
                    eligibilityParameterDto.getTempMetaData().setModifiedBy(username);
                    eligibilityParameterDto.getTempMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                }
            }


            return eligibilityParametersDAO.insertTempEligibilityParameter(eligibilityParameterDto);
        }
        return false;
    }

    public boolean saveTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        return eligibilityParametersDAO.insertTempEligibilityParameter(eligibilityParameterDto);
    }

    @Override
    public boolean updateTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        if(eligibilityParameterDto !=null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username=null;
            if(authentication !=null){
                username = authentication.getName();
            }
            if(eligibilityParameterDto.getTempMetaData()!=null) {
//        for save
                if (eligibilityParameterDto.getTempMetaData().getRecordStatus() == null) {
                    eligibilityParameterDto.getTempMetaData().setRecordStatus(RecordStatus.N);
                    eligibilityParameterDto.getTempMetaData().setSaveFlag(false);
                    eligibilityParameterDto.getTempMetaData().setCreatedBy(username);
                    eligibilityParameterDto.getTempMetaData().setCreationDate(Date.valueOf(LocalDate.now()));
                    return eligibilityParametersDAO.updateTempEligibilityParameter(eligibilityParameterDto);
                } else if (eligibilityParameterDto.getTempMetaData().getRecordStatus().equals(RecordStatus.MR)
                        || eligibilityParameterDto.getTempMetaData().getRecordStatus().equals(RecordStatus.M)) {
                    eligibilityParameterDto.getTempMetaData().setRecordStatus(RecordStatus.M);
                } else {
                    eligibilityParameterDto.getTempMetaData().setRecordStatus(RecordStatus.N);

                }
                eligibilityParameterDto.getTempMetaData().setModifiedBy(username);
                eligibilityParameterDto.getTempMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            }
            return eligibilityParametersDAO.updateTempEligibilityParameter(eligibilityParameterDto);
        }
        return false;
    }
    @Override
    public boolean checkerUpdateTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        if(eligibilityParameterDto != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username=null;
            if(authentication !=null){
                username = authentication.getName();
            }
            if(eligibilityParameterDto.getTempMetaData()!=null) {
                eligibilityParameterDto.getTempMetaData().setAuthorizedBy(username);
                eligibilityParameterDto.getTempMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
            }
            return eligibilityParametersDAO.updateTempEligibilityParameter(eligibilityParameterDto);

        }
        return false;
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO getTempEligibilityParameterById(Long eligibilityParameterId) {
        return eligibilityParametersDAO.getTempEligibilityParameterById(eligibilityParameterId);
    }

    @Override
    public List<EligibilityPolicyParameterDefinitionDTO> getAllTempEligibilityParameter() {
        return eligibilityParametersDAO.getAllTempEligibilityParameter();
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO searchEligibilityParameterPerByCode(String code) {
        return eligibilityParametersDAO.searchEligibilityParameterPerByCode(code);
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO getSavedParameter() {
        return eligibilityParametersDAO.getSavedParameter();
    }

    @Override
    public boolean deleteTempEligibilityParameterByCode(String code) {
        return eligibilityParametersDAO.deleteTempEligibilityParameterByCode(code);
    }
}
