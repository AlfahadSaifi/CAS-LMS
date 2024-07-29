package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.temporary.LoanApplicationTemp;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "REPAY_SCHEDULE_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "REPAY_SCHEDULE_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class RepaySchedule {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
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
