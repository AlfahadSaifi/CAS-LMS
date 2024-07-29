package org.nucleus.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.nucleus.dao.*;
import org.nucleus.dto.AllocationDTO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.utility.enums.ReceiptStatus;
import org.nucleus.utility.AllocationCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

@Service
public class AllocationServiceImpl implements  AllocationService{
    private static final Logger logger= LogManager.getLogger(AllocationServiceImpl.class);
    private ReceiptDAO receiptDAO;
    private ReceivablePayableDao receivablePayableDAO;
    private LoanAccountDAO loanAccountDAO;
    private AllocationDAO allocationDAO;

    @Autowired
    private Environment env;

    @Autowired
    AllocationServiceImpl(ReceiptDAO receiptDAO, ReceivablePayableDao receivablePayableDAO, LoanAccountDAO loanAccountDAO, AllocationDAO allocationDAO){
        this.receiptDAO = receiptDAO;
        this.allocationDAO = allocationDAO;
        this.receivablePayableDAO = receivablePayableDAO;
        this.loanAccountDAO = loanAccountDAO;
    }

    private static final ConcurrentMap<Long, Lock> receivableLocks = new ConcurrentHashMap<>();


    public boolean batchAllocationForReceipt(){

        int batchSize = Integer.parseInt(Objects.requireNonNull(env.getProperty("batchSize")));

        //get the total number of receiptPayment entries
        Integer totalNumberOfRows = receiptDAO.getTotalNumberOfRows();
        logger.info(totalNumberOfRows+" found in the receipt for allocation");


        if (totalNumberOfRows != null && batchSize!=0) {

            System.out.println("but i was here");
            //calculate total of batches in which processing will be done
            int totalNumberOfBatches = AllocationCalculation.calculateNumberOfBatches(batchSize,totalNumberOfRows);

            System.out.println("hello there" + totalNumberOfBatches);
            //creating executor service with pool size of total batches
            ExecutorService executorService = Executors.newFixedThreadPool(totalNumberOfBatches);
            logger.info(totalNumberOfBatches);

            //use int stream to parallel process the underlying logic
            IntStream.range(0, totalNumberOfBatches).forEach(
                    batchNumber -> executorService.execute(
                            () -> {
                                List<ReceiptDTO> receiptPaymentList = receiptDAO.getReceiptList(batchNumber, batchSize);
                                Set<AllocationDTO> allocationDTOList = new HashSet<>();

                                //process each batch and update the receivable, receipt, loan Account table and insert data into allocation
                                processReceiptPaymentList(receiptPaymentList, allocationDTOList);

                                boolean batchProcessFlag = receiptDAO.updateReceiptInBatch(receiptPaymentList);
                                if(batchProcessFlag){
                                    logger.info("batch processed with batchNumber"+batchNumber);
                                }

                                boolean allocationFlag = allocationDAO.batchInsertAllocation(allocationDTOList);
                                if(allocationFlag)logger.info("data inserted in allocation successfully");
                            }
                    )
            );
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            //shutdown the executorService when task is completed
            executorService.shutdown();
        }
        else {
            System.out.println("here i am again");
            logger.warn("No data was found in receipt table to be allocated");
            return false;
        }
        System.out.println("fdsjdlbkdla;fhsj");
        return true;
    }


