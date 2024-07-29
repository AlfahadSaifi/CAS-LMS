package org.nucleus.dto;


import org.nucleus.utility.enums.ProfessionType;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class OccupationInfoDTO {

    private Long occupationId;

    @NotNull(message = "Age of retirement is required")
    @Min(value = 0, message = "Value must be greater than or equal to 0")
    private Integer ageOfRetirement;

    private Integer totalYearOfExperience;

    @NotNull(message = "Profession type is required")
    @Enumerated(EnumType.STRING)
    private ProfessionType professionType;

    private String designation;

    @NotBlank(message = "Organization name is required")
    private String organizationName;

    private String organizationLocation;

    public Long getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(Long occupationId) {
        this.occupationId = occupationId;
    }

    public Integer getAgeOfRetirement() {
        return ageOfRetirement;
    }

    public void setAgeOfRetirement(Integer ageOfRetirement) {
        this.ageOfRetirement = ageOfRetirement;
    }

    public Integer getTotalYearOfExperience() {
        return totalYearOfExperience;
    }

    public void setTotalYearOfExperience(Integer totalYearOfExperience) {
        this.totalYearOfExperience = totalYearOfExperience;
    }

    public ProfessionType getProfessionType() {
        return professionType;
    }

    public void setProfessionType(ProfessionType professionType) {
        this.professionType = professionType;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationLocation() {
        return organizationLocation;
    }

    public void setOrganizationLocation(String organizationLocation) {
        this.organizationLocation = organizationLocation;
    }

    @Override
    public String toString() {
        return "OccupationInfo{" +
                "occupationId=" + occupationId +
                ", ageOfRetirement=" + ageOfRetirement +
                ", totalYearOfExperience=" + totalYearOfExperience +
                ", professionType=" + professionType +
                ", designation='" + designation + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", organizationLocation='" + organizationLocation + '\'' +
                '}';
    }
}