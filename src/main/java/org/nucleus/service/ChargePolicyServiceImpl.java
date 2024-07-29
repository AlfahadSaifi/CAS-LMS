package org.nucleus.service;

import org.nucleus.dao.ChargePolicyDao;
import org.nucleus.dto.ChargePolicyDto;
import org.nucleus.dto.ChargePolicyTempDto;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.temptoperm.ChargePolicyConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service

public class ChargePolicyServiceImpl implements ChargePolicyService {


    @Autowired
    private ChargePolicyDao chargePolicyDao;
    @Autowired
    private ChargePolicyTempService chargePolicyTempService;

    @Autowired
    private ChargePolicyConvertor chargePolicyConvertor;

    public ChargePolicyServiceImpl()
    {

    }
    @Override
    public boolean saveChargePolicy(ChargePolicyDto chargePolicyDto){
            if(chargePolicyDto !=null)
            {
                return chargePolicyDao.saveChargePolicy(chargePolicyDto);
            }
            return false;
        }

    @Override
    public boolean editChargePolicy(ChargePolicyDto chargePolicyDto) {
        return chargePolicyDao.editChargePolicy(chargePolicyDto);
    }

    @Override
    public ChargePolicyDto getChargePolicy(String policyCode) {
        return chargePolicyDao.getChargePolicy(policyCode);
    }

    @Override
    public boolean deleteChargePolicy(String policyCode) {
        return chargePolicyDao.deleteChargePolicy(policyCode);
    }

    @Override
    public List<ChargePolicyDto> getAllChargePolicy() {
        return chargePolicyDao.getAllChargePolicy();
    }

    @Override
    public boolean policyAuthorization(String policyCode) {
        //Fetching Authorized person name
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //fetching info of charge policy from tempDto
        ChargePolicyTempDto chargePolicyTempDto = chargePolicyTempService.getChargePolicy(policyCode);
        if (chargePolicyTempDto != null) {
            if (RecordStatus.D == chargePolicyTempDto.getRecordStatus()) {
                //Delete from both Table if Status is D
                deleteChargePolicy(chargePolicyTempDto.getPolicyCode());
                chargePolicyTempService.deleteChargePolicy(chargePolicyTempDto.getPolicyCode());
            } else {
                chargePolicyTempDto.setRecordStatus(RecordStatus.A);
                //setting name and status of charge policy here

                chargePolicyTempDto.setAuthorizedDate(Date.valueOf(LocalDate.now()));
                chargePolicyTempDto.setAuthorizedBy(username);

                ChargePolicyDto chargePolicyDto = chargePolicyConvertor.chargePolicyConvertor(chargePolicyTempDto);
                //If permanent table already has the charge policy then remove it
                if (getChargePolicy(chargePolicyDto.getPolicyCode()) != null)
                    deleteChargePolicy(chargePolicyDto.getPolicyCode());


                //SAVING IN PERMANENT TABLE
                saveChargePolicy(chargePolicyDto);
                //DELETE FROM TEMP TABLE
                chargePolicyTempService.deleteChargePolicy(chargePolicyTempDto.getPolicyCode());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean policyRejection(String policyCode) {
        ChargePolicyTempDto chargePolicyTempDto = chargePolicyTempService.getChargePolicy(policyCode);
        if(chargePolicyTempDto!=null) {
            //N->NR
            if (RecordStatus.N == chargePolicyTempDto.getRecordStatus()) {
                chargePolicyTempDto.setRecordStatus(RecordStatus.NR);

            } else if (RecordStatus.M == chargePolicyTempDto.getRecordStatus()) {//M->MR
                chargePolicyTempDto.setRecordStatus(RecordStatus.MR);
            } else//D->DR
            {
                chargePolicyTempDto.setRecordStatus(RecordStatus.DR);
            }
            chargePolicyTempService.updateChargePolicy(chargePolicyTempDto);//update status in temp table
            return true;
        }
        return false;
    }

}

