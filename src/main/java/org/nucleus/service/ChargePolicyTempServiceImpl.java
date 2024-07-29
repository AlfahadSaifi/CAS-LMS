package org.nucleus.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.nucleus.dao.ChargePolicyDao;
import org.nucleus.dao.ChargePolicyDaoImpl;
import org.nucleus.dao.ChargePolicyTempDao;
import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.ChargePolicyDto;
import org.nucleus.dto.ChargePolicyParameterTempDto;
import org.nucleus.dto.ChargePolicyTempDto;
import org.nucleus.exception.PolicyCodeAlreadyExistException;
import org.nucleus.utility.enums.Operator;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.temptoperm.ChargePolicyConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargePolicyTempServiceImpl implements ChargePolicyTempService {

    private final static Logger logger = LogManager.getLogger(ChargePolicyTempServiceImpl.class);


    private String recordStatusBeforeUpdate;
    private String updateStatus = "false";

    @Autowired
    private ChargePolicyTempDao chargePolicyTempDao;

    @Autowired
    private ChargePolicyDao chargePolicyDao;

    @Autowired
    private ChargePolicyService chargePolicyService;

    @Autowired
    private ChargePolicyConvertor chargePolicyConvertor;

    public ChargePolicyTempServiceImpl()
    {
    }
    @Override
    public boolean saveChargePolicy(ChargePolicyTempDto chargePolicyTempDto) throws PolicyCodeAlreadyExistException {
            if(chargePolicyTempDto !=null)
            {
                return chargePolicyTempDao.saveChargePolicy(chargePolicyTempDto);
            }
            return false;
        }

    @Override
    public boolean updateChargePolicy(ChargePolicyTempDto chargePolicyTempDto) {
        return chargePolicyTempDao.updateChargePolicy(chargePolicyTempDto);
    }

    @Override
    public ChargePolicyTempDto getChargePolicy(String policyCode) {
        return chargePolicyTempDao.getChargePolicy(policyCode);
    }

    @Override
    public boolean deleteChargePolicy(String policyCode) {
        return chargePolicyTempDao.deleteChargePolicy(policyCode);
    }

    @Override
    public List<ChargePolicyTempDto> getAllChargePolicy() {
        return chargePolicyTempDao.getAllChargePolicy();
    }

    @Override
    public ChargePolicyTempDto getChargePolicyByEditFlag(Boolean flagForEdit) {
        return chargePolicyTempDao.getChargePolicyByEditFlag(flagForEdit);
    }

    @Override
    public List<ChargePolicyDto> filterAndRemoveDuplicates(List<ChargePolicyTempDto> chargePoliciesTempDtoList) {

// get all charge policy form permanent table
        List<ChargePolicyDto> chargePolicyDtoList = chargePolicyDao.getAllChargePolicy();


        if(chargePolicyDtoList != null&&chargePoliciesTempDtoList!=null) {
// check the duplicate policy code
             return chargePolicyDtoList.stream()
                    .filter(policy -> chargePoliciesTempDtoList.stream()
                            .noneMatch(policyTemp -> policyTemp.getPolicyCode().equals(policy.getPolicyCode())))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public ChargePolicyTempDto setCreatedDateAndCreatedBy(ChargePolicyTempDto chargePolicyTempDto) {

        //      to get the username of maker
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

//        if update status is true (in case of save or update)
        if("true".equalsIgnoreCase(updateStatus))
        {
//           get the charge policy details for that policy code
            ChargePolicyTempDto chargePolicyTemp3 = getChargePolicy(chargePolicyTempDto.getPolicyCode());

//            set the creation date and created by

            if(chargePolicyTemp3!=null)
            {
                chargePolicyTempDto.setCreationDate(chargePolicyTemp3.getCreationDate());
                chargePolicyTempDto.setCreatedBy(chargePolicyTemp3.getCreatedBy());
            }
            else
            {
                chargePolicyTempDto.setCreationDate(Date.valueOf(LocalDate.now()));
                chargePolicyTempDto.setCreatedBy(username);
            }


//            then delete that policy from the temporary table so that when we click on save and request, exception (PolicyCodeAlreadyExist) does not occur
            deleteChargePolicy(chargePolicyTempDto.getPolicyCode());
        }


        else {
//            If policy code does not exist then it means maker create a new policy so set new creation date and created by
            chargePolicyTempDto.setCreationDate(Date.valueOf(LocalDate.now()));
            chargePolicyTempDto.setCreatedBy(username);
        }

        return chargePolicyTempDto;
    }

    @Override
    public ChargePolicyTempDto setRecordStatus(ChargePolicyTempDto chargePolicyTempDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //        if new rejected record is modified then set modified date and modified by
        if(("NR").equals(recordStatusBeforeUpdate))
        {
            chargePolicyTempDto.setModifiedDate(Date.valueOf(LocalDate.now()));
            chargePolicyTempDto.setModifiedBy(username);
        }

        if(recordStatusBeforeUpdate!=null)
        {
//           check record status and then set
            if(("N").equals(recordStatusBeforeUpdate) || ("NR").equals(recordStatusBeforeUpdate))
                chargePolicyTempDto.setRecordStatus(RecordStatus.N);
            else if(("M").equals(recordStatusBeforeUpdate) ||("MR").equals(recordStatusBeforeUpdate) || ("A").equals(recordStatusBeforeUpdate)||("DR").equals(recordStatusBeforeUpdate)||("D").equals(recordStatusBeforeUpdate))
                chargePolicyTempDto.setRecordStatus(RecordStatus.M);
        }
        else
        {
//            initially when maker create a new policy the value of recordStatusBeforeUpdate is null so set the recordStatus N
            chargePolicyTempDto.setRecordStatus(RecordStatus.N);
        }
        recordStatusBeforeUpdate=null;
        return chargePolicyTempDto;
    }


    @Override
    public String saveCase(ChargePolicyTempDto chargePolicyTempDto) {

        updateStatus="true";
        if(getChargePolicy(chargePolicyTempDto.getPolicyCode())!=null)
            deleteChargePolicy(chargePolicyTempDto.getPolicyCode());

        try
        {
            saveChargePolicy(chargePolicyTempDto);
            return "true";
        }
        catch(PolicyCodeAlreadyExistException e)
        {
           logger.error(e.getMessage());
           return "exist";
        }
        catch(Exception e)
        {
           logger.error(e.getMessage());
           return e.getMessage();
        }
    }

    @Override
    public String saveAndRequestCase(ChargePolicyTempDto chargePolicyTempDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if("M".equalsIgnoreCase(chargePolicyTempDto.getRecordStatus().toString()))
        {
//                    set the modified date when recordStatus set to M
            chargePolicyTempDto.setModifiedDate(Date.valueOf(LocalDate.now()));
            chargePolicyTempDto.setModifiedBy(username);
        }
        try
        {
//                 check unique constraint in the permanent table
            if("false".equalsIgnoreCase(updateStatus) && chargePolicyService.getChargePolicy(chargePolicyTempDto.getPolicyCode())!=null)
                throw new PolicyCodeAlreadyExistException("This Policy code already approved");

            saveChargePolicy(chargePolicyTempDto);

            //            set the update status again to false
            if("true".equalsIgnoreCase(updateStatus))
            {
                updateStatus = "false";
                logger.info("Charge Policy successfully updated");
                return "updateTrue";
            }
            else
            {
                logger.info("Charge Policy Saved successfully");
                return "true";
            }
        }

        catch(PolicyCodeAlreadyExistException e)
        {
            logger.error(e.getMessage());
            return "exist";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    @Override
    public List<ChargePolicyParameterTempDto> setChargePolicyParameters(List<String> chargeDefinitionCode,List<String> operator,List<String> value) {

        List<ChargePolicyParameterTempDto> chargePolicyParametersDto = new ArrayList<>();
        for (int i = 0; i < chargeDefinitionCode.size(); i++) {
            ChargePolicyParameterTempDto chargePolicyParameterTempDto = new ChargePolicyParameterTempDto();

            chargePolicyParameterTempDto.setChargeDefinitionCode(chargeDefinitionCode.get(i));
            chargePolicyParameterTempDto.setOperator(Operator.valueOf(operator.get(i)));
            chargePolicyParameterTempDto.setValue(Double.parseDouble(value.get(i)));
            chargePolicyParametersDto.add(chargePolicyParameterTempDto);
        }
        return chargePolicyParametersDto;
    }

    @Override
    public boolean deleteCase(String policyCode, String recordStatus) {

        ChargePolicyTempDto chargePolicyTempDtoDel;
        ChargePolicyDto chargePolicyDtoDel ;

//        if recordStatus is A means we try to delete the authorised record
        if("A".equalsIgnoreCase(recordStatus))
        {
//            then change the recordStatus from A to D
            chargePolicyDtoDel = chargePolicyService.getChargePolicy(policyCode);
            chargePolicyDtoDel.setRecordStatus(RecordStatus.D);
            chargePolicyTempDtoDel = chargePolicyConvertor.permDtoToTempDto(chargePolicyDtoDel);
            chargePolicyTempDtoDel.setSaveFlag(false);
            try
            {
               saveChargePolicy(chargePolicyTempDtoDel);
            }
            catch(PolicyCodeAlreadyExistException e)
            {
               logger.error(e.getMessage());
            }
            catch(Exception e)
            {
                logger.error(e.getMessage());
            }

            return true;
        }
        else {
//            If recordStatus is not A then simply delete that policy
              deleteChargePolicy(policyCode);
            return false;
        }
    }

    @Override
    public ChargePolicyTempDto editCase(String policyCode, String recordStatus) {
        // set the update status
        updateStatus="true";
        ChargePolicyTempDto chargePolicyTempDto;
        ChargePolicyDto chargePolicyDto ;

        if("A".equalsIgnoreCase(recordStatus))
        {
            chargePolicyDto = chargePolicyService.getChargePolicy(policyCode);
            chargePolicyTempDto = chargePolicyConvertor.permDtoToTempDto(chargePolicyDto);
            try
            {
                saveChargePolicy(chargePolicyTempDto);
            }
            catch(PolicyCodeAlreadyExistException e)
            {
               logger.error(e.getMessage());
            }
            catch(Exception e)
            {
                logger.error(e.getMessage());
            }
        }
        else
        {
            chargePolicyTempDto = getChargePolicy(policyCode);
        }

//     store the record status
        if(chargePolicyTempDto!=null)
            recordStatusBeforeUpdate = chargePolicyTempDto.getRecordStatus().toString();

        return chargePolicyTempDto;
    }
    @Override
    public List<ChargePolicyTempDto> getAllChargePolicyForChecker() {
        List<ChargePolicyTempDto> chargePolicies =  chargePolicyTempDao.getAllChargePolicy();
        if(chargePolicies!=null) {
            chargePolicies = chargePolicies.stream()
                    .filter(policy -> policy.getRecordStatus() != RecordStatus.NR &&
                            policy.getRecordStatus() != RecordStatus.MR &&
                            policy.getRecordStatus() != RecordStatus.DR)
                    .collect(Collectors.toList());
        }
        return chargePolicies;
    }


}


