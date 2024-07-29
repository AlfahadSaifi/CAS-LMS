package org.nucleus.service;

import org.nucleus.dao.EligibilityPolicyParameterDefinitionDAO;
import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CheckerEligibilityPolicyParameterDefinitionServiceImpl implements CheckerEligibilityPolicyParameterDefinitionService{

    private final EligibilityPolicyParameterDefinitionDAO eligibilityParametersPerDAO ;
    private final MakerEligibilityPolicyParameterDefinitionService makerEligibilityParameterService ;


    @Autowired
    public CheckerEligibilityPolicyParameterDefinitionServiceImpl(EligibilityPolicyParameterDefinitionDAO eligibilityParametersPerDAO, MakerEligibilityPolicyParameterDefinitionService makerEligibilityParameterService) {
        this.eligibilityParametersPerDAO = eligibilityParametersPerDAO;
        this.makerEligibilityParameterService=makerEligibilityParameterService;
    }

    @Override
    public boolean insertPerEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO) {
        if(eligibilityParameterDTO != null)
            return eligibilityParametersPerDAO.insertPerEligibilityParameter(eligibilityParameterDTO);
        return false;
    }

    @Override
    public boolean updateEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO) {
        return eligibilityParametersPerDAO.updatePerEligibilityParameter(eligibilityParameterDTO);
    }

    @Override
    public boolean deleteEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO) {
        return eligibilityParametersPerDAO.deletePerEligibilityParameter(eligibilityParameterDTO);
    }

    @Override
    public List<EligibilityPolicyParameterDefinitionDTO> getAllEligibilityParameter() {
        return eligibilityParametersPerDAO.getAllPerEligibilityParameter();
    }



    @Override
    public EligibilityPolicyParameterDefinitionDTO getEligibilityParameterById(Long eligibilityParametersId) {
        return eligibilityParametersPerDAO.getEligibilityParameterById(eligibilityParametersId);
    }

    @Override
    public boolean deleteEligibilityParameterById(Long id) {
        return eligibilityParametersPerDAO.deleteEligibilityParameterPerById(id);
    }


    //    approve method for approving parameter
    @Override
    public boolean approveEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO) {
        if(eligibilityParameterDTO != null) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            if (authentication != null) {
                username = authentication.getName();
            }
            eligibilityParameterDTO.getTempMetaData().setAuthorizedBy(username);
            eligibilityParameterDTO.getTempMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));

            if (eligibilityParameterDTO.getTempMetaData().getRecordStatus().equals(RecordStatus.N)) {
                eligibilityParameterDTO.getTempMetaData().setRecordStatus(RecordStatus.A);
                eligibilityParametersPerDAO.insertPerEligibilityParameter(eligibilityParameterDTO);
            }

            if (eligibilityParameterDTO.getTempMetaData().getRecordStatus().equals(RecordStatus.M)) {
                eligibilityParameterDTO.getTempMetaData().setRecordStatus(RecordStatus.A);

                eligibilityParametersPerDAO.deletePerEligibilityParameterByCode(eligibilityParameterDTO.getEligibilityParameterCode());
                eligibilityParametersPerDAO.insertPerEligibilityParameter(eligibilityParameterDTO);
            }
            if (eligibilityParameterDTO.getTempMetaData().getRecordStatus().equals(RecordStatus.D)) {
                eligibilityParametersPerDAO.deletePerEligibilityParameterByCode(eligibilityParameterDTO.getEligibilityParameterCode());
            }


            makerEligibilityParameterService.deleteTempEligibilityParameterByCode(eligibilityParameterDTO.getEligibilityParameterCode());
            return true;
        }
        return false;
    }


    @Override
    public List<EligibilityPolicyParameterDefinitionDTO> getAllPerEligibilityParameter() {
        return eligibilityParametersPerDAO.getAllPerEligibilityParameter();
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO searchEligibilityParameterPerByCode(String code) {
        return eligibilityParametersPerDAO.searchEligibilityParameterPerByCode(code);
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO rejectEligibilityPolicyParameterDefinition(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        eligibilityParameterDTO.getTempMetaData().setAuthorizedBy(username);
        eligibilityParameterDTO.getTempMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));


        if(eligibilityParameterDTO.getTempMetaData().getRecordStatus().toString().equalsIgnoreCase("N")){

            eligibilityParameterDTO.getTempMetaData().setRecordStatus(RecordStatus.NR);
        }

        else if(eligibilityParameterDTO.getTempMetaData().getRecordStatus().equals(RecordStatus.M))
            eligibilityParameterDTO.getTempMetaData().setRecordStatus(RecordStatus.MR);
        else if(eligibilityParameterDTO.getTempMetaData().getRecordStatus().equals(RecordStatus.D))
            eligibilityParameterDTO.getTempMetaData().setRecordStatus(RecordStatus.DR);
        return eligibilityParameterDTO;
    }
}
