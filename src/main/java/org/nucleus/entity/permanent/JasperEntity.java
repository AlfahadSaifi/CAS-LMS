package org.nucleus.entity.permanent;

import org.nucleus.utility.enums.RepaymentFrequency;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;

@Component
@Entity
@Table(name="JasperTable")
public class JasperEntity {
    @Id
    private String loanAccountNumber;
    private Date disbursalDate;
    private Double loanAmountDisbursed;
    private Double originalEmiAmount;
    private Double loanAmountPaidByCustomer;
    private Long productType;
    private Integer originalTenure;
    private Double interestRate;
    private String branch;
    private Long loanProduct;
    private Double finalSanctionedAmount;
    private Date loanSanctionDate;
    @Enumerated(EnumType.STRING)
    private RepaymentFrequency repaymentFrequency;
    private Integer numberOfInstallment;
    private String firstName;
    private Date dateOfBirth;
    private String emailAddress;
    private String cifNumber;
    private String fullAddress;
    private String city;
    private String state;
    private String country;
    private Integer pinCode;
    private Long contactNumber;

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public Date getDisbursalDate() {
        return disbursalDate;
    }

    public void setDisbursalDate(Date disbursalDate) {
        this.disbursalDate = disbursalDate;
    }

    public Double getLoanAmountDisbursed() {
        return loanAmountDisbursed;
    }

    public void setLoanAmountDisbursed(Double loanAmountDisbursed) {
        this.loanAmountDisbursed = loanAmountDisbursed;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Long getLoanProduct() {
        return loanProduct;
    }

    public Double getLoanAmountPaidByCustomer() {
        return loanAmountPaidByCustomer;
    }

    public void setLoanAmountPaidByCustomer(Double loanAmountPaidByCustomer) {
        this.loanAmountPaidByCustomer = loanAmountPaidByCustomer;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public void setLoanProduct(Long loanProduct) {
        this.loanProduct = loanProduct;
    }

    public Double getFinalSanctionedAmount() {
        return finalSanctionedAmount;
    }

    public void setFinalSanctionedAmount(Double finalSanctionedAmount) {
        this.finalSanctionedAmount = finalSanctionedAmount;
    }

    public Date getLoanSanctionDate() {
        return loanSanctionDate;
    }

    public void setLoanSanctionDate(Date loanSanctionDate) {
        this.loanSanctionDate = loanSanctionDate;
    }

    public RepaymentFrequency getRepaymentFrequency() {
        return repaymentFrequency;
    }

    public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
        this.repaymentFrequency = repaymentFrequency;
    }

    public Integer getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(Integer numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCifNumber() {
        return cifNumber;
    }

    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }


    @Override
    public String toString() {
        return "JasperEntity{" +
                "loanAccountNumber='" + loanAccountNumber + '\'' +
                ", disbursalDate=" + disbursalDate +
                ", loanAmountDisbursed=" + loanAmountDisbursed +
                ", originalEmiAmount=" + originalEmiAmount +
                ", loanAmountPaidByCustomer=" + loanAmountPaidByCustomer +
                ", productType=" + productType +
                ", originalTenure=" + originalTenure +
                ", interestRate=" + interestRate +
                ", branch='" + branch + '\'' +
                ", loanProduct=" + loanProduct +
                ", finalSanctionedAmount=" + finalSanctionedAmount +
                ", loanSanctionDate=" + loanSanctionDate +
                ", repaymentFrequency=" + repaymentFrequency +
                ", numberOfInstallment=" + numberOfInstallment +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", emailAddress='" + emailAddress + '\'' +
                ", cifNumber='" + cifNumber + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pinCode=" + pinCode +
                ", contactNumber=" + contactNumber +
                '}';
    }
}
