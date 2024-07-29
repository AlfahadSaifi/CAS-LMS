package org.nucleus.dao;
import org.apache.logging.log4j.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.entity.permanent.LoanClosure;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.utility.dtomapper.LoanClosureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Repository
@Transactional
public class LoanClosureDAOImpl implements LoanClosureDAO{
    @Autowired
    SessionFactory sessionFactory;
    Session session;

    //function to save loanClosure in Permanent Table
    @Override
    public boolean save(LoanClosureDTO loanClosureDTO) {
        try {
            session = sessionFactory.getCurrentSession();
            session.save(LoanClosureMapper.toPermanentEntity(loanClosureDTO));
            return true;
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
            return false;
        }
    }


    //function to add loanClosures in batch inside Permanent Table
    @Override
    public boolean addData(List<LoanClosureDTO> loanClosureDTOList, int batchSize) {
        try {
            session = sessionFactory.getCurrentSession();
            loanClosureDTOList.forEach(loanClosureDTO->{
                session.save(LoanClosureMapper.toPermanentEntity(loanClosureDTO)); // Queueing the save operation
            });
            return true;
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
            return false;
        }
    }

    //function to get all loanClosures that are normally closed from Permanent Table
    @Override
    public List<LoanClosureDTO> getAllRegularClosureData() {
        try {
            session = sessionFactory.getCurrentSession();
            List<LoanClosure> loanClosureTemps = session.createQuery("From LoanClosure where closureStatus='REGULAR_CLOSURE'").getResultList();
            return loanClosureTemps.stream()
                    .map(LoanClosureMapper::toPermanentDTO)
                    .collect(Collectors.toList());
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
        }
        return Collections.emptyList();
    }

    // function to update loanClosureDTO in permanent table
    @Override
    public boolean update(LoanClosureDTO loanClosureDTO) {
        try {
            session = sessionFactory.getCurrentSession();
            session.update(LoanClosureMapper.toPermanentEntity(loanClosureDTO));
            return true;
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
            return false;
        }
    }


    //Function to delete loanClosure from permanent table using loanClosureId
    @Override
    public boolean delete(Long closureLoanId) {
        try {
            session = sessionFactory.getCurrentSession();
            LoanClosureDTO loanClosureDTO=LoanClosureMapper.toPermanentDTO(session.get(LoanClosure.class, closureLoanId));
            loanClosureDTO.setLoanAccountDTO(null);
            session.delete(LoanClosureMapper.toPermanentEntity(loanClosureDTO));
            return true;
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
            return false;
        }
    }


    //function to delete whole loanClosureEntity from Permanent Table
    @Override
    public boolean delete(LoanClosureDTO loanClosureDTO) {
        try {
            session = sessionFactory.getCurrentSession();
            loanClosureDTO.setLoanAccountDTO(null);
            session.delete(LoanClosureMapper.toTemporaryEntity(loanClosureDTO));
            return true;
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
            return false;
        }

    }

    //function to fetch all the existing data with any record status from Loan Closure Table.
    @Override
    public List<LoanClosureDTO> getAllLoanClosureDataPermanentTable() {
        try {
            session = sessionFactory.getCurrentSession();
            List<LoanClosure> loanClosurePer = session.createQuery("From LoanClosure").getResultList();
            return loanClosurePer.stream()
                    .map(LoanClosureMapper::toPermanentDTO)
                    .collect(Collectors.toList());
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
        }
        return Collections.emptyList();
    }


    //function to get Loan Closure Data against the provided loanClosureId
    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public LoanClosureDTO getLoanClosureDetailPer(Long closureLoanId) {
        try {
            session = sessionFactory.getCurrentSession();
            return LoanClosureMapper.toPermanentDTO(session.get(LoanClosure.class, closureLoanId));
        }
        catch (HibernateException | NullPointerException e){
            LogManager.getLogger().error(e.getMessage());
            return null;
        }
    }
}