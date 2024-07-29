package org.nucleus.dao;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.PaymentDTO;
import org.nucleus.entity.permanent.Payment;
import org.nucleus.utility.dtomapper.PaymentDTOMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PaymentDAOImpl implements PaymentDAO {
    public static final Logger logger = LogManager.getLogger(PaymentDAOImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    PaymentDTOMapping paymentDTOMapping;
    @Override
    public boolean createPaymentDTO(PaymentDTO paymentdto) {
        Payment payment = paymentDTOMapping.mapDTOtoEntity(paymentdto);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(payment);
            logger.info("Payment Inserted successfully in permanent table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public PaymentDTO getPaymentDTOById(Long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            Payment payment = session.get(Payment.class, id);
            return paymentDTOMapping.mapEntitytoDTO(payment);
        } catch (Exception e) {
            logger.error(e.getMessage() + " in permanent table");
            return null;
        }
    }

    @Override
    public List<PaymentDTO> getAllPaymentDTOs() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<Payment> payments = session.createQuery("from Payment", Payment.class).list();
            return payments.stream().map(n -> paymentDTOMapping.mapEntitytoDTO(n)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("no Payment entries found in the permanent table");
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updatePaymentDTO(PaymentDTO updatedPaymentDTO) {
        Payment payment = paymentDTOMapping.mapDTOtoEntity(updatedPaymentDTO);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.update(payment);
            logger.info("Payment updated successfully in permanent table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in permanent table");
            return false;
        }
    }

    @Override
    public boolean deletePaymentDTO(PaymentDTO paymentDTO) {
        Payment payment = paymentDTOMapping.mapDTOtoEntity(paymentDTO);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(payment);
            logger.info("Payment deleted successfully from permanent table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in permanent table");
            return false;
        }
    }
}
