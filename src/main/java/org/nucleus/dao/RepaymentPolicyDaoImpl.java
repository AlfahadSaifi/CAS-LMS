package org.nucleus.dao;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.permanent.RepaymentPolicy;
import org.nucleus.exception.RepaymentPolicyCodeDuplicationExcaption;
import org.nucleus.utility.dtomapper.RepaymentPolicyDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepaymentPolicyDaoImpl implements RepaymentPolicyDao{
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean insertRepaymentPolicy(RepaymentPolicyDTO repaymentPolicyDTO)throws RepaymentPolicyCodeDuplicationExcaption {
        RepaymentPolicy repaymentPolicy= RepaymentPolicyDtoMapper.dtoToMasterConvertor(repaymentPolicyDTO);
        if(getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode())==null) {
            try  {
                Session session = sessionFactory.getCurrentSession();
//                session.beginTransaction();
                session.save(repaymentPolicy);
//                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                LogManager.getLogger(RepaymentPolicyDaoImpl.class).error("Repayment Policy Dao -", ex);
                return false;
            }
        }
        throw new RepaymentPolicyCodeDuplicationExcaption("policy already existed");
    }
    @Override
    public boolean updateRepaymentPolicy(RepaymentPolicyDTO repaymentPolicyDTO) {
        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            RepaymentPolicy repaymentPolicy=RepaymentPolicyDtoMapper.dtoToMasterConvertor(repaymentPolicyDTO);
            long id = session.createQuery("select repaymentPolicyId FROM RepaymentPolicy  WHERE repaymentPolicyCode = :code", Long.class)
                    .setParameter("code",repaymentPolicy.getRepaymentPolicyCode())
                    .uniqueResult();

            repaymentPolicy.setRepaymentPolicyId(id);
            session.update(repaymentPolicy);
//            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            LogManager.getLogger(RepaymentPolicyDaoImpl.class).error("Repayment Policy Dao -", ex);
            return false;
        }
    }
    @Override
    public boolean deleteRepaymentPolicy(String code) {
        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            RepaymentPolicy repaymentPolicy= session.createQuery(" FROM RepaymentPolicy  WHERE repaymentPolicyCode = :code", RepaymentPolicy.class)
                    .setParameter("code",code)
                    .uniqueResult();
            session.delete(repaymentPolicy);
//            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            LogManager.getLogger(RepaymentPolicyDaoImpl.class).error("Repayment Policy Dao -", ex);
            return false;
        }
    }

    @Override
    public RepaymentPolicyDTO getRepaymentPolicyById(String id) {
        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            List<RepaymentPolicy>repaymentPolicies= session.createQuery("from RepaymentPolicy where repaymentPolicyCode = ?1",RepaymentPolicy.class )
                    .setParameter(1, id).getResultList();
//            session.getTransaction().commit();

            System.out.println("ayush1" + repaymentPolicies.get(0));
            System.out.println("ayush2" + repaymentPolicies.get(0).getRepaymentPolicyCode());

            if(repaymentPolicies!=null && repaymentPolicies.size()>0)
            {
               return  repaymentPolicies.stream()
                        .map(RepaymentPolicyDtoMapper::masterToDTOConvertor)
                        .collect(Collectors.toList()).get(0);
            }

        } catch (Exception ex) {
            LogManager.getLogger(RepaymentPolicyDaoImpl.class).error("Repayment Policy Dao -", ex);
            return null;

        }
        return null;
    }

    @Override
    public RepaymentPolicy getRepayPolicyByCode(String code){
        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            List<RepaymentPolicy>repaymentPolicies= session.createQuery("from RepaymentPolicy where repaymentPolicyCode = ?1",RepaymentPolicy.class )
                    .setParameter(1, code).getResultList();
            return repaymentPolicies.get(0);
        } catch (Exception ex) {
            LogManager.getLogger(RepaymentPolicyDaoImpl.class).error("Repayment Policy Dao -", ex);
        }
        return null;
    }
    @Override
    public List<RepaymentPolicyDTO> getAllRepaymentPolicy() {
        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            List<RepaymentPolicy>repaymentPolicies= session.createQuery("from RepaymentPolicy", RepaymentPolicy.class).list();
            if(repaymentPolicies !=null && repaymentPolicies.size()>0)
            {
                return repaymentPolicies.stream()
                        .map(RepaymentPolicyDtoMapper::masterToDTOConvertor)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            LogManager.getLogger(RepaymentPolicyDaoImpl.class).error("Repayment Policy Dao -", ex);
            return null;
        }
        return null;
    }
}
