//package org.nucleus.entity.temporary;
//
//import lombok.Data;
//import org.nucleus.entity.meta.MetaData;
//import org.nucleus.entity.meta.TempMetaData;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Data
//@Entity
//@Table(name = "ALLOCATION_TEMP_TBL_BATCH_6")
//@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "ALLOCATION_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
//public class AllocationTemp {
//    @Id
//    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
//    private Long allocationId;
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    private LoanAccountTemp loanAccount;
//
//    private Date depositDate;
//    private Double interestComponentReceived;
//    private Double principalComponentReceived;
//    private Double penaltyCharge;
//    private String penaltyDescription;
//    @Embedded
//    private TempMetaData metaData;
//}