    public void processReceiptPaymentList(List<ReceiptDTO>receiptPaymentList, Set<AllocationDTO>allocationDTOList){


        System.out.println("--------------->"+receiptPaymentList);
        //traverse over the payments made and calculate the amount distribution
        for (ReceiptDTO payment : receiptPaymentList) {

            //check for the receipt is processed from before or not
            boolean realisedFlag = payment.getStatus().equals(ReceiptStatus.UNREALISED);
            if(!realisedFlag)continue;

            //get the receivable id
            Long receivableId = payment.getReceivablePayable().getReceivablePayableId();

            //get the loan Account using the loanAccountNumber
            String loanAccount = payment.getLoanAccountNumber();
            LoanAccountDTO loanAccountDTO = loanAccountDAO.getByAccountNumber(loanAccount);

            //form the receivable and payable DTO
            ReceivablePayableDTO receivablePayableDTO = payment.getReceivablePayable();

            //check for lock and then proceed with calculation
            if (receivablePayableDTO!=null && tryLockReceivable(receivableId)) {
                try {

                    Double interestRequired = receivablePayableDTO.getInterestComponent(); //Interest to be paid
                    Double principalRequired = receivablePayableDTO.getPrincipalComponent(); //Principal to be paid
                    double penalty = 0.0; //set penalty to zero initially

                    //check for null pointer then only find number of days
                    if(receivablePayableDTO.getReceivablePayableDate()!=null && payment.getInstrumentDate()!=null && Period.between(receivablePayableDTO.getReceivablePayableDate().toLocalDate(),payment.getInstrumentDate().toLocalDate()).getDays()>0) {

                        //demo calculation done here
                        penalty = AllocationCalculation.calculatePenalty(receivablePayableDTO,payment);
                    }

                    Double[] distributionAmount = AllocationCalculation.distributeAmount(payment.getTransactionAmount(),principalRequired,interestRequired,penalty);

                    //update the receipt
                    payment.setStatus(ReceiptStatus.REALISED);

                    //update the receivable
                    AllocationCalculation.updateReceivable(receivablePayableDTO,distributionAmount);

                    //update the loanAccount
                    //here first check whether to update the billed and un billed status
                    //loanAccount update - outStandingPrincipalComponent, totalNumberOfBilled Installment
                    AllocationCalculation.updateLoanAccount(loanAccountDTO,distributionAmount[2], "PAID".equals(receivablePayableDTO.getReceivablePayableStatus().name()));

                    allocationDTOList.add(AllocationCalculation.createAllocationDTO(loanAccountDTO,distributionAmount,payment));

                    //form the allocation DTO and call for it.
                    receivablePayableDAO.updateReceivablePayable(receivablePayableDTO);
                    loanAccountDAO.updateLoanAccount(loanAccountDTO);

                } finally{
                    logger.info("here to unlock"+" "+receivableId);
                    //add logic to use the dao of loanAccount and update the dataBase.
                    unlockReceivable(receivableId);
                }
            } else {
                // ReceivableId is already locked by another thread, skip processing
                logger.warn("Skipping processing for receivableId: " + receivableId);
            }
            //batch insert the receipts and allocation details

        }

    }

    private boolean tryLockReceivable(Long receivableId) {
        Lock lock = new ReentrantLock();
        Lock existingLock = receivableLocks.putIfAbsent(receivableId, lock);
        if (existingLock == null) {
            // Lock acquired successfully
            lock.lock();
            return true;
        } else {
            // Another thread is processing this receivableId
            return false;
        }
    }

    private void unlockReceivable(Long receivableId) {
        //unlock once free
        Lock lock = receivableLocks.get(receivableId);
        if (lock != null) {
            lock.unlock();
            receivableLocks.remove(receivableId, lock);
        }
    }

    //get the list of allocations in form of batches
    public List<AllocationDTO> getAllocationList(int batchSize, int batchNumber){
        return allocationDAO.getAllocationList(batchNumber,batchSize);
    }


    @Override
    public List<AllocationDTO> getAllocationByLoanAccountId(Long loanAccountId) {
        if(loanAccountId>0)
            return allocationDAO.getAllocationByLoanAccountId(loanAccountId);
        return null;
    }
    @Override
    public double calculateTotalPenalty(List<AllocationDTO> allocations) {
        double totalcharges=0.0;
        for (AllocationDTO allocation:allocations)
            if(allocation.getPenaltyDTOS().size() > 0)
            totalcharges+=AllocationCalculation.processDoubleValue(allocation.getPenaltyDTOS().get(0).getPenaltyAmount());
        return totalcharges;
    }



}
