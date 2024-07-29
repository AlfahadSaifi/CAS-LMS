//package org.nucleus.utility;
//
//import org.nucleus.entity.meta.MetaData;
//import org.nucleus.entity.meta.TempMetaData;
//import org.nucleus.utility.enums.RecordStatus;
//
//public class CurrentMakerMetaData {
//    public static TempMetaData getTempMetaDataForCreation(){
//        TempMetaData tempMetaData = new TempMetaData();
//
//        tempMetaData.setRecordStatus(RecordStatus.N);
//        tempMetaData.setActiveInactiveFlag(Boolean.TRUE);
//
////        tempMetaData.setCreationDate();
////        tempMetaData.setCreatedBy();
////        tempMetaData.setSaveOrSaveNext();
//
//        return tempMetaData;
//    }
//    public static TempMetaData getTempMetaDataForModification(TempMetaData tempMetaData){
//
//        tempMetaData.setRecordStatus(RecordStatus.M);
////        tempMetaData.setActiveInactiveFlag();
//
////        tempMetaData.setModifiedDate();
////        tempMetaData.setModifiedBy();
//
////        tempMetaData.setSaveOrSaveNext();
//
//        return tempMetaData;
//    }
//    public static TempMetaData getTempMetaDataForDeletion(TempMetaData tempMetaData){
//        tempMetaData.setRecordStatus(RecordStatus.D);
////        tempMetaData.setActiveInactiveFlag();
////        tempMetaData.setModifiedDate();
////        tempMetaData.setModifiedBy();
////        tempMetaData.setSaveOrSaveNext();
//
//        return tempMetaData;
//    }
//    public static TempMetaData getMetaDataForRejectedCreation(TempMetaData tempMetaData){
//        tempMetaData.setRecordStatus(RecordStatus.NR);
//
////        metaData.setModifiedDate();
////        metaData.setModifiedBy();
//
//        return tempMetaData;
//    }
//    public static TempMetaData getMetaDataForRejectedModification(TempMetaData tempMetaData){
//        tempMetaData.setRecordStatus(RecordStatus.MR);
//
////        metaData.setModifiedDate();
////        metaData.setModifiedBy();
//
//        return tempMetaData;
//    }
//    public static TempMetaData getMetaDataForRejectedDeletion(TempMetaData tempMetaData){
//        tempMetaData.setRecordStatus(RecordStatus.DR);
//
////        metaData.setModifiedDate();
////        metaData.setModifiedBy();
//
//        return tempMetaData;
//    }
//}
