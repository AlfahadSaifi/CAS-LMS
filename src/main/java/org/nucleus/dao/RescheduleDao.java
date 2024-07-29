package org.nucleus.dao;

import org.nucleus.dto.RescheduleResponseDTO;
import org.nucleus.entity.temporary.RepayScheduleTemp;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RescheduleDao {

    String getCustomerNameByLoanAccountNumber(String loanAccountNumber);
    RescheduleResponseDTO getRescheduleResponse(String loanAccountNumber);
    List<RepayScheduleTemp> checkRepaySchedule(String key, String value);
}
