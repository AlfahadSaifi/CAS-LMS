package org.nucleus.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.nucleus.entity.meta.MetaData;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

//AUTHOR: HARKIRAT SINGH
public class ChargeDefinitionDTO {
    private String chargeDefinitionCode;
    @NotBlank(message ="Charge Name is required")
    @Pattern(regexp = "[a-zA-Z ]+",message = "Charge Name Must Only Contains Alphabets ")
    private String chargeName;
    private String description;
    @DecimalMin(value = "0.00", message = "Minimum amount must be greater than or equal to 0.00")
    @DecimalMax(value = "999999.99", message = "Maximum amount must be less than or equal to 999999.99")
    private Double maximumAmount;
    @DecimalMax(value = "999999.99", message = "Maximum amount must be less than or equal to 999999.99")
    @DecimalMin(value = "0.00", message = "Minimum amount must be greater than or equal to 0.00")
    private Double minimumAmount;
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

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
