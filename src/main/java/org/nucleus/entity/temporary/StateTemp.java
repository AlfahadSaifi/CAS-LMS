package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "STATE_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "STATE_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class StateTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Integer id;
    private String stateCode;
    private String stateName;
    private String countryName;
    private String region;
    private Boolean isUnionTerritory;
    @Embedded
    private TempMetaData metaDataTemp;
    public StateTemp(){
        this.metaDataTemp = new TempMetaData();
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
}
