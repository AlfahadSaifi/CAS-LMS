package org.nucleus.utility.dtomapper;

import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;

public class MetaDataMapper {

    public static MetaData convertToMetaData(TempMetaData tempMetaData) {
        MetaData metaData = new MetaData();
        if(tempMetaData != null) {
            metaData.setRecordStatus(tempMetaData.getRecordStatus());
            metaData.setActiveInactiveFlag(tempMetaData.getActiveInactiveFlag());
            metaData.setCreationDate(tempMetaData.getCreationDate());
            metaData.setCreatedBy(tempMetaData.getCreatedBy());
            metaData.setModifiedDate(tempMetaData.getModifiedDate());
            metaData.setModifiedBy(tempMetaData.getModifiedBy());
            metaData.setAuthorizedDate(tempMetaData.getAuthorizedDate());
            metaData.setAuthorizedBy(tempMetaData.getAuthorizedBy());
            return metaData;
        }
        return null;
    }
    public static MetaData tempToMetaData(TempMetaData tempMetaData) {
        MetaData metaData = new MetaData();
        if(tempMetaData != null) {
            metaData.setRecordStatus(tempMetaData.getRecordStatus());
            metaData.setActiveInactiveFlag(tempMetaData.getActiveInactiveFlag());
            metaData.setCreationDate(tempMetaData.getCreationDate());
            metaData.setCreatedBy(tempMetaData.getCreatedBy());
            metaData.setModifiedDate(tempMetaData.getModifiedDate());
            metaData.setModifiedBy(tempMetaData.getModifiedBy());
            metaData.setAuthorizedDate(tempMetaData.getAuthorizedDate());
            metaData.setAuthorizedBy(tempMetaData.getAuthorizedBy());
            return metaData;
        }
        return null;
    }

    public static TempMetaData convertToTempMetaData(MetaData metaData) {
        TempMetaData tempMetaData = new TempMetaData();
        if(metaData != null) {
            tempMetaData.setRecordStatus(metaData.getRecordStatus());
            tempMetaData.setActiveInactiveFlag(metaData.getActiveInactiveFlag());
            tempMetaData.setCreationDate(metaData.getCreationDate());
            tempMetaData.setCreatedBy(metaData.getCreatedBy());
            tempMetaData.setModifiedDate(metaData.getModifiedDate());
            tempMetaData.setModifiedBy(metaData.getModifiedBy());
            tempMetaData.setAuthorizedDate(metaData.getAuthorizedDate());
            tempMetaData.setAuthorizedBy(metaData.getAuthorizedBy());
            return tempMetaData;
        }
        return null;
    }

    public static TempMetaData metaDataToTemp(MetaData metaData) {
        TempMetaData tempMetaData = new TempMetaData();
        if(metaData != null) {
            tempMetaData.setRecordStatus(metaData.getRecordStatus());
            tempMetaData.setActiveInactiveFlag(metaData.getActiveInactiveFlag());
            tempMetaData.setCreationDate(metaData.getCreationDate());
            tempMetaData.setCreatedBy(metaData.getCreatedBy());
            tempMetaData.setModifiedDate(metaData.getModifiedDate());
            tempMetaData.setModifiedBy(metaData.getModifiedBy());
            tempMetaData.setAuthorizedDate(metaData.getAuthorizedDate());
            tempMetaData.setAuthorizedBy(metaData.getAuthorizedBy());
            return tempMetaData;
        }
        return null;
    }
}

