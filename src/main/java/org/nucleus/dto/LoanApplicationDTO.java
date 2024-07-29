package org.nucleus.dto;

import org.nucleus.utility.enums.LoanStatus;
import org.nucleus.utility.enums.RecordStatus;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

public class LoanApplicationDTO {

    private Long loanId;
    private String loanApplicationId;
    private Date applicationCreationDate;
    @NotNull(message = "Loan amount must not be null")
    @DecimalMin(value = "0.01", message = "Loan amount must be greater than 0")
    private Double loanAmount;

    @NotNull(message = "Tenure must not be null")
    @Min(value = 1, message = "Tenure must be greater than 0")
    private Integer tenure;
    private String loanAccountNumber;
    private String applicationPurpose;
    private LoanProductDTO loanProductDTO;

    @NotNull(message = "Branch must not be null")
    @Size(min = 1, max = 50, message = "Branch name must be between 1 and 50 characters")
    private String branch;
    private String tenureIn;
    private Double rate;
    private LoanStatus loanStatus = LoanStatus.PENDING;
    private CustomerDTO customerDTO;

    // meta data
    private RecordStatus recordStatus;
    private Boolean activeInactiveFlag;
    private Date creationDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private Date authorizedDate;
    private String authorizedBy;
    private Boolean saveFlag = Boolean.FALSE;

    public LoanApplicationDTO() {
    }


    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(String loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public Date getApplicationCreationDate() {
        return applicationCreationDate;
    }

    public void setApplicationCreationDate(Date applicationCreationDate) {
        this.applicationCreationDate = applicationCreationDate;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public String getApplicationPurpose() {
        return applicationPurpose;
    }

    public void setApplicationPurpose(String applicationPurpose) {
        this.applicationPurpose = applicationPurpose;
    }

    public LoanProductDTO getLoanProductDTO() {
        return loanProductDTO;
    }

    public void setLoanProductDTO(LoanProductDTO loanProductDTO) {
        this.loanProductDTO = loanProductDTO;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getTenureIn() {
        return tenureIn;
    }

    public void setTenureIn(String tenureIn) {
        this.tenureIn = tenureIn;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Boolean getActiveInactiveFlag() {
        return activeInactiveFlag;
    }

    public void setActiveInactiveFlag(Boolean activeInactiveFlag) {
        this.activeInactiveFlag = activeInactiveFlag;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getAuthorizedDate() {
        return authorizedDate;
    }

    public void setAuthorizedDate(Date authorizedDate) {
        this.authorizedDate = authorizedDate;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public Boolean getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    @Override
    public String toString() {
        return "LoanApplicationDTO{" +
                "loanId=" + loanId +
                ", loanApplicationId='" + loanApplicationId + '\'' +
                ", applicationCreationDate=" + applicationCreationDate +
                ", loanAmount=" + loanAmount +
                ", tenure=" + tenure +
                ", loanAccountNumber='" + loanAccountNumber + '\'' +
                ", applicationPurpose='" + applicationPurpose + '\'' +
                ", loanProductDTO=" + loanProductDTO +
                ", branch='" + branch + '\'' +
                ", tenureIn='" + tenureIn + '\'' +
                ", rate=" + rate +
                ", loanStatus=" + loanStatus +
                ", customerDTO=" + customerDTO +
                ", recordStatus=" + recordStatus +
                ", activeInactiveFlag=" + activeInactiveFlag +
                ", creationDate=" + creationDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", authorizedDate=" + authorizedDate +
                ", authorizedBy='" + authorizedBy + '\'' +
                ", saveFlag=" + saveFlag +
                '}';
    }
}
