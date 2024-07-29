package org.nucleus.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.nucleus.dao.LoanApplicationDAO;
import org.nucleus.dao.ReceivablePayableTempDao;
import org.nucleus.dao.RepayScheduleDAO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.entity.permanent.RepaySchedule;
import org.nucleus.utility.AllocationCalculation;
import org.nucleus.utility.enums.ReceivablePayableStatus;
import org.nucleus.utility.enums.ReceivablePayableType;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceivablePayableTempServiceImpl implements ReceivablePayableTempService {

    private final ReceivablePayableTempDao receivablePayableTempDao;
    private final LoanApplicationService loanApplicationDAO;

    private final RepayScheduleDAO repayScheduleDAO;

    private static final Logger logger = LogManager.getLogger(ReceivablePayableTempServiceImpl.class);
    @Autowired
    public ReceivablePayableTempServiceImpl(ReceivablePayableTempDao receivablePayableTempDao,LoanApplicationService loanApplicationDAO,RepayScheduleDAO repayScheduleDAO){
        this.receivablePayableTempDao = receivablePayableTempDao;
        this.loanApplicationDAO=loanApplicationDAO;
        this.repayScheduleDAO = repayScheduleDAO;
    }

    @Override
    public boolean insertReceivablePayable(ReceivablePayableDTO receivablePayableDTO) {

        if(ReceivablePayableType.RECEIVABLE.equals(receivablePayableDTO.getReceivablePayableType())){
            try{
                List<RepayScheduleDTO> repayschedule = repayScheduleDAO.fetchRepaySchedule(loanApplicationDAO.readPermanentByLoanAccountNumber(receivablePayableDTO.getLoanAccount().getLoanAccountNumber()).getLoanId());

                List<RepayScheduleDTO> newRepayschedule = repayschedule
                        .stream()
                        .filter((e) -> e.getDueDate().equals(receivablePayableDTO.getReceivablePayableDueDate()))
                        .collect(Collectors.toList());

                receivablePayableDTO.setPrincipalComponent(newRepayschedule.get(0).getPrincipalComponent());
                receivablePayableDTO.setInterestComponent(newRepayschedule.get(0).getInterestComponent());
            }catch(ArrayIndexOutOfBoundsException e){
                logger.error("Due Date doesn't exist");
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        }
        if(receivablePayableDTO.getReceivablePayableAmount() == AllocationCalculation.processDoubleValue(receivablePayableDTO.getLoanAccount().getFinalSanctionedAmount()-AllocationCalculation.processDoubleValue(receivablePayableDTO.getLoanAccount().getLoanAmountPaidByCustomer()))){
            receivablePayableDTO.setPrincipalComponent(receivablePayableDTO.getReceivablePayableAmount());
        }
        receivablePayableDTO.setReceivablePayableDate(Date.valueOf(LocalDate.now()));
        receivablePayableDTO.setReceivablePayableStatus(ReceivablePayableStatus.PENDING);
        receivablePayableDTO.getTempMetaData().setRecordStatus(RecordStatus.N);
        System.out.println("----------------------"+receivablePayableDTO.getReceivablePayableDueDate());
        System.out.println("Date==============="+receivablePayableDTO.getReceivablePayableDate());
        return receivablePayableTempDao.insertReceivablePayable(receivablePayableDTO);
    }

    @Override
    public boolean updateReceivablePayable(ReceivablePayableDTO receivablePayableDTO) {

        //check if the rejection level flag is N, set it to NR(newly rejected)
        if (receivablePayableDTO.getTempMetaData().getRecordStatus() == RecordStatus.N)
            receivablePayableDTO.getTempMetaData().setRecordStatus(RecordStatus.NR);

            //check if the rejection level flag is Nr, set it to MR(modified rejected)
        else if(receivablePayableDTO.getTempMetaData().getRecordStatus() == RecordStatus.NR)
            receivablePayableDTO.getTempMetaData().setRecordStatus(RecordStatus.MR);
        return receivablePayableTempDao.updateReceivablePayable(receivablePayableDTO);

    }

    @Override
    public boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayableDTO) {
        return receivablePayableTempDao.deleteReceivablePayable(receivablePayableDTO);
    }

    @Override
    public ReceivablePayableDTO getReceivablePayable(Long receivablePayableId) {
        return receivablePayableTempDao.getReceivablePayable(receivablePayableId);
    }

    @Override
    public List<ReceivablePayableDTO> getAllReceivablePayable() {
        return receivablePayableTempDao.getAllReceivablePayable();
    }

    @Override
    public List<ReceivablePayableDTO> getReceivablePayableAgainstLoanIdAndDateRange(LoanAccountDTO loanAccount, Date startDate, Date endDate) {
        return receivablePayableTempDao.getReceivablePayableAgainstLoanIdAndDateRange(loanAccount,startDate,endDate);
    }
}
