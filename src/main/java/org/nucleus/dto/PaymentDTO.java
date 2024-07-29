package org.nucleus.dto;

import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.ReceivablePayable;
import org.nucleus.utility.enums.Currency;
import org.nucleus.utility.enums.PaymentMode;
import org.nucleus.utility.enums.ReceiptStatus;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Past;
import java.sql.Date;

public class PaymentDTO {
        private Long receiptId;
        private String paidTo;
        private String payeeName;
        private ReceivablePayableDTO receivablePayable;
    @DecimalMin(value ="1",message = "Payment amount can not be less than 1")
    private double paymentAmount;
    private Currency paymentCurrency;
    private String loanAccountNumber;
    @Past(message = "Payment Date can not be greater than current Date.")
    private Date paymentDate;
    private String payoutBankAccount;
    private ReceiptStatus status;
    private PaymentMode paymentMode;
    private TempMetaData tempMetaData;


    public PaymentDTO(){
            tempMetaData = new TempMetaData();
        }


    public TempMetaData getTempMetaData() {
        return tempMetaData;
    }

    public void setTempMetaData(TempMetaData tempMetaData) {
        this.tempMetaData = tempMetaData;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public ReceivablePayableDTO getReceivablePayable() {
        return receivablePayable;
    }

    public void setReceivablePayable(ReceivablePayableDTO receivablePayable) {
        this.receivablePayable = receivablePayable;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Currency getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(Currency paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPayoutBankAccount() {
        return payoutBankAccount;
    }

    public void setPayoutBankAccount(String payoutBankAccount) {
        this.payoutBankAccount = payoutBankAccount;
    }


    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "receiptId=" + receiptId +
                ", paidTo='" + paidTo + '\'' +
                ", payeeName='" + payeeName + '\'' +
                ", receivablePayable=" + receivablePayable +
                ", paymentAmount=" + paymentAmount +
                ", paymentCurrency=" + paymentCurrency +
                ", loanAccountNumber='" + loanAccountNumber + '\'' +
                ", paymentDate=" + paymentDate +
                ", payoutBankAccount='" + payoutBankAccount + '\'' +
                ", status=" + status +
                ", paymentMode=" + paymentMode +
                ", tempMetaData=" + tempMetaData +
                '}';
    }
}
