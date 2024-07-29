package org.nucleus.dto;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class FinancialInfoDTO {

    private Long financialId;

    @NotNull(message = "Monthly income is required")
    @DecimalMin(value = "0.01", message = "Loan amount must be greater than 0")
    private Double monthlyIncome;

    private Double rentIncome;

    @NotNull(message = "Monthly expense is required")
    @Min(value = 0, message = "Value must be greater than or equal to 0")
    private Double monthlyExpense;

    private Double medicalExpense;


    public Long getFinancialId() {
        return financialId;
    }

    public void setFinancialId(Long financialId) {
        this.financialId = financialId;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Double getRentIncome() {
        return rentIncome;
    }

    public void setRentIncome(Double rentIncome) {
        this.rentIncome = rentIncome;
    }

    public Double getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(Double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public Double getMedicalExpense() {
        return medicalExpense;
    }

    public void setMedicalExpense(Double medicalExpense) {
        this.medicalExpense = medicalExpense;
    }


    @Override
    public String toString() {
        return "FinancialInfo{" +
                "financialId=" + financialId +
                ", monthlyIncome=" + monthlyIncome +
                ", rentIncome=" + rentIncome +
                ", monthlyExpense=" + monthlyExpense +
                ", medicalExpense=" + medicalExpense +
                '}';
    }
}
