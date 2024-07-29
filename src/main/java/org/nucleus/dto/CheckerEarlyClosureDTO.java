package org.nucleus.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.utility.enums.ClosureStatus;

import javax.persistence.Embedded;
import java.sql.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
public class CheckerEarlyClosureDTO
{
    private Long loanClosureId;
    private String loanAccountNumber;
    private Date loanClosureDate;
    private ClosureStatus closureStatus;
    private Double loanAmountPaidByCustomer;
    private Double finalSanctionedAmount;
    private Date dueDate;
    @Embedded
    MetaData metaData;

}
