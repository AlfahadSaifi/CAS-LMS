package org.nucleus.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.dao.EligibilityPolicyMakerDAO;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDAOMapper;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDTOMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.validations.EligibilityPolicyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class EligibilityPolicyMakerServiceImpl implements EligibilityPolicyMakerService {
    private static final Logger log = LogManager.getLogger(EligibilityPolicyMakerServiceImpl.class);
    private String nullString = "Eligibility Policy is null";
    private EligibilityPolicyMakerDAO makerDAO;

    @Autowired
    public EligibilityPolicyMakerServiceImpl(EligibilityPolicyMakerDAO makerDAO) {
        this.makerDAO = makerDAO;
    }
    @Autowired
    private EligibilityPolicyTempDTOMapper policyDTOMapper;
    @Autowired
    private EligibilityPolicyTempDAOMapper policyDAOMapper;
    @Autowired
    private EligibilityPolicyCheckerService eligibilityPolicyCheckerService;
    @Override
    public boolean insertEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPoliciesDTO) {
        if (eligibilityPoliciesDTO == null) {
            log.info(nullString);
            return false;
        }
        if (eligibilityPoliciesDTO.getMetaData()==null){
            eligibilityPoliciesDTO.setMetaData(new TempMetaData());
        }
        if ( eligibilityPoliciesDTO.getId()==null) {
            eligibilityPoliciesDTO.setFlag(RecordStatus.N);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication==null){
                log.info("User is not authorised");
                return false;
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.info(userDetails);
            eligibilityPoliciesDTO
                    .getMetaData().setCreatedBy(userDetails.getUsername());
            eligibilityPoliciesDTO
                    .getMetaData().setCreationDate(Date.valueOf(LocalDate.now()));
        }
        return makerDAO.insertEligibilityPolicy(eligibilityPoliciesDTO);
    }

    @Override
    public boolean deleteEligibilityPolicy(Long policyId,String flag) {
        if (policyId == null) {
            log.info("Given policy code is null");
            return false;
        }
        else {
            if ("A".equalsIgnoreCase(flag)){
                EligibilityPolicyTempDTO eligibilityPolicyTempDTO=eligibilityPolicyCheckerService.getEligibilityPolicy(policyId);
                eligibilityPolicyTempDTO.setFlag(RecordStatus.D);
                return insertEligibilityPolicy(eligibilityPolicyTempDTO);
            }
            else if("D".equalsIgnoreCase(flag)){
                log.info("Ise delete nhi krna");
                return true;
            }else {
                return makerDAO.deleteEligibilityPolicy(policyId);
            }
        }
    }

    @Override
    public boolean deleteEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicyTempDTO) {
        if (eligibilityPolicyTempDTO==null){
            log.info(nullString);
            return false;
        }
        else {
            return makerDAO.deleteEligibilityPolicy(eligibilityPolicyTempDTO);
        }
    }

    @Override
    public boolean deleteEligibilityPolicyById(Long policyId) {
        if (policyId==null)
            return false;
        return makerDAO.deleteEligibilityPolicyById(policyId);
    }

    @Override
    public boolean updateEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPoliciesDTO) {
        if (eligibilityPoliciesDTO == null) {
            log.info(nullString);
            return false;
        }
        String flag= String.valueOf(eligibilityPoliciesDTO.getFlag());
        log.info(flag);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            log.info("User is not authorised");
            return false;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles=new ArrayList<>();
        if (authorities==null || authorities.isEmpty())
        {
            log.info("User with no authority was not in our assumption");
            return false;
        }
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            roles.add(role);
        }

        if (eligibilityPoliciesDTO.getMetaData()==null){
            eligibilityPoliciesDTO.setMetaData(new TempMetaData());
        }
        if (flag.equalsIgnoreCase("A")) {
            eligibilityPoliciesDTO.setFlag(RecordStatus.M);
            if (userDetails!=null) {
                eligibilityPoliciesDTO.getMetaData().setModifiedBy(userDetails.getUsername());
            }
            eligibilityPoliciesDTO.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            return insertEligibilityPolicy(eligibilityPoliciesDTO);
        }
        if (roles.contains("ROLE_MAKER")){ //Assuming one user has only one role
            eligibilityPoliciesDTO.getMetaData().setModifiedBy(userDetails.getUsername());
            eligibilityPoliciesDTO.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            if (flag.equalsIgnoreCase("MR")){
                eligibilityPoliciesDTO.setFlag(RecordStatus.M);
            }
            else {
                if (flag.equalsIgnoreCase("NR")) {
                    eligibilityPoliciesDTO.setFlag(RecordStatus.N);
                }
            }
        }
        log.info(eligibilityPoliciesDTO.getFlag());
        return makerDAO.updateEligibilityPolicy(eligibilityPoliciesDTO);

    }



    public boolean updateEligibilityPolicy(Long policyId, String flag) {
        if (policyId==null)
            return false;
        EligibilityPolicyTempDTO eligibilityPoliciesDTO=getEligibilityPolicy(policyId);
        eligibilityPoliciesDTO.setFlag(RecordStatus.valueOf(flag));
        if (eligibilityPoliciesDTO.getMetaData()==null){
            eligibilityPoliciesDTO.setMetaData(new TempMetaData());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            log.info("User is not authorised");
            return false;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        eligibilityPoliciesDTO.getMetaData().setModifiedBy(userDetails.getUsername());
        eligibilityPoliciesDTO.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
        return makerDAO.updateEligibilityPolicy(eligibilityPoliciesDTO);
    }

    @Override
    public EligibilityPolicyTempDTO getEligibilityPolicy(Long policyId) {
        if (policyId==null){
            return null;
        }
            return makerDAO.getEligibilityPolicyDTO(policyId);
    }

    @Override
    public List<EligibilityPolicyTemp> viewAllEligibilityPolicy() {
        List<EligibilityPolicyTemp> list = makerDAO.viewAllEligibilityPolicy();
        if (list==null||list.isEmpty()) {
            log.info("List is empty");
            return Collections.emptyList();
        }
        return list;
    }
}
