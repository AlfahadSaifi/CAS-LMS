package org.nucleus.dto;

import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.LoanAccount;
import org.nucleus.utility.enums.Currency;
import org.nucleus.utility.enums.ReceivablePayableStatus;
import org.nucleus.utility.enums.ReceivablePayableType;
import org.nucleus.utility.enums.RecordStatus;

import java.sql.Date;

public class ReceivablePayableDTO {

    Long receivablePayableId;

    Currency currency;

    ReceivablePayableType receivablePayableType;
    Date receivablePayableDueDate;

    LoanAccountDTO loanAccount;

    Double receivablePayableAmount;
    Double principalComponent;
    Double principalComponentReceived;
    Double interestComponent;
    Double interestComponentReceived;
    Date receivablePayableDate;
    ReceivablePayableStatus receivablePayableStatus;

    Double penalty;

    public TempMetaData getTempMetaData() {
        return tempMetaData;
    }

    public void setTempMetaData(TempMetaData tempMetaData) {
        this.tempMetaData = tempMetaData;
    }

    private TempMetaData tempMetaData;


    public Long getReceivablePayableId() {
        return receivablePayableId;
    }

    public void setReceivablePayableId(Long receivablePayableId) {
        this.receivablePayableId = receivablePayableId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public ReceivablePayableType getReceivablePayableType() {
        return receivablePayableType;
    }

    public void setReceivablePayableType(ReceivablePayableType receivablePayableType) {
        this.receivablePayableType = receivablePayableType;
    }

    public Date getReceivablePayableDueDate() {
        return receivablePayableDueDate;
    }

    public void setReceivablePayableDueDate(Date receivablePayableDueDate) {
        this.receivablePayableDueDate = receivablePayableDueDate;
    }

    public LoanAccountDTO getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(LoanAccountDTO loanAccount) {
        this.loanAccount = loanAccount;
    }

    public Double getReceivablePayableAmount() {
        return receivablePayableAmount;
    }

    public void setReceivablePayableAmount(Double receivablePayableAmount) {
        this.receivablePayableAmount = receivablePayableAmount;
    }

    public Double getPrincipalComponent() {
        return principalComponent;
    }

    public void setPrincipalComponent(Double principalComponent) {
        this.principalComponent = principalComponent;
    }

    public Double getPrincipalComponentReceived() {
        return principalComponentReceived;
    }

    public void setPrincipalComponentReceived(Double principalComponentReceived) {
        this.principalComponentReceived = principalComponentReceived;
    }

    public Double getInterestComponent() {
        return interestComponent;
    }

    public void setInterestComponent(Double interestComponent) {
        this.interestComponent = interestComponent;
    }

    public Double getInterestComponentReceived() {
        return interestComponentReceived;
    }

    public void setInterestComponentReceived(Double interestComponentReceived) {
        this.interestComponentReceived = interestComponentReceived;
    }

    public Date getReceivablePayableDate() {
        return receivablePayableDate;
    }

    public void setReceivablePayableDate(Date receivablePayableDate) {
        this.receivablePayableDate = receivablePayableDate;
    }
    public ReceivablePayableStatus getReceivablePayableStatus() {
        return receivablePayableStatus;
    }

    public void setReceivablePayableStatus(ReceivablePayableStatus receivablePayableStatus) {
        this.receivablePayableStatus = receivablePayableStatus;
    }


    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }


    @Override
    public String toString() {
        return "ReceivablePayableDTO{" +
                "receivablePayableId=" + receivablePayableId +
                ", currency=" + currency +
                ", receivablePayableType=" + receivablePayableType +
                ", receivablePayableDueDate=" + receivablePayableDueDate +
                ", loanAccount=" + loanAccount +
                ", receivablePayableAmount=" + receivablePayableAmount +
                ", principalComponent=" + principalComponent +
                ", principalComponentReceived=" + principalComponentReceived +
                ", interestComponent=" + interestComponent +
                ", interestComponentReceived=" + interestComponentReceived +
                ", receivablePayableDate=" + receivablePayableDate +
                ", receivablePayableStatus=" + receivablePayableStatus +
                ", penalty=" + penalty +

                '}';
    }
}
