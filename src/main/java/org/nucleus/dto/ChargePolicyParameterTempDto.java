package org.nucleus.dto;


import org.nucleus.utility.enums.Operator;

public class ChargePolicyParameterTempDto {
    private Long chargePolicyParameterId;

    private String chargeDefinitionCode;

    private Operator operator;
    private Double value;


    public Long getChargePolicyParameterId() {
        return chargePolicyParameterId;
    }

    public void setChargePolicyParameterId(Long chargePolicyParameterId) {
        this.chargePolicyParameterId = chargePolicyParameterId;
    }

    public String getChargeDefinitionCode() {
        return chargeDefinitionCode;
    }

    public void setChargeDefinitionCode(String chargeDefinitionCode) {
        this.chargeDefinitionCode = chargeDefinitionCode;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ChargePolicyParameterTempDto{" +
                "chargePolicyParameterId=" + chargePolicyParameterId +
                ", chargeDefinitionCode='" + chargeDefinitionCode + '\'' +
                ", operator=" + operator +
                ", value='" + value + '\'' +
                '}';
    }
}
