package org.nucleus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class RepayScheduleDTO {

    private Long id;
    private Double effectiveInterestRate;
    private Double installmentAmount;
    private Integer installmentNumber;
    private Double openingBalance;
    private Double outstandingBalancePrincipal;
    private Double principalComponent;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    private Double interestComponent;

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getPrincipalComponent() {
        return principalComponent;
    }

    public void setPrincipalComponent(Double principalComponent) {
        this.principalComponent = principalComponent;
    }

    public Double getInterestComponent() {
        return interestComponent;
    }

    public void setInterestComponent(Double interestComponent) {
        this.interestComponent = interestComponent;
    }

    public Double getEffectiveInterestRate() {
        return effectiveInterestRate;
    }

    public void setEffectiveInterestRate(Double effectiveInterestRate) {
        this.effectiveInterestRate = effectiveInterestRate;
    }

    public Double getOutstandingBalancePrincipal() {
        return outstandingBalancePrincipal;
    }

    public void setOutstandingBalancePrincipal(Double outstandingBalancePrincipal) {
        this.outstandingBalancePrincipal = outstandingBalancePrincipal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RepayScheduleDTO{" +
                "id=" + id +
                ", effectiveInterestRate=" + effectiveInterestRate +
                ", installmentAmount=" + installmentAmount +
                ", installmentNumber=" + installmentNumber +
                ", openingBalance=" + openingBalance +
                ", outstandingBalancePrincipal=" + outstandingBalancePrincipal +
                ", principalComponent=" + principalComponent +
                ", dueDate=" + dueDate +
                ", interestComponent=" + interestComponent +
                '}';
    }
}
