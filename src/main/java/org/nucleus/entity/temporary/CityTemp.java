package org.nucleus.entity.temporary;

import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.State;

import javax.persistence.*;


@Entity
@Table(name = "CITY_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "CITY_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class CityTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Integer cityId;
    @ManyToOne
    @JoinColumn(name="id")
    private State state;
    private String cityName;
    private String cityCode;
    private String stdCode;
    private String cityMICRCode;
    private String locationType;
    @Embedded
    private TempMetaData metaData;
    public CityTemp(){
        metaData = new TempMetaData();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public TempMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TempMetaData metaData) {
        this.metaData = metaData;
    }
}
