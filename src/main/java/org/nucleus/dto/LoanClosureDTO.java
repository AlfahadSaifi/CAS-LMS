package org.nucleus.dto;

import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.permanent.LoanAccount;
import org.nucleus.utility.enums.ClosureStatus;

import javax.persistence.Embedded;
import java.sql.Date;

public class LoanClosureDTO
{
    private Long loanClosureId;
    private Date loanClosureDate;
    private ClosureStatus closureStatus;
    private LoanAccountDTO loanAccountDTO;
    @Embedded
    private MetaData metaData;
    public MetaData getMetaData()
    {
        return metaData;
    }
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
    public Long getLoanClosureId()
    {
        return loanClosureId;
    }
    public void setLoanClosureId(Long loanClosureId) {
        this.loanClosureId = loanClosureId;
    }
    public Date getLoanClosureDate()
    {
        return loanClosureDate;
    }
    public void setLoanClosureDate(Date loanClosureDate)
    {
        this.loanClosureDate = loanClosureDate;
    }
    public ClosureStatus getClosureStatus()
    {
        return closureStatus;
    }
    public void setClosureStatus(ClosureStatus closureStatus)
    {
        this.closureStatus = closureStatus;
    }
    public LoanAccountDTO getLoanAccountDTO()
    {
        return loanAccountDTO;
    }
    public void setLoanAccountDTO(LoanAccountDTO loanAccountDTO)
    {
        this.loanAccountDTO = loanAccountDTO;
    }

    @Override
    public String toString() {
        return "LoanClosureDTO{" +
                "loanClosureId=" + loanClosureId +
                ", loanClosureDate=" + loanClosureDate +
                ", closureStatus=" + closureStatus +
                ", loanAccount=" + loanAccountDTO +
                ", metaData=" + metaData +
                '}';
    }
}