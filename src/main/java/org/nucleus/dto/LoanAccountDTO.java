package org.nucleus.dto;

import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.utility.enums.LoanStatus;
import org.nucleus.utility.enums.RepaymentFrequency;
import org.springframework.stereotype.Component;

import java.sql.Date;
@Component
public class LoanAccountDTO {
    private Long loanAccountId;
    private String loanAccountNumber;
    private Date loanSanctionDate;
    private Date disbursalDate;
    private String cifNumber;
    private Double finalSanctionedAmount;
    private Double loanAmountDisbursed;
    private Long loanProduct;
    private Long productType;
    private Double loanAmountPaidByCustomer;
    private Integer numberOfInstallment;
    private Integer numberOfInstallmentBilled;
    private Integer numberOfInstallmentUnbilled;
    private Double originalEmiAmount;
    private Integer originalTenure;
    private Double interestRate;
    private RepaymentFrequency repaymentFrequency;
    private LoanStatus loanStatus;
    private LoanApplication loanApplication;

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public Long getLoanAccountId() {
        return loanAccountId;
    }

    public void setLoanAccountId(Long loanAccountId) {
        this.loanAccountId = loanAccountId;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public Date getLoanSanctionDate() {
        return loanSanctionDate;
    }

    public void setLoanSanctionDate(Date loanSanctionDate) {
        this.loanSanctionDate = loanSanctionDate;
    }

    public Date getDisbursalDate() {
        return disbursalDate;
    }

    public void setDisbursalDate(Date disbursalDate) {
        this.disbursalDate = disbursalDate;
    }

    public String getCifNumber() {
        return cifNumber;
    }

    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }

    public Double getFinalSanctionedAmount() {
        return finalSanctionedAmount;
    }

    public void setFinalSanctionedAmount(Double finalSanctionedAmount) {
        this.finalSanctionedAmount = finalSanctionedAmount;
    }

    public Double getLoanAmountDisbursed() {
        return loanAmountDisbursed;
    }

    public void setLoanAmountDisbursed(Double loanAmountDisbursed) {
        this.loanAmountDisbursed = loanAmountDisbursed;
    }

    public Long getLoanProduct() {
        return loanProduct;
    }

    public void setLoanProduct(Long loanProduct) {
        this.loanProduct = loanProduct;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Double getLoanAmountPaidByCustomer() {
        return loanAmountPaidByCustomer;
    }

    public void setLoanAmountPaidByCustomer(Double loanAmountPaidByCustomer) {
        this.loanAmountPaidByCustomer = loanAmountPaidByCustomer;
    }

    public Integer getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(Integer numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }

    public Integer getNumberOfInstallmentBilled() {
        return numberOfInstallmentBilled;
    }

    public void setNumberOfInstallmentBilled(Integer numberOfInstallmentBilled) {
        this.numberOfInstallmentBilled = numberOfInstallmentBilled;
    }

    public Integer getNumberOfInstallmentUnbilled() {
        return numberOfInstallmentUnbilled;
    }

    public void setNumberOfInstallmentUnbilled(Integer numberOfInstallmentUnbilled) {
        this.numberOfInstallmentUnbilled = numberOfInstallmentUnbilled;
    }

    public Double getOriginalEmiAmount() {
        return originalEmiAmount;
    }

    public void setOriginalEmiAmount(Double originalEmiAmount) {
        this.originalEmiAmount = originalEmiAmount;
    }

    public Integer getOriginalTenure() {
        return originalTenure;
    }

    public void setOriginalTenure(Integer originalTenure) {
        this.originalTenure = originalTenure;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public RepaymentFrequency getRepaymentFrequency() {
        return repaymentFrequency;
    }

    public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
        this.repaymentFrequency = repaymentFrequency;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    @Override
    public String toString() {
        return "LoanAccountDTO{" +
                "loanAccountId=" + loanAccountId +
                ", loanAccountNumber='" + loanAccountNumber + '\'' +
                ", loanSanctionDate=" + loanSanctionDate +
                ", disbursalDate=" + disbursalDate +
                ", cifNumber='" + cifNumber + '\'' +
                ", finalSanctionedAmount=" + finalSanctionedAmount +
                ", loanAmountDisbursed=" + loanAmountDisbursed +
                ", loanProduct=" + loanProduct +
                ", productType=" + productType +
                ", loanAmountPaidByCustomer=" + loanAmountPaidByCustomer +
                ", numberOfInstallment=" + numberOfInstallment +
                ", numberOfInstallmentBilled=" + numberOfInstallmentBilled +
                ", numberOfInstallmentUnbilled=" + numberOfInstallmentUnbilled +
                ", originalEmiAmount=" + originalEmiAmount +
                ", originalTenure=" + originalTenure +
                ", interestRate=" + interestRate +
                ", repaymentFrequency=" + repaymentFrequency +
                ", loanStatus=" + loanStatus +
                ", loanApplication=" + loanApplication +
                '}';
    }
}
