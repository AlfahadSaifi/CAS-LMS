package org.nucleus.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.nucleus.entity.meta.TempMetaData;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
public class CityDTO {
    private Integer id;
    private StateDTO stateDto;

    @NotBlank(message = "City Name can't be Empty ")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only Alphabets allowed")
    private String cityName;
    @NotBlank(message = "City Code can't be Empty ")
    private String cityCode;
    //validations
    private String stdCode;
    //validations
    private String cityMICRCode;
    private TempMetaData metaData;
    private String locationType;

    public CityDTO(){
        stateDto=new StateDTO();
        this.metaData = new TempMetaData();
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public StateDTO getStateDto() {
        return stateDto;
    }

    public void setStateDto(StateDTO stateDto) {
        this.stateDto = stateDto;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public String getCityMICRCode() {
        return cityMICRCode;
    }

    public void setCityMICRCode(String cityMICRCode) {
        this.cityMICRCode = cityMICRCode;
    }

    public TempMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TempMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "CityDTO{" +
                "id=" + id +
                ", stateDto=" + stateDto +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", stdCode='" + stdCode + '\'' +
                ", cityMICRCode='" + cityMICRCode + '\'' +
                ", metaData=" + metaData +
                ", locationType='" + locationType + '\'' +
                '}';
    }
}