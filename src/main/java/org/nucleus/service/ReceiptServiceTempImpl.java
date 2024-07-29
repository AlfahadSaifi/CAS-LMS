package org.nucleus.service;



import org.nucleus.dao.ReceiptDAOTemp;
import org.nucleus.dto.ReceiptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ReceiptServiceTempImpl implements ReceiptServiceTemp {

    @Autowired
    ReceiptDAOTemp receiptDAOTemp;
    @Override
    public boolean createReceiptTemp(ReceiptDTO payment) {
        return receiptDAOTemp.createReceiptTemp(payment);
    }

    @Override
    public ReceiptDTO getReceiptTempById(Long id) {
        return receiptDAOTemp.getReceiptTempById(id);
    }

    @Override
    public List<ReceiptDTO> getAllReceiptTemps() {
        return receiptDAOTemp.getAllReceiptTemps();
    }

    @Override
    public boolean updateReceiptTemp(ReceiptDTO updatedReceiptTemp) {
        return receiptDAOTemp.updateReceiptTemp(updatedReceiptTemp);
    }

    @Override
    public boolean deleteReceiptTemp( ReceiptDTO receiptDTO) {
        return receiptDAOTemp.deleteReceiptTemp(receiptDTO);
    }

    @Override
    public List<ReceiptDTO> getListReceiptTempById(Long id) {
        return receiptDAOTemp.getListReceiptTempById(id);
    }
}
