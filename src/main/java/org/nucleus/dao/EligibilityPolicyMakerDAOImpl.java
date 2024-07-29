package org.nucleus.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDAOMapper;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Repository
public class EligibilityPolicyMakerDAOImpl implements EligibilityPolicyMakerDAO {
    private static final Logger log = LogManager.getLogger(EligibilityPolicyMakerDAOImpl.class);
    private SessionFactory sessionFactory;
    private EligibilityPolicyTempDTOMapper eligibilityPolicyDTOMapper;
    private EligibilityPolicyTempDAOMapper eligibilityPolicyDAOMapper;

    @Autowired
    public EligibilityPolicyMakerDAOImpl(SessionFactory sessionFactory, EligibilityPolicyTempDTOMapper eligibilityPolicyDTOMapper, EligibilityPolicyTempDAOMapper eligibilityPolicyDAOMapper) {
        this.sessionFactory = sessionFactory;
        this.eligibilityPolicyDTOMapper = eligibilityPolicyDTOMapper;
        this.eligibilityPolicyDAOMapper = eligibilityPolicyDAOMapper;
    }

    @Override
    public boolean insertEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicies) {
        if (eligibilityPolicies==null)
            return false;
        EligibilityPolicyTemp eligibilityPolicyTemp = eligibilityPolicyDTOMapper.mapPolicy(eligibilityPolicies);
//        System.out.println("rthyu");
        Session session = sessionFactory.getCurrentSession();
        log.info("save tk to aaya");
        try {
            session.merge(eligibilityPolicyTemp);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;

    }

    @Override
    public boolean deleteEligibilityPolicy(Long policyId) {
        if (policyId==null)
            return false;
        log.info("inside deleteEligibilityPolicy id");
        Session session = sessionFactory.getCurrentSession();
        int n=0;
        try {
            session.createNativeQuery("delete from ELIGIBILITY_POLICY_PARAMETER_TEMP_TBL_BATCH_6 where eligibilityPolicyId='" + policyId + "'").executeUpdate();
            n = session.createNativeQuery("delete from ELIGIBILITY_POLICY_TEMP_TBL_BATCH_6 where eligibilityPolicyId='" + policyId + "'").executeUpdate();
            return n > 0;

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }


    @Override
    public boolean deleteEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicyTempDTO) {
        if (eligibilityPolicyTempDTO==null)
            return false;
        log.info("inside deleteEligibilityPolicy object");
        Session session = sessionFactory.getCurrentSession();
        try {
            session.delete(eligibilityPolicyDTOMapper.mapPolicy(eligibilityPolicyTempDTO));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteEligibilityPolicyById(Long policyId) {
        if (policyId==null)
            return false;
        Session session = sessionFactory.getCurrentSession();
        try {
            EligibilityPolicyTemp policiesTempToDelete = session.get(EligibilityPolicyTemp.class, policyId);
            if (policiesTempToDelete != null) {
                session.delete(policiesTempToDelete);
                return true;
            } else {
                log.info(String.format("Policy with ID %s not found.", policyId));
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateEligibilityPolicy(EligibilityPolicyTempDTO eligibilityPolicies) {
        if(eligibilityPolicies==null)
            return false;
        Session session = sessionFactory.getCurrentSession();
        log.info("lkl");
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            EligibilityPolicyTemp eligibilityPolicyTemp = eligibilityPolicyDTOMapper.mapPolicy(eligibilityPolicies);
//            EligibilityPolicyTemp eligibilityPolicyTemp1 = getEligibilityPolicy(eligibilityPolicyTemp.getEligibilityPolicyId());
//            TempMetaData metaData = eligibilityPolicyTemp1.getMetaData();
//            TempMetaData tempMetaData = eligibilityPolicyTemp.getMetaData();
//            tempMetaData.setCreatedBy(metaData.getCreatedBy());
//            tempMetaData.setCreationDate(metaData.getCreationDate());
//            tempMetaData.setAuthorizedBy(metaData.getAuthorizedBy());
//            tempMetaData.setAuthorizedDate(metaData.getAuthorizedDate());
//            tempMetaData.setModifiedBy(userDetails.getUsername());
//            tempMetaData.setModifiedDate(Date.valueOf(LocalDate.now()));
//            eligibilityPolicyTemp.setMetaData(tempMetaData);
            session.merge(eligibilityPolicyTemp);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public EligibilityPolicyTempDTO getEligibilityPolicyDTO(Long policyId) {
        if (policyId==null)
            return null;
        EligibilityPolicyTemp eligibilityPolicyTemp = getEligibilityPolicy(policyId);
        if (eligibilityPolicyTemp == null)
            return null;
        return eligibilityPolicyDAOMapper.mapPolicy(eligibilityPolicyTemp);
    }


    @Override
    public EligibilityPolicyTemp getEligibilityPolicy(Long policyId) {
        if (policyId==null)
            return null;
        Session session = sessionFactory.getCurrentSession();
        EligibilityPolicyTemp policy = null;
        try {
            policy = session.get(EligibilityPolicyTemp.class, policyId);
            if (policy == null)
                return null;
            return policy;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    public List<EligibilityPolicyTemp> viewAllEligibilityPolicy() {
        Session session = sessionFactory.getCurrentSession();
        List<EligibilityPolicyTemp> policiesList = null;
        try {
            policiesList = session.createQuery("from EligibilityPolicyTemp", EligibilityPolicyTemp.class).getResultList();
            return policiesList;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return policiesList;
    }
}
