package org.nucleus.dao;


import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.entity.permanent.RepaySchedule;

import java.util.List;

public interface RepayScheduleDAO {
    List<RepayScheduleDTO> fetchRepaySchedule(Long loanId);
}
