package org.nucleus.utility.dtomapper;


import org.nucleus.dto.PaymentDTO;
import org.nucleus.entity.permanent.Payment;
import org.nucleus.entity.temporary.PaymentTemp;
import org.springframework.stereotype.Component;

@Component
public class PaymentDTOMapping {

    public PaymentTemp mapDTOtoEntityTemp(PaymentDTO paymentDTO){
        ReceivablePayableDtoMapper receivablePayableDtoMapper = new ReceivablePayableDtoMapper();
        PaymentTemp paymentTemp = new PaymentTemp();
        paymentTemp.setReceiptId(paymentDTO.getReceiptId());
        paymentTemp.setPaidTo(paymentDTO.getPaidTo());
        paymentTemp.setPayeeName(paymentDTO.getPayeeName());
        paymentTemp.setReceivablePayable(receivablePayableDtoMapper.mapObjectToReceivablePayableTemp(paymentDTO.getReceivablePayable()));
        paymentTemp.setPaymentAmount(paymentDTO.getPaymentAmount());
        paymentTemp.setPaymentCurrency(paymentDTO.getPaymentCurrency());
        paymentTemp.setLoanAccountNumber(paymentDTO.getLoanAccountNumber());
        paymentTemp.setPaymentDate(paymentDTO.getPaymentDate());
        paymentTemp.setPayoutBankAccount(paymentDTO.getPayoutBankAccount());
        paymentTemp.setPaymentMode(paymentDTO.getPaymentMode());
        paymentTemp.setMetaData(paymentDTO.getTempMetaData());
        paymentTemp.setStatus(paymentDTO.getStatus());
        return paymentTemp;
    }
    public Payment mapDTOtoEntity(PaymentDTO paymentDTO){
        Payment payment = new Payment();
        ReceivablePayableDtoMapper receivablePayableDtoMapper = new ReceivablePayableDtoMapper();
        MetaDataMapper metaDataMapper = new MetaDataMapper();
        payment.setReceiptId(paymentDTO.getReceiptId());
        payment.setPaidTo(paymentDTO.getPaidTo());
        payment.setPayeeName(paymentDTO.getPayeeName());
        payment.setReceivablePayable(receivablePayableDtoMapper.mapObjectToReceivablePayable(paymentDTO.getReceivablePayable()));
        payment.setPaymentAmount(paymentDTO.getPaymentAmount());
        payment.setPaymentCurrency(paymentDTO.getPaymentCurrency());
        payment.setLoanAccountNumber(paymentDTO.getLoanAccountNumber());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setPayoutBankAccount(paymentDTO.getPayoutBankAccount());
        payment.setPaymentMode(paymentDTO.getPaymentMode());
        payment.setMetaData(metaDataMapper.tempToMetaData(paymentDTO.getTempMetaData()));
        payment.setStatus(paymentDTO.getStatus());
        return payment;
    }
    public PaymentDTO mapEntitytoDTO(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();
        ReceivablePayableDtoMapper receivablePayableDtoMapper = new ReceivablePayableDtoMapper();
        MetaDataMapper metaDataMapper = new MetaDataMapper();
        paymentDTO.setReceiptId(payment.getReceiptId());
        paymentDTO.setPaidTo(payment.getPaidTo());
        paymentDTO.setPayeeName(payment.getPayeeName());
        paymentDTO.setReceivablePayable(receivablePayableDtoMapper.mapObjectToreceivablePayableDTO(payment.getReceivablePayable()));
        paymentDTO.setPaymentAmount(payment.getPaymentAmount());
        paymentDTO.setPaymentCurrency(payment.getPaymentCurrency());
        paymentDTO.setLoanAccountNumber(payment.getLoanAccountNumber());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setPayoutBankAccount(payment.getPayoutBankAccount());
        paymentDTO.setPaymentMode(payment.getPaymentMode());
        paymentDTO.setTempMetaData(metaDataMapper.metaDataToTemp(payment.getMetaData()));
        paymentDTO.setStatus(payment.getStatus());
        return paymentDTO;
    }
    public PaymentDTO mapEntityTemptoDTO(PaymentTemp paymentTemp){
        PaymentDTO paymentDTO = new PaymentDTO();
        ReceivablePayableDtoMapper receivablePayableDtoMapper = new ReceivablePayableDtoMapper();
        MetaDataMapper metaDataMapper = new MetaDataMapper();
        paymentDTO.setReceiptId(paymentTemp.getReceiptId());
        paymentDTO.setPaidTo(paymentTemp.getPaidTo());
        paymentDTO.setPayeeName(paymentTemp.getPayeeName());
        paymentDTO.setReceivablePayable(receivablePayableDtoMapper.mapTempObjectToreceivablePayableDTO(paymentTemp.getReceivablePayable()));
        paymentDTO.setPaymentAmount(paymentTemp.getPaymentAmount());
        paymentDTO.setPaymentCurrency(paymentTemp.getPaymentCurrency());
        paymentDTO.setLoanAccountNumber(paymentTemp.getLoanAccountNumber());
        paymentDTO.setPaymentDate(paymentTemp.getPaymentDate());
        paymentDTO.setPayoutBankAccount(paymentTemp.getPayoutBankAccount());
        paymentDTO.setPaymentMode(paymentTemp.getPaymentMode());
        paymentDTO.setTempMetaData(paymentTemp.getMetaData());
        paymentDTO.setStatus(paymentTemp.getStatus());
        return paymentDTO;
    }
}
