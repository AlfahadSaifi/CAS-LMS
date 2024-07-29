package org.nucleus.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.LoanSecurityType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoanProductDTO {
    private Long productId;
    @NotEmpty(message = "Product code cannot be left empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$" , message = "Product code cannot have special characters and space")
    private String productCode;
    @NotEmpty(message = "Product name cannot be left empty")
    @Pattern(regexp = "^[a-zA-Z_ ]+$" ,message = "Product name must have alphabets")
    private String productName;
    @NotEmpty(message = "Product description cannot be left empty")
    @Size(max = 100, message = "Product description can have at most 100 characters")
    private String productDescription;  // Y or N
    private LoanSecurityType securityType;

    @DecimalMin(value = "1.0",message = "Rate of Interest cannot be less than zero or equal to zero")
    @NotNull(message = "Rate of Interest cannot be left empty")
    private Double rateOfInterest;
    private String productTypeCode;
    private String repaymentPolicyCode;
    private String chargePolicyCode;
    private String eligibilityPolicyCode;
    private TempMetaData metaData;


    public LoanProductDTO() {
        metaData = new TempMetaData();
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getRepaymentPolicyCode() {
        return repaymentPolicyCode;
    }

    public void setRepaymentPolicyCode(String repaymentPolicyCode) {
        this.repaymentPolicyCode = repaymentPolicyCode;
    }

    public TempMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TempMetaData metaData) {
        this.metaData = metaData;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public LoanSecurityType getSecurityType() {
        return securityType;
    }

    public void setSecurityType(LoanSecurityType securityType) {
        this.securityType = securityType;
    }

    public Double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(Double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public String getChargePolicyCode() {
        return chargePolicyCode;
    }

    public void setChargePolicyCode(String chargePolicyCode) {
        this.chargePolicyCode = chargePolicyCode;
    }

    public String getEligibilityPolicyCode() {
        return eligibilityPolicyCode;
    }

    public void setEligibilityPolicyCode(String eligibilityPolicyCode) {
        this.eligibilityPolicyCode = eligibilityPolicyCode;
    }

    @Override
    public String toString() {
        return "LoanProductDTO{" +
                "productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", securityType=" + securityType +
                ", rateOfInterest=" + rateOfInterest +
                ", productTypeCode='" + productTypeCode + '\'' +
                ", repaymentPolicyCode='" + repaymentPolicyCode + '\'' +
                ", chargePolicyCode='" + chargePolicyCode + '\'' +
                ", eligibilityPolicyCode='" + eligibilityPolicyCode + '\'' +
                ", metaData=" + metaData +
                '}';
    }
}
