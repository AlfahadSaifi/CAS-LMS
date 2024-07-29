package org.nucleus.service;

import org.nucleus.dao.PaymentDAOTemp;
import org.nucleus.dto.PaymentDTO;
import org.nucleus.utility.dtomapper.PaymentDTOMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PaymentServiceTempImpl implements PaymentServiceTemp {
    @Autowired
    PaymentDAOTemp paymentDAOTemp;
    @Autowired
    PaymentDTOMapping paymentDTOMapping;
    @Override
    public boolean createPaymentTemp(PaymentDTO payment) {
        return paymentDAOTemp.createPaymentTemp(payment);
    }

    @Override
    public PaymentDTO getPaymentTempById(Long id) {
        return paymentDAOTemp.getPaymentTempById(id);
    }

    @Override
    public List<PaymentDTO> getListPaymentTempById(Long id) {
        return paymentDAOTemp.getListPaymentTempById(id);
    }

    @Override
    public List<PaymentDTO> getAllPaymentTemps() {
        return paymentDAOTemp.getAllPaymentTemps();
    }

    @Override
    public boolean updatePaymentTemp(PaymentDTO updatedPaymentTemp) {
        return paymentDAOTemp.updatePaymentTemp(updatedPaymentTemp);
    }

    @Override
    public boolean deletePaymentTemp(PaymentDTO payment) {
        return paymentDAOTemp.deletePaymentTemp(payment);
    }

}