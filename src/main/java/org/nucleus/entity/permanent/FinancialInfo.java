package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FINANCIAL_INFO_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "FINANCIAL_INFO_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class FinancialInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Long financialId;

    private Double monthlyIncome;
    private Double rentIncome;
    private Double monthlyExpense;
    private Double medicalExpense;

    @OneToOne
    private Customer customer;
    @Embedded
    private MetaData metaData;
}