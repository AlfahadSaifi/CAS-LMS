package org.nucleus.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;


@Component
public class ChargePolicyTempDto {

    private Boolean saveFlag;
    @NotEmpty(message = "Policy can't be empty")
    @Pattern(regexp = "^[0-9a-zA-Z]+",message = "Invalid Policy Code: Policy code must contain only alphanumeric characters.")
    private String policyCode;
    @NotEmpty(message = "Policy Name can't be empty")
    @Pattern(regexp = "[a-zA-Z\\s]*",message = "Invalid Policy Name: Policy Name must only contain alphabets")
    private String policyName;


    private RecordStatus recordStatus;
    private Boolean activeInactiveFlag;
    private Date creationDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private Date authorizedDate;
    private String authorizedBy;


        private List<ChargePolicyParameterTempDto> chargePolicyParameterList;


        public ChargePolicyTempDto()
        {}

    public Boolean getSaveFlag() {
        return saveFlag;
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

    public void setSaveFlag(Boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

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

        public List<ChargePolicyParameterTempDto> getChargePolicyParameterList() {
            return chargePolicyParameterList;
        }

        public void setChargePolicyParameterList(List<ChargePolicyParameterTempDto> chargePolicyParameterList) {
            this.chargePolicyParameterList = chargePolicyParameterList;
        }

    @Override
    public String toString() {
        return "ChargePolicyTempDto{" +
                "flagForEdit=" + saveFlag +
                ", policyCode='" + policyCode + '\'' +
                ", policyName='" + policyName + '\'' +
                ", recordStatus=" + recordStatus +
                ", activeInactiveFlag=" + activeInactiveFlag +
                ", creationDate=" + creationDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", authorizedDate=" + authorizedDate +
                ", authorizedBy='" + authorizedBy + '\'' +
                ", chargePolicyParameterList=" + chargePolicyParameterList +
                '}';
    }
}


