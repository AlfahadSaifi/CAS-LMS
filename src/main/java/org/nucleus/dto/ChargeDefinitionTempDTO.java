package org.nucleus.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.nucleus.entity.meta.TempMetaData;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

//AUTHOR: HARKIRAT SINGH
public class ChargeDefinitionTempDTO {


    //@Pattern(regexp = "^[A-Z1-9]*$", message = "Charge Definition Code must contain only capital letters and numbers 1 to 9")
    //@Length(min = 5,max = 10,message = "Length of ChargeDefinitionCode Must be between 5-10")
    @NotBlank(message = "Charge Code is required")
    private String chargeDefinitionCode;
    @NotBlank(message ="Charge Name is required")
    @Pattern(regexp = "[a-zA-Z ]+",message = "Charge Name Must Only Contains Alphabets ")
    private String chargeName;
    private String description;

    @DecimalMax(value = "999999.99", message = "Amount must be less than or equal to 999999.99")
    @DecimalMin(value = "0.00", message = "Amount must be greater than or equal to 0.00")
    private Double maximumAmount;

    @DecimalMin(value = "0.00", message = "Amount must be greater than or equal to 0.00")
    @DecimalMax(value = "999999.99", message = "Amount must be less than or equal to 999999.99")
    private Double minimumAmount;
    private TempMetaData tempMetaData;
    public TempMetaData getMetaData() {
        return tempMetaData;
    }

    public void setMetaData(TempMetaData tempMetaData) {
        this.tempMetaData = tempMetaData;
    }//Setting Temp Data


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaximumAmount(Double maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }
    public String getChargeDefinitionCode() {
        return chargeDefinitionCode;
    }

    public void setChargeDefinitionCode(String chargeDefinitionCode) {
        this.chargeDefinitionCode = chargeDefinitionCode;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public Double getMaximumAmount() {
        return maximumAmount;
    }

    public Double getMinimumAmount() {
        return minimumAmount;
    }

    @Override
    public String toString() {
        return "ChargeDefinition{" +
                ", chargeDefinitionCode='" + chargeDefinitionCode + '\'' +
                ", chargeName='" + chargeName + '\'' +
                ", description='" + description + '\'' +
                ", maximumAmount=" + maximumAmount +
                ", minimumAmount=" + minimumAmount +
                '}';
    }
}
