package org.nucleus.dto;

import org.springframework.stereotype.Component;

@Component
public class EmiParameterDTO {
    private Double loanAmount;
    private Double rate;
    private Integer repaymentFrequency;
    private Double tenure;
    private String loanApplicationId;
    private String fixedOrFloat;

    public String getFixedOrFloat() {
        return fixedOrFloat;
    }

    public void setFixedOrFloat(String fixedOrFloat) {
        this.fixedOrFloat = fixedOrFloat;
    }

    public String getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(String loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getRepaymentFrequency() {
        return repaymentFrequency;
    }

    public void setRepaymentFrequency(Integer repaymentFrequency) {
        this.repaymentFrequency = repaymentFrequency;
    }

    public Double getTenure() {
        return tenure;
    }

    public void setTenure(Double tenure) {
        this.tenure = tenure;
    }

    @Override
    public String toString() {
        return "EmiParameters{" +
                "loanAmount=" + loanAmount +
                ", rate=" + rate +
                ", repaymentFrequency=" + repaymentFrequency +
                ", tenure=" + tenure +
                '}';
    }
}
