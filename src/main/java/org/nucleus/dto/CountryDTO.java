package org.nucleus.dto;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.nucleus.entity.meta.TempMetaData;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CountryDTO {

    private Integer id;
    @NotBlank(message = "Country group is required")
    private String countryGroup;
    @NotBlank(message = "Country name is required")
    @Pattern(regexp = "[a-zA-Z ]+",message = "Country name must only contains alphabets ")
    private String countryName;
    @Digits(integer = 10 ,fraction = 0 , message ="")
    @NotNull(message = "ISD Code cannot be empty")
    private Integer countryIsdCode;
    @NotBlank(message = "Country ISO code is required")
    @Pattern(regexp = "[a-zA-Z ]+",message = "Country ISO code must only contains alphabets ")
    private String countryIsoCode;
    @NotBlank(message = "This field cannot be empty.")
    private String nationality;
    private String region;
    private String status;
    private TempMetaData metaData;

    public CountryDTO(){
        this.metaData = new TempMetaData();
    }
    public TempMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TempMetaData metaData) {
        this.metaData = metaData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryGroup() {
        return countryGroup;
    }

    public void setCountryGroup(String countryGroup) {
        this.countryGroup = countryGroup;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountryIsdCode() {
        return countryIsdCode;
    }

    public void setCountryIsdCode(Integer countryIsdCode) {
        this.countryIsdCode = countryIsdCode;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id"+ id + '\''+
                "countryGroup='" + countryGroup + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryIsdCode=" + countryIsdCode +
                ", countryIsoCode='" + countryIsoCode + '\'' +
                ", nationality='" + nationality + '\'' +
                ", region='" + region + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
