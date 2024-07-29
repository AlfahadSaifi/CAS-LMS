package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.permanent.EligibilityPolicy;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDAOMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.nucleus.utility.temptoperm.EligibilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class EligibilityPolicyCheckerDAOImpl implements EligibilityPolicyCheckerDao {
    private static final Logger log = LogManager.getLogger(EligibilityPolicyCheckerDAOImpl.class);
    private SessionFactory sessionFactory;
    @Autowired
    private EligibilityPolicyTempDAOMapper eligibilityPolicyTempDAOMapper;

    @Autowired
    public EligibilityPolicyCheckerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean approveEligibilityPolicy(EligibilityPolicy eligibilityPoliciesMst) {
        log.info("approveEligibilityPolicy is called");
        eligibilityPoliciesMst.getMetaData().setRecordStatus(RecordStatus.A);
        Session session = sessionFactory.getCurrentSession();
        try {
            log.info("inside try");
            session.merge(eligibilityPoliciesMst);
            log.info(eligibilityPoliciesMst);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateEligibilityPolicy(EligibilityPolicy eligibilityPolicy) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.merge(eligibilityPolicy);
            log.info(eligibilityPolicy);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateEligibilityPolicyByPolicyCode(String policyCode) {
        return false;
    }

    @Override
    public EligibilityPolicyTempDTO getEligibilityPolicy(Long policyId) {
        Session session = sessionFactory.getCurrentSession();
        EligibilityPolicy policy=null;
        try {
            policy = session.get(EligibilityPolicy.class, policyId);
                return eligibilityPolicyTempDAOMapper.mapPolicy(EligibilityMapper.convertorTemp(policy));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public EligibilityPolicyTempDTO getEligibilityPolicyByCode(String policyCode) {
        Session session = sessionFactory.getCurrentSession();
        List<EligibilityPolicy> policies;
        try {
            policies = session.createQuery("from EligibilityPolicy where eligibilityPolicyCode=?1",EligibilityPolicy.class)
                    .setParameter(1,policyCode)
                    .getResultList();
            if (policies==null||policies.isEmpty()){
                return null;
            }
            else {
                return eligibilityPolicyTempDAOMapper.mapPolicy(EligibilityMapper.convertorTemp(policies.get(0)));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean rejectEligibilityPolicy(EligibilityPolicy eligibilityPoliciesMst) {
        return false;
    }

    @Override
    public boolean deleteEligibilityPolicy(Long eligibilityPolicyId) {
        log.info("inside deleteEligibilityPolicy id");
        Session session = sessionFactory.getCurrentSession();
        int n;
        try {
            session.createNativeQuery("delete from ELIGIBILITY_POLICY_PARAMETER_TBL_BATCH_6 where eligibilityPolicyId='" + eligibilityPolicyId + "'").executeUpdate();
            n = session.createNativeQuery("delete from ELIGIBILITY_POLICY_TBL_BATCH_6 where eligibilityPolicyId='" + eligibilityPolicyId + "'").executeUpdate();
            return n > 0;

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }


    @Override
    public List<EligibilityPolicy> viewAllEligibilityPolicy() {
        Session session = sessionFactory.getCurrentSession();
        List<EligibilityPolicy> policiesList = session.createQuery("from EligibilityPolicy", EligibilityPolicy.class).getResultList();
        if (policiesList.isEmpty()) {
            log.info("No policy found.");
            return Collections.emptyList();
        } else {
            return policiesList;
        }
    }
}
