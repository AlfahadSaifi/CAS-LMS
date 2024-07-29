package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "REPAY_SCHEDULE_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "REPAY_SCHEDULE_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class RepayScheduleTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long id;
    private Double effectiveInterestRate;
    private Double installmentAmount;
    private Integer installmentNumber;
    private Double openingBalance;
    private Double outstandingBalancePrincipal;
    private Double principalComponent;
    private Date dueDate;
    private Double interestComponent;
}
