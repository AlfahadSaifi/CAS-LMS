package org.nucleus.dto;

import org.nucleus.entity.meta.MetaData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EligibilityPolicyDTO {

    private Long eligibilityPolicyId;
    private String policyCode;
    private String policyName;
    private String eligibilityCriteria;
    private List<EligibilityPolicyParameterDTO> eligibilityParameterMstDTOS;
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Long getEligibilityPolicyId() {
        return eligibilityPolicyId;
    }

    public void setEligibilityPolicyId(Long eligibilityPolicyId) {
        this.eligibilityPolicyId = eligibilityPolicyId;
    }

    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }



    public List<EligibilityPolicyParameterDTO> getEligibilityParameterMstDTOS() {
        return eligibilityParameterMstDTOS;
    }

    public void setEligibilityParameterMstDTOS(List<EligibilityPolicyParameterDTO> eligibilityParameterMstDTOS) {
        this.eligibilityParameterMstDTOS = eligibilityParameterMstDTOS;
    }

    @Override
    public String toString() {
        return "EligibilityPoliciesMstDTO{" +
                "eligibilityPolicyId=" + eligibilityPolicyId +
                ", policyCode='" + policyCode + '\'' +
                ", eligibilityCriteria='" + eligibilityCriteria + '\'' +
                ", eligibilityParameterMstDTOS=" + eligibilityParameterMstDTOS +
                ", metaData=" + metaData +
                '}';
    }
}
