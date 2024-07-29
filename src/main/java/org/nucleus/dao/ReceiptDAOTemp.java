package org.nucleus.dao;


import org.nucleus.dto.ReceiptDTO;

import java.util.List;

public interface ReceiptDAOTemp {
    boolean createReceiptTemp(ReceiptDTO payment);
    ReceiptDTO getReceiptTempById(Long id);
    List<ReceiptDTO> getAllReceiptTemps();
    boolean updateReceiptTemp(ReceiptDTO updatedReceiptTemp);
    boolean deleteReceiptTemp(ReceiptDTO receiptDTO);
    List<ReceiptDTO> getListReceiptTempById(Long id);
}
