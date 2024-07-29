package org.nucleus.dto;


import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.temporary.EligibilityPolicyParameterTemp;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EligibilityPolicyTempDTO {
    private Long id;
    private String policyCode;
    private String policyName;
    private String eligibilityCriteria;
//    private String from;

    private List<EligibilityPolicyParameterTemp> eligibilityParameterMappingList;
    private RecordStatus flag;
    private TempMetaData metaData;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
//    public String getFrom() {
//        return from;
//    }

//    public void setFrom(String from) {
//        this.from = from;
//    }

    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public RecordStatus getFlag() {
        return flag;
    }

    public void setFlag(RecordStatus flag) {
        this.flag = flag;
    }

    public List<EligibilityPolicyParameterTemp> getEligibilityParameterMappingList() {
        return eligibilityParameterMappingList;
    }

    public void setEligibilityParameterMappingList(List<EligibilityPolicyParameterTemp> eligibilityParameterMappingList) {
        this.eligibilityParameterMappingList = eligibilityParameterMappingList;
    }

    public TempMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TempMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "EligibilityPolicies{" +
                "policyCode='" + policyCode + '\'' +
                ", policyName='" + policyName + '\'' +
                ", eligibilityCriteria='" + eligibilityCriteria + '\'' +
                ", eligibilityParameterMappingList=" + eligibilityParameterMappingList +
                '}';
    }
}
