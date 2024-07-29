package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.controller.restcontroller.RepayScheduleCheck;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.entity.permanent.RepaySchedule;
import org.nucleus.entity.temporary.LoanApplicationTemp;
import org.nucleus.utility.dtomapper.LoanApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class LoanApplicationDAOImpl implements LoanApplicationDAO {
    private final SessionFactory sessionFactory;
    @Autowired
    private LoanApplicationMapper loanApplicationMapper;
    @Autowired
    LoanApplicationDAOImpl(SessionFactory factory){this.sessionFactory=factory;}
    @Override
    public LoanApplication getApplicationByAccountNumber(String loanAccountNumber) {
        try{
            return (LoanApplication) sessionFactory.getCurrentSession().createSQLQuery("select * from LOAN_APPLICATION_TBL_BATCH_6 where loanAccountNumber= :loanAccountNumber")
                    .setParameter("loanAccountNumber", loanAccountNumber)
                    .addEntity(LoanApplication.class)
                    .list()
                    .get(0);
        }
        catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Error while reading by application Number ", e);
        }
        return null;
    }

    @Override
    public Long create(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO==null)return null;

        LoanApplication loanApplication = loanApplicationMapper.toEntity(loanApplicationDTO);
        List<RepaySchedule> repaySchedules = RepayScheduleCheck.getRepaySchedules();
        loanApplication.setRepaySchedules(repaySchedules);
        try{
            Session session=sessionFactory.getCurrentSession();
            return (Long)session.merge(loanApplication);
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while creating loanApplication: ", e);
        }
        return null;
    }

    @Override
    public List<LoanApplicationDTO> readingAll() {
        try{
            Session session=sessionFactory.getCurrentSession();
            List<LoanApplication> loanApplications = session.createQuery("from LoanApplication", LoanApplication.class)
                    .list();

            // map entity to dto
            return loanApplications.stream()
                    .map(loanApplicationMapper::toDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while readingAll loanApplication: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public LoanApplicationDTO read(Long loanId) {
        if(loanId==null)return null;
        try{
            Session session=sessionFactory.getCurrentSession();
            LoanApplication loanApplication = session.get(LoanApplication.class, loanId);

            // map entity to dto
            return loanApplicationMapper.toDTO(loanApplication);

        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while reading loanApplication: ",e);
        }
        return null;
    }

    @Override
    public boolean merge(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO ==null)return false;

        // map dto to entity
        LoanApplication loanApplication=loanApplicationMapper.toEntity(loanApplicationDTO);
        try{
            Session session=sessionFactory.getCurrentSession();
            session.merge(loanApplication);
            return true;
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while updating loanApplication: ",e);
        }
        return false;
    }
    @Override
    public boolean merge(LoanApplication loanApplication) {
        if(loanApplication == null)
            return false;
        try{
            Session session=sessionFactory.getCurrentSession();
            session.update(loanApplication);
            return true;
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while updating loanApplication: ",e);
        }
        return false;
    }

    @Override
    public LoanApplicationDTO readByLoanApplicationId(String loanApplicationId) {
        if(loanApplicationId==null)return null;
        try{
            Session session=sessionFactory.getCurrentSession();
            LoanApplication loanApplication =
                    session.createQuery("from LoanApplication where loanApplicationId=:loanApplicationId", LoanApplication.class)
                            .setParameter("loanApplicationId", loanApplicationId)
                            .uniqueResult();

            // map entity to dto
            return loanApplicationMapper.toDTO(loanApplication);
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while reading loanApplication: ",e);
        }
        return null;
    }

    @Override
    public LoanApplicationDTO readByLoanAccountNumber(String loanAccountNumber) {
        if(loanAccountNumber==null)return null;
        try{
            Session session=sessionFactory.getCurrentSession();
            LoanApplication loanApplication =
                    session.createQuery("from LoanApplication where loanAccountNumber=:loanAccountNumber", LoanApplication.class)
                            .setParameter("loanAccountNumber", loanAccountNumber)
                            .uniqueResult();

            // map entity to dto
            return loanApplicationMapper.toDTO(loanApplication);
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while reading loanApplication by loanAccountNumber: ", e);
        }
        return null;
    }

    @Override
    public boolean update(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO ==null)return false;

        // map dto to entity
        LoanApplication loanApplication = loanApplicationMapper.toEntity(loanApplicationDTO);
        try{
            Session session=sessionFactory.getCurrentSession();
            session.update(loanApplication);
            return true;
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while updating loanApplication: ", e);
        }
        return false;
    }

    @Override
    public boolean delete(String loanApplicationID) {
        if(loanApplicationID ==null)return false;

        try{
            Session session=sessionFactory.getCurrentSession();
            int cnt = session.createQuery("delete from LoanApplication where loanApplicationId = :longApplicationID", LoanApplication.class)
                    .setParameter("longApplicationID", loanApplicationID)
                    .executeUpdate();
            return cnt>1;
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Exception occurred while deleting loanApplication using longApplicationID: ", e);
        }
        return false;
    }

    @Override
    public LoanApplication getLoanIdAgainstApplication(String loanApplicationId) {
        try{
            return (LoanApplication)sessionFactory.getCurrentSession().createQuery("from LoanApplication where loanApplicationId=:loanApplicationId")
                    .setParameter("loanApplicationId", loanApplicationId)
                    .getSingleResult();
        }
        catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("Error while reading by application id ", e);
        }
        return null;
    }
    @Override
    public LoanApplicationTemp getLoanApplicationAgainstLoanId(Long loanId) {

        Session session= sessionFactory.getCurrentSession();
        try {
            return session.get(LoanApplicationTemp.class, loanId);
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationDAOImpl.class).error("No loanApplication found against LoanID");
        }
        return null;
    }
}
