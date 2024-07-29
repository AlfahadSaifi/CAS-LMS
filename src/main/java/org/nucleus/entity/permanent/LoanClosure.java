package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.utility.enums.ClosureStatus;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "LOAN_CLOSURE_TBL_BATCH_6")
@TableGenerator(name = "ID_TABLE_GEN_BATCH_6", pkColumnValue = "LOAN_CLOSURE_TBL_BATCH_6", initialValue = 100000, allocationSize = 1)
public class LoanClosure {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Long loanClosureId;
    private Date loanClosureDate;
    @Enumerated(EnumType.STRING)
    private ClosureStatus closureStatus;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loanId")
    private LoanAccount loanAccount;
    @Embedded
    private MetaData metaData;
}
