package org.nucleus.dao;

import org.nucleus.dto.ReceiptDTO;

import java.sql.Date;
import java.util.List;

public interface ReceiptDAO {
//    boolean batchInsertAllocation(List<AllocationDTO> allocationDTOList);
      List<ReceiptDTO> getReceiptList(int batchNumber, int batchSize);
      Integer getTotalNumberOfRows();
      Boolean updateReceiptInBatch(List<ReceiptDTO>receiptDTOList);

      boolean createReceipt(ReceiptDTO receiptDTO);
      ReceiptDTO getReceiptById(Long id);
      List<ReceiptDTO> getAllReceipts();
      boolean updateReceipt(ReceiptDTO updatedReceipt);
      boolean deleteReceipt(ReceiptDTO receiptDTO);

      List<ReceiptDTO> getallReceiptbyLoanACC(String id, Date fromdate, Date todate);

}
