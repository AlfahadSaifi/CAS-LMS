package org.nucleus.dao;


import org.nucleus.dto.PaymentDTO;

import java.util.List;

public interface PaymentDAOTemp {
    boolean createPaymentTemp(PaymentDTO payment);
    PaymentDTO getPaymentTempById(Long id);
    List<PaymentDTO> getListPaymentTempById(Long id);
    List<PaymentDTO> getAllPaymentTemps();
    boolean updatePaymentTemp(PaymentDTO updatedPaymentTemp);
    boolean deletePaymentTemp(PaymentDTO payment);
}
