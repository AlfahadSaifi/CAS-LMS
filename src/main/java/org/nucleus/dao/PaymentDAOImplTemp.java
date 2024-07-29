package org.nucleus.dao;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.PaymentDTO;
import org.nucleus.entity.temporary.PaymentTemp;
import org.nucleus.utility.dtomapper.PaymentDTOMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class PaymentDAOImplTemp implements PaymentDAOTemp{
    public static final Logger logger = LogManager.getLogger(PaymentDAOImplTemp.class);
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    PaymentDTOMapping paymentDTOMapping;

    @Override
    public boolean createPaymentTemp(PaymentDTO payment) {
        PaymentTemp paymentTemp = paymentDTOMapping.mapDTOtoEntityTemp(payment);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(paymentTemp);
            logger.info("Payment Inserted successfully in temporary table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    public PaymentDTO getPaymentTempById(Long id){
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            PaymentTemp paymentTemp = session.get(PaymentTemp.class, id);
            return paymentDTOMapping.mapEntityTemptoDTO(paymentTemp);
        } catch (Exception e) {
            logger.error(e.getMessage() + " in temporary table");
            return null;
        }
    }

    @Override
    public List<PaymentDTO> getListPaymentTempById(Long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<PaymentTemp> paymentTemps = session.createQuery("from PaymentTemp where RECEIVABLEPAYABLEID= :rid",PaymentTemp.class).setParameter("rid",id).list();
            return paymentTemps.stream().map(n -> paymentDTOMapping.mapEntityTemptoDTO(n)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage() + " in temporary table");
            return Collections.emptyList();
        }
    }
    @Override
    public List<PaymentDTO> getAllPaymentTemps() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<PaymentTemp> paymentTemps = session.createQuery("from PaymentTemp", PaymentTemp.class).list();
            return paymentTemps.stream().map(n -> paymentDTOMapping.mapEntityTemptoDTO(n)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("no payment entries found in the temporary table");
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updatePaymentTemp(PaymentDTO updatedPaymentTemp) {
        PaymentTemp paymentTemp = paymentDTOMapping.mapDTOtoEntityTemp(updatedPaymentTemp);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.update(paymentTemp);
            logger.info("Payment updated successfully in temporary table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in temporary table");
            return false;
        }
    }

    @Override
    public boolean deletePaymentTemp(PaymentDTO payment) {
        PaymentTemp paymentTemp = paymentDTOMapping.mapDTOtoEntityTemp(payment);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(paymentTemp);
            logger.info("Payment deleted successfully from temporary table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in temporary table");
            return false;
        }
    }


}
