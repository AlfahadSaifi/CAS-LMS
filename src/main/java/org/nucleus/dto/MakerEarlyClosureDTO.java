package org.nucleus.dto;



import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nucleus.entity.meta.TempMetaData;

import java.sql.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
public class MakerEarlyClosureDTO
{
    private String loanAccountNumber;
    private String customerName;
    private Integer balanceInstallment;
    private Double loanAmount;
    private Double balancePrinciple;
    private Double totalDuePrincipal;
    private Date closureDate;
    private Double dueCharges;
    private Double totalClosureAmount;
    private TempMetaData tempMetaData;

}
