package org.nucleus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.nucleus.entity.permanent.Penalty;

import java.sql.Date;
import java.util.List;

public class AllocationDTO {

    private Long allocationId;
    private LoanAccountDTO loanAccountDTO;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date depositDate;
    private Double interestComponentReceived;
    private Double principalComponentReceived;
    private List<PenaltyDTO>penaltyDTOS;

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public LoanAccountDTO getLoanAccount() {
        return loanAccountDTO;
    }

    public void setLoanAccount(LoanAccountDTO loanAccountDTO) {
        this.loanAccountDTO = loanAccountDTO;
    }

    public Date getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    public Double getInterestComponentReceived() {
        return interestComponentReceived;
    }

    public void setInterestComponentReceived(Double interestComponentReceived) {
        this.interestComponentReceived = interestComponentReceived;
    }

    public Double getPrincipalComponentReceived() {
        return principalComponentReceived;
    }

    public void setPrincipalComponentReceived(Double principalComponentReceived) {
        this.principalComponentReceived = principalComponentReceived;
    }

    public LoanAccountDTO getLoanAccountDTO() {
        return loanAccountDTO;
    }

    public void setLoanAccountDTO(LoanAccountDTO loanAccountDTO) {
        this.loanAccountDTO = loanAccountDTO;
    }

    public List<PenaltyDTO> getPenaltyDTOS() {
        return penaltyDTOS;
    }

    public void setPenaltyDTOS(List<PenaltyDTO> penaltyDTOS) {
        this.penaltyDTOS = penaltyDTOS;
    }

    @Override
    public String toString() {
        return "AllocationDTO{" +
                "allocationId=" + allocationId +
                ", loanAccountDTO=" + loanAccountDTO +
                ", depositDate=" + depositDate +
                ", interestComponentReceived=" + interestComponentReceived +
                ", principalComponentReceived=" + principalComponentReceived +
                ", penaltyDTOS=" + penaltyDTOS +
                '}';
    }
}
