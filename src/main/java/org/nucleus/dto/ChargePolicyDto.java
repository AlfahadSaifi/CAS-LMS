package org.nucleus.dto;

import org.nucleus.utility.enums.RecordStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;


@Component
public class ChargePolicyDto {
        private String policyCode;
        private String policyName;
        private RecordStatus recordStatus;
        private Boolean activeInactiveFlag;
        private Date creationDate;
        private String createdBy;
        private Date modifiedDate;
        private String modifiedBy;
        private Date authorizedDate;
        private String authorizedBy;

        private List<ChargePolicyParameterDto> chargePolicyParameterList;


        public ChargePolicyDto()
        {}



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

        public List<ChargePolicyParameterDto> getChargePolicyParameterList() {
            return chargePolicyParameterList;
        }

        public void setChargePolicyParameterList(List<ChargePolicyParameterDto> chargePolicyParameterList) {
            this.chargePolicyParameterList = chargePolicyParameterList;
        }

    @Override
    public String toString() {
        return "ChargePolicyDto{" +
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


