package org.nucleus.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.entity.permanent.JasperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDao {
    private static final Logger logger = LogManager.getLogger(EmployeeDAOImpl.class);
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    public EmployeeDAOImpl(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    @Override
    public List<JasperEntity> getAllEntries() {
        List<JasperEntity> result;
        Session session = sessionFactory.getCurrentSession();
        result = session.createQuery("from JasperEntity", JasperEntity.class).getResultList();
        return result;
    }

    @Override
    public JasperEntity getEntry(String loanAccountNumber) {
        JasperEntity result;
        Session session = sessionFactory.getCurrentSession();
        result = session.get(JasperEntity.class, loanAccountNumber);
        return result;
    }

    @Override
    public void saveEmployee(JasperEntity jasperEntity) {
        try (Session session = sessionFactory.openSession()) {
            if (session != null) {
                session.beginTransaction();
                session.merge(jasperEntity);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
