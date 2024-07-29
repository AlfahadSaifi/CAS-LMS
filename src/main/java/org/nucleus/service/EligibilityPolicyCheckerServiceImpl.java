package org.nucleus.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.dao.EligibilityPolicyCheckerDao;
import org.nucleus.dao.EligibilityPolicyMakerDAO;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.permanent.EligibilityPolicy;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDAOMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.temptoperm.EligibilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.nucleus.utility.enums.RecordStatus.A;

@Service
@Transactional
public class EligibilityPolicyCheckerServiceImpl implements EligibilityPolicyCheckerService {
    private static final Logger log = LogManager.getLogger(EligibilityPolicyCheckerServiceImpl.class);
    String nullString = "eligibilityPolicies is null";
    private EligibilityPolicyCheckerDao checkerDao;
    @Autowired
    private EligibilityPolicyMakerService makerService;
    private EligibilityPolicyMakerDAO makerDAO;
    @Autowired
    private EligibilityPolicyTempDAOMapper eligibilityPolicyTempDAOMapper;
    @Autowired
    private EligibilityMapper eligibilityTempToPerm;

    @Autowired
    public EligibilityPolicyCheckerServiceImpl(EligibilityPolicyCheckerDao checkerDao,EligibilityPolicyMakerDAO makerDAO) {
        this.checkerDao = checkerDao;
        this.makerDAO = makerDAO;
    }

    @Override
    public boolean approveEligibilityPolicy(EligibilityPolicyTemp eligibilityPoliciesTemp) {
        if (eligibilityPoliciesTemp == null) {
            log.info(nullString);
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info(userDetails);
        eligibilityPoliciesTemp
                .getMetaData().setAuthorizedBy(userDetails.getUsername());
        eligibilityPoliciesTemp
                .getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
        if(eligibilityPoliciesTemp.getMetaData().getRecordStatus().toString().equalsIgnoreCase("M")){
            log.info("Inside if condition of M");
            EligibilityPolicy policiesMst = EligibilityMapper.convertor(eligibilityPoliciesTemp);
            policiesMst.getMetaData().setRecordStatus(A);
            log.info(policiesMst);
            policiesMst.setEligibilityPolicyId(getEligibilityPolicyByCode(policiesMst.getEligibilityPolicyCode()).getId());
            checkerDao.updateEligibilityPolicy(policiesMst);
            makerService.deleteEligibilityPolicy(eligibilityPoliciesTemp.getEligibilityPolicyId(),null);
            return true;

        }
        else if(eligibilityPoliciesTemp.getMetaData().getRecordStatus().toString().equalsIgnoreCase("D")) {
            log.info("Inside if condition of D");
            EligibilityPolicy policiesMst = EligibilityMapper.convertor(eligibilityPoliciesTemp);
            log.info(policiesMst);
            policiesMst.setEligibilityPolicyId(getEligibilityPolicyByCode(policiesMst.getEligibilityPolicyCode()).getId());
            log.info("policy id after fecthing"+policiesMst.getEligibilityPolicyId());
            checkerDao.deleteEligibilityPolicy(policiesMst.getEligibilityPolicyId());
            makerService.deleteEligibilityPolicy(eligibilityPoliciesTemp.getEligibilityPolicyId(),null);
            return true;
        }
        log.info("Not in if");
        EligibilityPolicy policiesMst = EligibilityMapper.convertor(eligibilityPoliciesTemp);
        log.info("Before policy mst");
        log.info(policiesMst);
        policiesMst.getMetaData().setRecordStatus(A);
        checkerDao.approveEligibilityPolicy(policiesMst);
        makerService.deleteEligibilityPolicy(eligibilityPoliciesTemp.getEligibilityPolicyId(),null);
        return true;
    }

    @Override
    public boolean rejectEligibilityPolicy(EligibilityPolicyTemp eligibilityPoliciesTemp) {
        if (eligibilityPoliciesTemp == null) {
            log.info(nullString);
            return false;
        }
        if (eligibilityPoliciesTemp.getMetaData().getRecordStatus().toString().equalsIgnoreCase("N")) {
            eligibilityPoliciesTemp.getMetaData().setRecordStatus(RecordStatus.NR);
            return makerService.updateEligibilityPolicy(eligibilityPolicyTempDAOMapper.mapPolicy(eligibilityPoliciesTemp));
        }
        if (eligibilityPoliciesTemp.getMetaData().getRecordStatus().toString().equalsIgnoreCase("M")) {
            eligibilityPoliciesTemp.getMetaData().setRecordStatus(RecordStatus.MR);
            makerService.updateEligibilityPolicy(eligibilityPolicyTempDAOMapper.mapPolicy(eligibilityPoliciesTemp));
            eligibilityPoliciesTemp.getMetaData().setRecordStatus(A);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.info(userDetails);
            eligibilityPoliciesTemp
                    .getMetaData().setAuthorizedBy(userDetails.getUsername());
            eligibilityPoliciesTemp
                    .getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
            EligibilityPolicy policiesMst = EligibilityMapper.convertor(eligibilityPoliciesTemp);
            return checkerDao.approveEligibilityPolicy(policiesMst);
        }

        if (eligibilityPoliciesTemp.getMetaData().getRecordStatus().toString().equalsIgnoreCase("D")) {
            eligibilityPoliciesTemp.getMetaData().setRecordStatus(RecordStatus.DR);
            return makerService.updateEligibilityPolicy(eligibilityPolicyTempDAOMapper.mapPolicy(eligibilityPoliciesTemp));
        }
        log.info("No Status is selected");
        return false;
    }

    @Override
    public boolean updateEligibilityPolicy(EligibilityPolicyTemp eligibilityPoliciesTemp) {
        if (eligibilityPoliciesTemp == null) {
            log.info(nullString);
            return false;
        }
        return true;
    }


    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<EligibilityPolicy> viewAllEligibilityPolicyFiltered() {
        List<EligibilityPolicy> eligibilityPolicies = new ArrayList<>();
        boolean flag=false;
        for (EligibilityPolicy eligibilityPolicy : checkerDao.viewAllEligibilityPolicy()) {
            for (EligibilityPolicyTemp eligibilityPolicyTemp: makerService.viewAllEligibilityPolicy()){
                if (eligibilityPolicyTemp.getEligibilityPolicyCode().equalsIgnoreCase(eligibilityPolicy.getEligibilityPolicyCode())){
                    flag=true;
                    break;
                }
            }
            if (!flag){
                eligibilityPolicies.add(eligibilityPolicy);
            }
            flag=false;
        }
        return eligibilityPolicies;
    }
    @Override
    public List<EligibilityPolicy> viewAllEligibilityPolicy() {
        return checkerDao.viewAllEligibilityPolicy();
    }
    @Override
    public EligibilityPolicyTempDTO getEligibilityPolicy(Long policyId){
        return checkerDao.getEligibilityPolicy(policyId);
    }
    @Override
    public EligibilityPolicyTempDTO getEligibilityPolicyByCode(String policyCode) {
         return checkerDao.getEligibilityPolicyByCode(policyCode);
    }

}
