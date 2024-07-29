package org.nucleus.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class LoanSummaryDTO {
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
}