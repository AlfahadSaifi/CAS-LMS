package org.nucleus.service;


import org.nucleus.dao.PaymentDAO;
import org.nucleus.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
@Autowired
PaymentDAO paymentDAO;
    @Override
    public boolean createPaymentDTO(PaymentDTO payment) {
        return paymentDAO.createPaymentDTO(payment);
    }

    @Override
    public PaymentDTO getPaymentDTOById(Long id) {
        return paymentDAO.getPaymentDTOById(id);
    }

    @Override
    public List<PaymentDTO> getAllPaymentDTOs() {
        return paymentDAO.getAllPaymentDTOs();
    }

    @Override
    public boolean updatePaymentDTO(PaymentDTO updatedPaymentDTO) {
        return paymentDAO.updatePaymentDTO(updatedPaymentDTO);
    }

    @Override
    public boolean deletePaymentDTO(PaymentDTO payment) {
        return paymentDAO.deletePaymentDTO(payment);
    }
}
