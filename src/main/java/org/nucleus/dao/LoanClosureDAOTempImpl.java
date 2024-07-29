package org.nucleus.dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.entity.permanent.LoanAccount;
import org.nucleus.entity.temporary.LoanClosureTemp;
import org.nucleus.utility.dtomapper.LoanAccountDTOMapper;
import org.nucleus.utility.dtomapper.LoanClosureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Repository
@Transactional
public class LoanClosureDAOTempImpl implements LoanClosureDAOTemp {
    private static final Logger logger= LogManager.getLogger(LoanClosureDAOTempImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    Session session;
    @Override
    public boolean addData(LoanClosureDTO loanClosureDTO) {
        try {
            session = sessionFactory.getCurrentSession();
            session.save(LoanClosureMapper.toTemporaryEntity(loanClosureDTO));
            return true;
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean update(LoanClosureDTO loanClosureDTO) {
        try {
            session = sessionFactory.getCurrentSession();
            session.update(LoanClosureMapper.toTemporaryEntity(loanClosureDTO));
            return true;
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return false;
    }
    @Override
    public boolean delete(LoanClosureDTO loanClosureDTO) {
        try {
            session = sessionFactory.getCurrentSession();
            loanClosureDTO.setLoanAccountDTO(null);
            session.delete(LoanClosureMapper.toTemporaryEntity(loanClosureDTO));
            return true;
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean delete(Long closureLoanId) {
        try {
            session = sessionFactory.getCurrentSession();
            LoanClosureTemp loanClosureTemp=session.get(LoanClosureTemp.class, closureLoanId);
            session.delete(loanClosureTemp);
            return true;
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return false;
    }
    @Override
    public List<LoanClosureDTO> getCheckerTempData(){
        try {
            session = sessionFactory.getCurrentSession();
            List<LoanClosureTemp> loanClosureTemps = session.createQuery("From LoanClosureTemp where recordStatus='N'").getResultList();
            return loanClosureTemps.stream()
                    .map(LoanClosureMapper::toTemporaryDTO)
                    .collect(Collectors.toList());
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return Collections.emptyList();
    }
    @Override
    public List<LoanClosureDTO> getCheckerTempDataDeleted(){
        try {
            session = sessionFactory.getCurrentSession();
            List<LoanClosureTemp> loanClosureTemps = session.createQuery("From LoanClosureTemp where recordStatus='D'").getResultList();
            return loanClosureTemps.stream()
                    .map(LoanClosureMapper::toTemporaryDTO)
                    .collect(Collectors.toList());
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return Collections.emptyList();
    }
    @Override
    public List<LoanClosureDTO> getAllLoanClosureData() {
        try {
            session = sessionFactory.getCurrentSession();
            List<LoanClosureTemp> loanClosureTemps = session.createQuery("From LoanClosureTemp where closureStatus='REGULAR_CLOSURE'").getResultList();
            return loanClosureTemps.stream()
                    .map(LoanClosureMapper::toTemporaryDTO)
                    .collect(Collectors.toList());
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return Collections.emptyList();
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public LoanClosureDTO getLoanClosureDetail(Long closureLoanId) {
        try {
            session = sessionFactory.getCurrentSession();
            LoanClosureTemp loanClosureTemp=session.get(LoanClosureTemp.class, closureLoanId);
            return LoanClosureMapper.toTemporaryDTO(loanClosureTemp);
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return null;
    }
    @Override
    public LoanClosureDTO findLoanClosureByLoanAccountId(Long loanAccountId)
    {
        try
        {
            session = sessionFactory.getCurrentSession();
            String hql = "FROM LoanClosureTemp WHERE loanId = :loanAccountId";
            Query<LoanClosureTemp> query = session.createQuery(hql,LoanClosureTemp.class);
            query.setParameter("loanAccountId", loanAccountId);
            LoanClosureTemp loanClosureTemp = query.uniqueResult();
            return LoanClosureMapper.toTemporaryDTO(loanClosureTemp);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            return null;
        }
    }
    @Override
    public Long addEarlyClosureData(LoanClosureDTO loanClosureDTO)
    {
        try {
            session = sessionFactory.getCurrentSession();
            LoanClosureTemp loanClosureTemp=LoanClosureMapper.toTemporaryEntity(loanClosureDTO);
            return (Long)session.save(loanClosureTemp);
        }
        catch (HibernateException e){
            logger.error(e);
        }
        return null;
    }
}