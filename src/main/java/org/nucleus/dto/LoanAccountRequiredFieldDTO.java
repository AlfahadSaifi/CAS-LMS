/*
 *Author: Rakesh Kumar
 *
 */

package org.nucleus.dto;

import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.utility.enums.RepaymentFrequency;

import java.sql.Date;

public class LoanAccountRequiredFieldDTO {
    private Double sanctionedAmt;
    private RepaymentFrequency repaymentFrequency;
    private Double emiAmt;

    private Double roi;
    private Integer numberOfInstallment;
    private Integer noOfInstallmentsUnbilled;
    private Integer noOfInstallmentsBilled;
    private Date loanSanctionDate;
    private Date disbursalDate;
    private String cifNumber;
    private Double disbursedAmt;
    private Long product;
    private Long productType;
    private LoanApplication loanApplication;

    public LoanApplication getLoanApplicationDTO() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public Integer getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(Integer numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }

    public Double getSanctionedAmt() {
        return sanctionedAmt;
    }

    public void setSanctionedAmt(Double sanctionedAmt) {
        this.sanctionedAmt = sanctionedAmt;
    }

    public RepaymentFrequency getRepaymentFrequency() {
        return repaymentFrequency;
    }

    public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
        this.repaymentFrequency = repaymentFrequency;
    }

    public Double getEmiAmt() {
        return emiAmt;
    }

    public void setEmiAmt(Double emiAmt) {
        this.emiAmt = emiAmt;
    }

    public Integer getNoOfInstallmentsUnbilled() {
        return noOfInstallmentsUnbilled;
    }

    public void setNoOfInstallmentsUnbilled(Integer noOfInstallmentsUnbilled) {
        this.noOfInstallmentsUnbilled = noOfInstallmentsUnbilled;
    }

    public Integer getNoOfInstallmentsBilled() {
        return noOfInstallmentsBilled;
    }

    public void setNoOfInstallmentsBilled(Integer noOfInstallmentsBilled) {
        this.noOfInstallmentsBilled = noOfInstallmentsBilled;
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

    public Double getDisbursedAmt() {
        return disbursedAmt;
    }

    public void setDisbursedAmt(Double disbursedAmt) {
        this.disbursedAmt = disbursedAmt;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }
}
