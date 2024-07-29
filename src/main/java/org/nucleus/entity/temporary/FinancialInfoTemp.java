package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FINANCIAL_INFO_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "FINANCIAL_INFO_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class FinancialInfoTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long financialId;
    private Double monthlyIncome;
    private Double rentIncome;
    private Double monthlyExpense;
    private Double medicalExpense;
}