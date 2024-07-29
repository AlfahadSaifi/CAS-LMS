package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.temporary.RepaymentPolicyTemp;
import org.nucleus.exception.RepaymentPolicyCodeDuplicationExcaption;
import org.nucleus.utility.dtomapper.RepaymentPolicyDtoMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepaymentPolicyTempDaoImpl implements RepaymentPolicyTempDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean insertRepaymentPolicy(RepaymentPolicyDTO repaymentPolicyDTO) throws RepaymentPolicyCodeDuplicationExcaption {
        if(getRepaymentPolicyById(repaymentPolicyDTO.getRepaymentPolicyCode())==null)
        {
            RepaymentPolicyTemp repaymentPolicy= RepaymentPolicyDtoMapper.dtoToTempConvertor(repaymentPolicyDTO);
            try  {
                Session session = sessionFactory.getCurrentSession();
//                session.beginTransaction();
                session.save(repaymentPolicy);
//                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                LogManager.getLogger().error("Repayment Policy Temp Dao -", ex);
                return false;
            }
        }
        throw  new RepaymentPolicyCodeDuplicationExcaption("policy already existed");
    }
    @Override
    public boolean updateRepaymentPolicy(RepaymentPolicyDTO repaymentPolicyDTO) {
        RepaymentPolicyTemp repaymentPolicy=RepaymentPolicyDtoMapper.dtoToTempConvertor(repaymentPolicyDTO);
        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            long id = session.createQuery("select repaymentPolicyId FROM RepaymentPolicyTemp  WHERE repaymentPolicyCode = :code", Long.class)
                    .setParameter("code",repaymentPolicy.getRepaymentPolicyCode())
                    .uniqueResult();

            repaymentPolicy.setRepaymentPolicyId(id);
            System.out.println("Sumit "+repaymentPolicy);
            session.merge(repaymentPolicy);
//            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            LogManager.getLogger().error("Repayment Policy Temp Dao -", ex);
            return false;

        }

    }
    @Override
    public boolean deleteRepaymentPolicy(String code) {
        System.out.println(code);
        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            RepaymentPolicyTemp repaymentPolicy= session.createQuery(" FROM RepaymentPolicyTemp  WHERE repaymentPolicyCode = :code", RepaymentPolicyTemp.class)
                    .setParameter("code",code)
                    .uniqueResult();
            if (repaymentPolicy==null){
                return false;
            }

            session.delete(repaymentPolicy);
//            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            LogManager.getLogger().error("Repayment Policy Temp Dao -", ex);
            return false;

        }

    }

    @Override
    public RepaymentPolicyDTO getRepaymentPolicyById(String id) {

        try  {
            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
            System.out.println();
           List<RepaymentPolicyTemp> repaymentPolicies= session.createQuery("from RepaymentPolicyTemp where repaymentPolicyCode = ?1",RepaymentPolicyTemp.class )
                   .setParameter(1, id).getResultList();
            System.out.println("get By Id ");
           if(repaymentPolicies!=null && repaymentPolicies.size()>0){

               List<RepaymentPolicyDTO> repaymentPolicyTemps=repaymentPolicies.stream()
                       .map(RepaymentPolicyDtoMapper::tempToDTOConvertor)
                       .collect(Collectors.toList());
               return repaymentPolicyTemps.get(0);
           }
           return null;
        }
        catch(Exception ex)
        {
            LogManager.getLogger().error("Repayment Policy Temp Dao -", ex);
            return null;

        }


    }
    @Override
    public List<RepaymentPolicyDTO> getAllRepaymentPolicy() {
        try  {
            Session session = sessionFactory.getCurrentSession();
            List<RepaymentPolicyTemp> repaymentPolicies= session.createQuery("from RepaymentPolicyTemp", RepaymentPolicyTemp.class).list();
            if(repaymentPolicies != null && !repaymentPolicies.isEmpty()) {
                return repaymentPolicies.stream()
                        .filter(policy -> policy.getMetaData().getRecordStatus() == RecordStatus.M || policy.getMetaData().getRecordStatus() == RecordStatus.N ||policy.getMetaData().getRecordStatus()==RecordStatus.D)
                        .map(RepaymentPolicyDtoMapper::tempToDTOConvertor)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            LogManager.getLogger().error("Repayment Policy Temp Dao -", ex);
        }
        return Collections.emptyList();
    }

    @Override
    public RepaymentPolicyDTO checkSave() {
        try  {
            Session session = sessionFactory.getCurrentSession();

//            session.getTransaction();
            TypedQuery<RepaymentPolicyTemp> query = session.createQuery("FROM RepaymentPolicyTemp rpt WHERE rpt.metaData.saveFlag = 1", RepaymentPolicyTemp.class);
            RepaymentPolicyDTO repaymentPolicyDTO=RepaymentPolicyDtoMapper.tempToDTOConvertor(query.getSingleResult());
            return repaymentPolicyDTO;
        } catch (NoResultException e) {
        }
        catch (HibernateException e) {
        }
        return null;
    }

    @Override
    public List<RepaymentPolicyDTO> getAllRepaymentPolicyRejeted() {
        try  {
            Session session = sessionFactory.getCurrentSession();
            List<RepaymentPolicyTemp> repaymentPolicies= session.createQuery("from RepaymentPolicyTemp", RepaymentPolicyTemp.class).list();
            if(repaymentPolicies != null && !repaymentPolicies.isEmpty()) {
                return repaymentPolicies.stream()
                        .filter(policy -> policy.getMetaData().getRecordStatus() == RecordStatus.MR || policy.getMetaData().getRecordStatus() == RecordStatus.NR || policy.getMetaData().getRecordStatus() == RecordStatus.DR)
                        .map(RepaymentPolicyDtoMapper::tempToDTOConvertor)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            LogManager.getLogger().error("Repayment Policy Temp Dao -", ex);
        }
        return Collections.emptyList();
    }

}
