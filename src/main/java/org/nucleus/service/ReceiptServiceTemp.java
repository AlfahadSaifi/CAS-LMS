package org.nucleus.service;




import org.nucleus.dto.ReceiptDTO;

import java.util.List;

public interface ReceiptServiceTemp {
    boolean createReceiptTemp(ReceiptDTO receiptDTO);
    ReceiptDTO getReceiptTempById(Long id);
    List<ReceiptDTO> getAllReceiptTemps();
    boolean updateReceiptTemp(ReceiptDTO updatedReceiptTemp);
    boolean deleteReceiptTemp(ReceiptDTO receiptDTO);
    List<ReceiptDTO> getListReceiptTempById(Long id);
}
