package org.nucleus.service;
/* RISHA SHARMA */
import org.apache.logging.log4j.LogManager;

import org.nucleus.dao.*;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.utility.enums.ReceivablePayableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.utility.enums.LoanStatus;
import org.nucleus.utility.enums.ClosureStatus;
import org.nucleus.utility.enums.RecordStatus;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Service
@Transactional
public class LoanClosureServiceTempImpl implements LoanClosureServiceTemp {
    @Autowired
    LoanAccountDAO loanAccountDAO;
    @Autowired
    LoanClosureDAO loanClosureDAO;
    @Autowired
    RepayScheduleDAO repayScheduleDAO;
    @Autowired
    ReceivablePayableDao receivablePayableDao;

    @Autowired
    private Environment env;

    @Override
    public boolean performLoanClosure() {
        final int maxThreads = Integer.parseInt(Objects.requireNonNull(env.getProperty("MAX_THREADS")));
        int batchSize = Integer.parseInt(Objects.requireNonNull(env.getProperty("batchSize")));

        //total number of rows in loan account table
        Long totalRecords = loanAccountDAO.getRowCount();

        if(totalRecords==0 || batchSize==0) {
            LogManager.getLogger().info("Either no data in Loan Account table, or batchSize is set to 0");
            return false;
        }

        if(totalRecords<= batchSize)
            batchSize = 1;

        //number of threads to be made.
        int numThreads = Integer.parseInt(String.valueOf(Math.min(maxThreads, totalRecords / batchSize)));

        //Creating fixed Threads through ExecutorService to perform multitasking
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int finalBatchSize = batchSize;
            IntStream.range(0, numThreads).forEach(element ->
                    executor.submit(() -> {
                        try {

                            //getting the batches for performing loan closure
                            List<LoanAccountDTO> batch = loanAccountDAO.getLoanAccountsInBatch(element, finalBatchSize);

                            //calling loan Account closure method
                            return closeLoanAccounts(batch);
                        } catch (Exception e) {
                            LogManager.getLogger().error(e.getMessage());
                            Thread.currentThread().interrupt();
                            return false;
                        } finally {
                            executor.shutdown();
                        }
                    }));
            return true;
    }

    private boolean closeLoanAccounts(List<LoanAccountDTO> loanAccounts) {
        try {
            //checking if loanAccount List is null or not, if null or empty, no further processing will be done.
            assert loanAccounts != null;
            if(loanAccounts.isEmpty()) {
                LogManager.getLogger().debug("Loan Account batch is empty");
                return false;
            }
            //Adding data to loanClosure table while changing the status to be inactive/closed in Loan Account table.
            IntStream.range(0, loanAccounts.size())
                    .forEach(chunk -> {
                        List<LoanClosureDTO> loanClosureDTOList=new ArrayList<>();
                        loanAccounts.forEach(loanAccountDTO -> {

                            //setting loanClosure object to save in database
                            LoanClosureDTO loanClosureDTO = new LoanClosureDTO();

                            //setting loanAccount status to closed
                            loanAccountDTO.setLoanStatus(LoanStatus.CLOSED);
                            loanClosureDTO.setLoanAccountDTO(loanAccountDTO);
                            loanClosureDTO.setLoanClosureDate(Date.valueOf(LocalDate.now()));
                            loanClosureDTO.setClosureStatus(ClosureStatus.REGULAR_CLOSURE);

                            MetaData metaData = new MetaData();
                            metaData.setCreationDate(Date.valueOf(LocalDate.now()));
                            metaData.setRecordStatus(RecordStatus.A);
                            metaData.setCreationDate(Date.valueOf(LocalDate.now()));
                            loanClosureDTO.setMetaData(metaData);
                            loanClosureDTOList.add(loanClosureDTO);
                        });

                        //calling the functions in batching to update loan Account and add data to loanClosure list.
                        loanAccountDAO.updateBatch(loanAccounts, loanAccounts.size());
                        loanClosureDAO.addData(loanClosureDTOList,loanAccounts.size());
                    });
        }
        catch (Exception e) {
            LogManager.getLogger().error(e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }

    //method to get the data of all loans that could not be closed by today, and checking the valid conditions to extract their information.
    @Override
    public List<ReceivablePayableDTO> getLoansNotClosed() {

        AtomicReference<List<ReceivablePayableDTO>> receivablePayableDTOList = new AtomicReference<>();
        loanAccountDAO.getAllLoanAccounts().forEach((loanAccountDTO -> {
            List<RepayScheduleDTO> repayScheduleDTOS =repayScheduleDAO.fetchRepaySchedule(loanAccountDTO.getLoanAccountId());

            //check if repaySchedule for particular loanId is null or not.
            if(!repayScheduleDTOS.isEmpty()

                    //check for if today is matured date of the respective loan account
                    && Objects.equals(repayScheduleDTOS.get(repayScheduleDTOS.size()-1).getDueDate(), Date.valueOf(LocalDate.now()))

                    //The highest variation that the difference of 2 numbers with 2 decimal points could face will be 3.6, as considering the max tenure of 30 years, and monthly installments.
                    && loanAccountDTO.getLoanAmountDisbursed()-loanAccountDTO.getLoanAmountPaidByCustomer()>=3.6

                    //check if loanStatus is active or closed
                    && loanAccountDTO.getLoanStatus().equals(LoanStatus.ACTIVE)){

                receivablePayableDTOList.set(receivablePayableDao.getReceivablePayableBYLoanAccId(loanAccountDTO.getLoanAccountId())
                        .stream()
                        .filter(receivablePayableDTO->receivablePayableDTO.getReceivablePayableStatus()

                                //check for if the receivable is generated for a particular loan and its status is PENDING, if yes, then loan Account will not be closed.
                                .equals(ReceivablePayableStatus.PENDING))
                        .collect(Collectors.toList())
                );
            }
        }));

        return receivablePayableDTOList.get();
    }

    @Override
    public List<LoanClosureDTO> getAllRegularClosureData() {
        return loanClosureDAO.getAllRegularClosureData();
    }
    @Override
    public LoanClosureDTO getLoanClosureDetailPermanent(Long closureLoanId){
        return loanClosureDAO.getLoanClosureDetailPer(closureLoanId);
    }

    @Override
    public boolean save(LoanClosureDTO loanClosureDTO) {
        return loanClosureDAO.save(loanClosureDTO);
    }

    @Override
    public boolean update(LoanClosureDTO loanClosureDTO) {
        return loanClosureDAO.update(loanClosureDTO);
    }

    @Override
    public boolean delete(Long loanClosureId) {
        return loanClosureDAO.delete(loanClosureId);
    }

    @Override
    public boolean delete(LoanClosureDTO loanClosureDTO) {
        return loanClosureDAO.delete(loanClosureDTO);
    }

    @Override
    public List<LoanClosureDTO> getAllLoanClosureDataPermanentTable() {
        return loanClosureDAO.getAllLoanClosureDataPermanentTable();
    }
}