package org.nucleus.service;

import org.nucleus.dao.ReceivablePayableDao;
import org.nucleus.dao.ReceivablePayableTempDao;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.permanent.ReceivablePayable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class ReceivablePayableServiceImpl implements ReceivablePayableService{
    private final ReceivablePayableDao receivablePayableDao;
    @Autowired
    public ReceivablePayableServiceImpl(ReceivablePayableDao receivablePayableDao){
        this.receivablePayableDao = receivablePayableDao;
    }

    @Override
    public boolean insertReceivablePayable(ReceivablePayableDTO receivablePayable) {
        return receivablePayableDao.insertReceivablePayable(receivablePayable);
    }

    @Override
    public boolean updateReceivablePayable(ReceivablePayableDTO receivablePayable) {
        return receivablePayableDao.updateReceivablePayable(receivablePayable);
    }

    @Override
    public boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayable) {
        return receivablePayableDao.deleteReceivablePayable(receivablePayable);
    }

    @Override
    public ReceivablePayableDTO getReceivablePayable(Long receivablePayableId) {
        return receivablePayableDao.getReceivablePayable(receivablePayableId);
    }

    @Override
    public List<ReceivablePayableDTO> getAllReceivablePayable() {
        return receivablePayableDao.getAllReceivablePayable();
    }

    @Override
    public List<ReceivablePayableDTO> getReceivablePayableBYLoanAccId(Long loanAccountId) {
        if(loanAccountId>0)
            return receivablePayableDao.getReceivablePayableBYLoanAccId(loanAccountId);
        return null;
    }
    public Double calculateUnpaidPenalty(List<ReceivablePayableDTO> receivablePayables)
    {
        Double unpaidPenalty=0.0;

        for(ReceivablePayableDTO receivablePayable:receivablePayables)
        {
            Date currentDate=new Date(System.currentTimeMillis());
            System.out.println("Status is "+receivablePayable.getReceivablePayableStatus());
            if("PENDING".equalsIgnoreCase(receivablePayable.getReceivablePayableStatus().toString()) && receivablePayable.getReceivablePayableDueDate().before(currentDate))
            {

                Date sqlDate1 = receivablePayable.getReceivablePayableDueDate();
                Date sqlDate2 = currentDate;
// Convert SQL dates to LocalDate
                LocalDate date1 = sqlDate1.toLocalDate();
                LocalDate date2 = LocalDate.now();
// Calculate the difference in days
                long daysDifference = ChronoUnit.DAYS.between(date1, date2);
                System.out.println("Days between the two dates: " + daysDifference);
                unpaidPenalty+=daysDifference*100;
            }
        }
        return unpaidPenalty;
    }

}
