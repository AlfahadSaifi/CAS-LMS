package org.nucleus.dto;

import org.nucleus.entity.meta.TempMetaData;
import org.springframework.stereotype.Component;
import javax.validation.constraints.*;
import java.util.Objects;


@Component
public class EligibilityPolicyParameterDefinitionDTO {

    private Long eligibilityParameterId;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "parameter code must not contain special characters")
    private String eligibilityParameterCode;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "parameter name must not contain special characters")

    private String eligibilityParameterName;
    private String eligibilityParameterDescription;
    private TempMetaData tempMetaData;

    public TempMetaData getTempMetaData() {
        return tempMetaData;
    }

    public void setTempMetaData(TempMetaData tempMetaData) {
        this.tempMetaData = tempMetaData;
    }

    public EligibilityPolicyParameterDefinitionDTO() {
    }

    public Long getEligibilityParameterId() {
        return eligibilityParameterId;
    }

    public void setEligibilityParameterId(Long eligibilityParameterId) {
        this.eligibilityParameterId = eligibilityParameterId;
    }

    public String getEligibilityParameterCode() {
        return eligibilityParameterCode;
    }

    public void setEligibilityParameterCode(String eligibilityParameterCode) {
        this.eligibilityParameterCode = eligibilityParameterCode;
    }

    public String getEligibilityParameterName() {
        return eligibilityParameterName;
    }

    public void setEligibilityParameterName(String eligibilityParameterName) {
        this.eligibilityParameterName = eligibilityParameterName;
    }

    public String getEligibilityParameterDescription() {
        return eligibilityParameterDescription;
    }

    public void setEligibilityParameterDescription(String eligibilityParameterDescription) {
        this.eligibilityParameterDescription = eligibilityParameterDescription;
    }

    @Override
    public String toString() {
        return "EligibilityParameterDTO{" +
                "eligibilityParameterId=" + eligibilityParameterId +
                ", eligibilityParameterCode='" + eligibilityParameterCode + '\'' +
                ", eligibilityParameterName='" + eligibilityParameterName + '\'' +
                ", eligibilityParameterDescription='" + eligibilityParameterDescription + '\'' +
                ", tempMetaData=" + tempMetaData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EligibilityPolicyParameterDefinitionDTO that = (EligibilityPolicyParameterDefinitionDTO) o;
        return Objects.equals(eligibilityParameterCode, that.eligibilityParameterCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eligibilityParameterCode);
    }
}
