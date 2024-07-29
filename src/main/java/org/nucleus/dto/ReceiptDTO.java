package org.nucleus.dto;


import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.*;

import java.sql.Date;

public class ReceiptDTO {
    private Long receiptId;
    private String loanAccountNumber;
    private Double transactionAmount;
    private Date transactionDate;
    private Currency currency;
    private Date dateOfReceipt;
    private Double requiredAmount;
    private String instrumentNumber;
    private Date instrumentDate;
    private String receivedfrom;
    private ReceiptBasis receiptbasis;
    private String bankName;
    private ReceiptStatus status;
    private ReceivablePayableDTO receivablePayable;
    private PaymentMode paymentMode = PaymentMode.CASH;
    private TempMetaData tempMetaData;

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public Double getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(Double requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public String getInstrumentNumber() {
        return instrumentNumber;
    }

    public void setInstrumentNumber(String instrumentNumber) {
        this.instrumentNumber = instrumentNumber;
    }

    public Date getInstrumentDate() {
        return instrumentDate;
    }

    public void setInstrumentDate(Date instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public String getReceivedfrom() {
        return receivedfrom;
    }

    public void setReceivedfrom(String receivedfrom) {
        this.receivedfrom = receivedfrom;
    }

    public ReceiptBasis getReceiptbasis() {
        return receiptbasis;
    }

    public void setReceiptbasis(ReceiptBasis receiptbasis) {
        this.receiptbasis = receiptbasis;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }


    public ReceivablePayableDTO getReceivablePayable() {
        return receivablePayable;
    }

    public void setReceivablePayable(ReceivablePayableDTO receivablePayable) {
        this.receivablePayable = receivablePayable;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public TempMetaData getTempMetaData() {
        return tempMetaData;
    }

    public void setTempMetaData(TempMetaData tempMetaData) {
        this.tempMetaData = tempMetaData;
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
                "receiptId=" + receiptId +
                ", loanAccountNumber='" + loanAccountNumber + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionDate=" + transactionDate +
                ", currency=" + currency +
                ", dateOfReceipt=" + dateOfReceipt +
                ", requiredAmount=" + requiredAmount +
                ", instrumentNumber='" + instrumentNumber + '\'' +
                ", instrumentDate=" + instrumentDate +
                ", receivedfrom='" + receivedfrom + '\'' +
                ", receiptbasis=" + receiptbasis +
                ", bankName='" + bankName + '\'' +
                ", status=" + status +
                ", receivablePayable=" + receivablePayable +
                ", paymentMode=" + paymentMode +
                ", tempMetaData=" + tempMetaData +
                '}';
    }
}
