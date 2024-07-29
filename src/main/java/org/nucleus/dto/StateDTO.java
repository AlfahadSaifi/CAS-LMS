package org.nucleus.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.nucleus.entity.meta.TempMetaData;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Pattern;

@Component
public class StateDTO {
    private Integer id;
    @NotBlank(message = "State Code can't be Empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "No Special Character allowed")
    private String stateCode;
    @NotBlank(message = "State Name can't be Empty ")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only Alphabets allowed")
    private String stateName;
    private CountryDTO country;
    private String region;
    private Boolean isUnionTerritory;
    private TempMetaData metaDataTemp;
    public StateDTO()
    {
        this.metaDataTemp = new TempMetaData();
        this.country = new CountryDTO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getUnionTerritory() {
        return isUnionTerritory;
    }

    public void setUnionTerritory(Boolean unionTerritory) {
        isUnionTerritory = unionTerritory;
    }

    public TempMetaData getMetaDataTemp() {
        return metaDataTemp;
    }
    public void setMetaDataTemp(TempMetaData metaDataTemp) {
        this.metaDataTemp = metaDataTemp;
    }

    @Override
    public String toString() {
        return "StateDTO{" +
                "id=" + id +
                ", stateCode='" + stateCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", country=" + country +
                ", region='" + region + '\'' +
                ", isUnionTerritory=" + isUnionTerritory +
                ", metaDataTemp=" + metaDataTemp +
                '}';
    }
}
