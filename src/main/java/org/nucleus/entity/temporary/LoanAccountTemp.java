package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.LoanStatus;
import org.nucleus.utility.enums.RepaymentFrequency;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "LOAN_ACCOUNT_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "LOAN_ACCOUNT_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class LoanAccountTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long loanAccountId;
    private String loanAccountNumber;
    @OneToOne
    private LoanApplicationTemp loanApplication;
    private Date loanSanctionDate;
    private Date disbursalDate;
    private String cifNumber;
    private Double finalSanctionedAmount;
    private Double loanAmountDisbursed;
    private Long loanProduct;
    private Long productType;
    private Double loanAmountPaidByCustomer;
    private Integer numberOfInstallment;
    private Integer numberOfInstallmentBilled;
    private Integer numberOfInstallmentUnbilled;
    private Double originalEmiAmount;
    private Integer originalTenure;
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    private RepaymentFrequency repaymentFrequency;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;
    @Embedded
    private TempMetaData metaData;
}
