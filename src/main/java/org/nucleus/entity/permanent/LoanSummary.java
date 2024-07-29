package org.nucleus.entity.permanent;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Component
@Entity
@Table(name = "LOAN_Summary_NEW_17235")
public class LoanSummary {
    @Id
    private String loanAccountNumber;
    private String fullName;
    private Long contactNumber;
    private Double loanAmount;
    private Double emiAmount;
    private Date maturityDate;
    private Integer totalInstallments;
    private Date nextInstallmentDate;
    private Double outstandingBalancePrincipal;
    private Integer installmentsPaid;
    private Integer installmentsRemaining;
    private Double installmentAmountPaid;
    private Double installmentAmountRemaining;
    private Double penaltyChargesPaid;
    private Double penaltyChargesRemaining;
    private Double otherChargesPaid;
    private Double getOtherChargesRemaining;
    private Double totalPaid;
    private Double totalOverdue;

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(Double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Integer getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(Integer totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public Date getNextInstallmentDate() {
        return nextInstallmentDate;
    }

    public void setNextInstallmentDate(Date nextInstallmentDate) {
        this.nextInstallmentDate = nextInstallmentDate;
    }

    public Double getOutstandingBalancePrincipal() {
        return outstandingBalancePrincipal;
    }

    public void setOutstandingBalancePrincipal(Double outstandingBalancePrincipal) {
        this.outstandingBalancePrincipal = outstandingBalancePrincipal;
    }

    public Integer getInstallmentsPaid() {
        return installmentsPaid;
    }

    public void setInstallmentsPaid(Integer installmentsPaid) {
        this.installmentsPaid = installmentsPaid;
    }

    public Integer getInstallmentsRemaining() {
        return installmentsRemaining;
    }

    public void setInstallmentsRemaining(Integer installmentsRemaining) {
        this.installmentsRemaining = installmentsRemaining;
    }

    public Double getInstallmentAmountPaid() {
        return installmentAmountPaid;
    }

    public void setInstallmentAmountPaid(Double installmentAmountPaid) {
        this.installmentAmountPaid = installmentAmountPaid;
    }

    public Double getInstallmentAmountRemaining() {
        return installmentAmountRemaining;
    }

    public void setInstallmentAmountRemaining(Double installmentAmountRemaining) {
        this.installmentAmountRemaining = installmentAmountRemaining;
    }

    public Double getPenaltyChargesPaid() {
        return penaltyChargesPaid;
    }

    public void setPenaltyChargesPaid(Double penaltyChargesPaid) {
        this.penaltyChargesPaid = penaltyChargesPaid;
    }

    public Double getPenaltyChargesRemaining() {
        return penaltyChargesRemaining;
    }

    public void setPenaltyChargesRemaining(Double penaltyChargesRemaining) {
        this.penaltyChargesRemaining = penaltyChargesRemaining;
    }

    public Double getOtherChargesPaid() {
        return otherChargesPaid;
    }

    public void setOtherChargesPaid(Double otherChargesPaid) {
        this.otherChargesPaid = otherChargesPaid;
    }

    public Double getGetOtherChargesRemaining() {
        return getOtherChargesRemaining;
    }

    public void setGetOtherChargesRemaining(Double getOtherChargesRemaining) {
        this.getOtherChargesRemaining = getOtherChargesRemaining;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getTotalOverdue() {
        return totalOverdue;
    }

    public void setTotalOverdue(Double totalOverdue) {
        this.totalOverdue = totalOverdue;
    }

    @Override
    public String toString() {
        return "LoanSummary{" +
                "loanAccountNumber='" + loanAccountNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contactNumber=" + contactNumber +
                ", loanAmount=" + loanAmount +
                ", emiAmount=" + emiAmount +
                ", maturityDate=" + maturityDate +
                ", totalInstallments=" + totalInstallments +
                ", nextInstallmentDate=" + nextInstallmentDate +
                ", outstandingBalancePrincipal=" + outstandingBalancePrincipal +
                ", installmentsPaid=" + installmentsPaid +
                ", installmentsRemaining=" + installmentsRemaining +
                ", installmentAmountPaid=" + installmentAmountPaid +
                ", installmentAmountRemaining=" + installmentAmountRemaining +
                ", penaltyChargesPaid=" + penaltyChargesPaid +
                ", penaltyChargesRemaining=" + penaltyChargesRemaining +
                ", otherChargesPaid=" + otherChargesPaid +
                ", getOtherChargesRemaining=" + getOtherChargesRemaining +
                ", totalPaid=" + totalPaid +
                ", totalOverdue=" + totalOverdue +
                '}';
    }
}
