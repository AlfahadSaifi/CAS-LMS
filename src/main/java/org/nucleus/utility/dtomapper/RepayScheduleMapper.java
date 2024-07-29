package org.nucleus.utility.dtomapper;

import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.entity.permanent.RepaySchedule;

import java.sql.Date;

public class RepayScheduleMapper {

    public static RepayScheduleDTO convertToDTO(RepaySchedule repaySchedule) {
        RepayScheduleDTO repayScheduleDTO= new RepayScheduleDTO();
        repayScheduleDTO.setInstallmentAmount(repaySchedule.getInstallmentAmount());
        repayScheduleDTO.setDueDate(repaySchedule.getDueDate());
        repayScheduleDTO.setEffectiveInterestRate(repaySchedule.getEffectiveInterestRate());
        repayScheduleDTO.setInterestComponent(repaySchedule.getInterestComponent());
        repayScheduleDTO.setPrincipalComponent(repaySchedule.getPrincipalComponent());
        repayScheduleDTO.setOpeningBalance(repaySchedule.getOpeningBalance());
        repayScheduleDTO.setInstallmentNumber(repaySchedule.getInstallmentNumber());
        repayScheduleDTO.setOutstandingBalancePrincipal(repaySchedule.getOutstandingBalancePrincipal());

        return repayScheduleDTO;
    }
    public static RepaySchedule convertToEntity(RepayScheduleDTO repayScheduleDTO) {
        RepaySchedule repaySchedule= new RepaySchedule();
        repaySchedule.setId(repaySchedule.getId());
        repaySchedule.setInstallmentAmount(repayScheduleDTO.getInstallmentAmount());
        repaySchedule.setDueDate(repayScheduleDTO.getDueDate());
        repaySchedule.setEffectiveInterestRate(repayScheduleDTO.getEffectiveInterestRate());
        repaySchedule.setInterestComponent(repayScheduleDTO.getInterestComponent());
        repaySchedule.setPrincipalComponent(repayScheduleDTO.getPrincipalComponent());
        repaySchedule.setOpeningBalance(repayScheduleDTO.getOpeningBalance());
        repaySchedule.setInstallmentNumber(repayScheduleDTO.getInstallmentNumber());
        repaySchedule.setOutstandingBalancePrincipal(repayScheduleDTO.getOutstandingBalancePrincipal());
        return repaySchedule;
    }
}