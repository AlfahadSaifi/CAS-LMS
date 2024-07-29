package org.nucleus.service;



import org.nucleus.dto.ReceiptDTO;

import java.sql.Date;
import java.util.List;

public interface ReceiptService {
    boolean createReceiptDTO(ReceiptDTO receiptDTO);
    ReceiptDTO getReceiptDTOById(Long id);
    List<ReceiptDTO> getAllReceiptDTOs();
    boolean updateReceiptDTO(ReceiptDTO updatedReceiptDTO);
    boolean deleteReceiptDTO(ReceiptDTO receiptDTO);
    List<ReceiptDTO> getallReceiptbyLoanACC(String id, Date fromdate, Date todate);
    boolean createReceiptTemp(ReceiptDTO receiptDTO);
    ReceiptDTO getReceiptTempById(Long id);
    List<ReceiptDTO> getAllReceiptTemps();
    boolean updateReceiptTemp(ReceiptDTO updatedReceiptTemp);
    boolean deleteReceiptTemp(ReceiptDTO receiptDTO);
    List<ReceiptDTO> getListReceiptTempById(Long id);
    ReceiptDTO calculateReceivableDetails(Long receivablePayableId);
    boolean submitReceipt(ReceiptDTO receiptDTO);
    List<ReceiptDTO> getReceiptDTOListByReceivableId(Long receivablePayableId, Date fromDate, Date toDate);
    ReceiptDTO approveReceiptByReceiptId(ReceiptDTO receiptDTO);
}
