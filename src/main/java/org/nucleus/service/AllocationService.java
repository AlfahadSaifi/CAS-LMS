 package org.nucleus.service;

 import org.nucleus.dto.AllocationDTO;
 import org.nucleus.dto.ReceiptDTO;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Set;

 public interface AllocationService {

    boolean batchAllocationForReceipt();
    //create executor pool to read a batch of size 25

    void processReceiptPaymentList(List<ReceiptDTO>receiptPaymentList, Set<AllocationDTO> allocationDTOList);
    //process the list of receiptPayment

     List<AllocationDTO> getAllocationList(int batchSize, int batchNumber);
     //get receipts in batch

     List<AllocationDTO> getAllocationByLoanAccountId(Long loanAccountId);

     double calculateTotalPenalty(List<AllocationDTO> allocations);

}
