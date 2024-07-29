package org.nucleus.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.entity.permanent.ReceivablePayable;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.utility.dtomapper.ReceivablePayableDTOToReceivablePayable;

import org.nucleus.utility.dtomapper.ReceivablePayableToReceivablePayableDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ReceivablePayableDaoImpl implements ReceivablePayableDao{
    private static final Logger logger = LogManager.getLogger(ReceivablePayableDaoImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    ReceivablePayableDTOToReceivablePayable receivablePayableDTOToReceivablePayable = new ReceivablePayableDTOToReceivablePayable();
    ReceivablePayableToReceivablePayableDTO receivablePayableToReceivablePayableDTO = new ReceivablePayableToReceivablePayableDTO();
    @Override
    public boolean insertReceivablePayable(ReceivablePayableDTO receivablePayableDto) {
        ReceivablePayable receivablePayable = receivablePayableDTOToReceivablePayable.mapObjectToReceivablePayable(receivablePayableDto);
        if(receivablePayable==null)
            return false;
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(receivablePayable);
            logger.info("receivable payable inserted successfully in permanent table");
            return true;
        } catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    @Override
    public boolean updateReceivablePayable(ReceivablePayableDTO receivablePayableDto) {
        Session session = sessionFactory.getCurrentSession();
        ReceivablePayable receivablePayable = receivablePayableDTOToReceivablePayable.mapObjectToReceivablePayable(receivablePayableDto);
        session.update(receivablePayable);
        return true;
    }
    @Override
    public boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayableDto) {
        ReceivablePayable receivablePayable = receivablePayableDTOToReceivablePayable.mapObjectToReceivablePayable(receivablePayableDto);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(receivablePayable);
            logger.info("receivable payable deleted successfully from permanent table");
            return true;
        } catch (Exception e){
            logger.error(e.getMessage()+" in permanent table");
            return false;
        }
    }
    @Override
    public ReceivablePayableDTO getReceivablePayable(Long receivablePayableId) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            ReceivablePayable receivablePayable = session.get(ReceivablePayable.class,receivablePayableId);
            Hibernate.initialize(receivablePayable.getLoanAccount());

            ReceivablePayableDTO receivablePayableDTO = receivablePayableToReceivablePayableDTO.mapObjectToreceivablePayableDTO(receivablePayable);
            System.out.println("asdf"+receivablePayableDTO.getLoanAccount());
            return receivablePayableDTO;
        } catch (Exception e){
            logger.error(e.getMessage()+" in permanent table");
            return null;
        }
    }
    @Override
    public List<ReceivablePayableDTO> getAllReceivablePayable() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<ReceivablePayable> receivablePayables = session.createQuery("from ReceivablePayable",ReceivablePayable.class).list();
            return receivablePayables.stream().map(n->receivablePayableToReceivablePayableDTO.mapObjectToreceivablePayableDTO(n)).collect(Collectors.toList());
        } catch (Exception e){
            logger.error("no receivable payable entries found in the permanent table");
            return null;
        }
    }


    @Override
    public List<ReceivablePayableDTO> getReceivablePayableBYLoanAccId(Long loanAccountId) {
        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            String hql = "from ReceivablePayable where loanAccountId =?1";
            List<ReceivablePayable> receivablePayables = session.createQuery(hql, ReceivablePayable.class).setParameter(1, loanAccountId).getResultList();
            session.getTransaction().commit();
            return receivablePayables.stream().map(n->receivablePayableToReceivablePayableDTO.mapObjectToreceivablePayableDTO(n)).collect(Collectors.toList());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            return null;
        }
    }
}