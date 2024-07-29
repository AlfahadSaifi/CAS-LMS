package org.nucleus.utility.dtomapper;


import org.nucleus.entity.permanent.Receipt;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.entity.permanent.ReceivablePayable;
import org.nucleus.entity.temporary.ReceiptTemp;

public class ReceiptDTOMapping {

    public ReceiptDTO mapEntitytoDTO(Receipt receipt){
        ReceiptDTO receiptDTO = new ReceiptDTO();
        MetaDataMapper metaDataMapper = new MetaDataMapper();
        ReceivablePayableDtoMapper receivablePayableDtoMapper = new ReceivablePayableDtoMapper();
        receiptDTO.setReceiptId(receipt.getReceiptId());
        receiptDTO.setLoanAccountNumber(receipt.getLoanAccountNumber());
        receiptDTO.setTransactionAmount(receipt.getTransactionAmount());
        receiptDTO.setTransactionDate(receipt.getTransactionDate());
        receiptDTO.setCurrency(receipt.getCurrency());
        receiptDTO.setDateOfReceipt(receipt.getDateOfReceipt());
        receiptDTO.setRequiredAmount(receipt.getRequiredAmount());
        receiptDTO.setInstrumentNumber(receipt.getInstrumentNumber());
        receiptDTO.setInstrumentDate(receipt.getInstrumentDate());
        receiptDTO.setReceivedfrom(receipt.getReceivedfrom());
        receiptDTO.setReceiptbasis(receipt.getReceiptbasis());
        receiptDTO.setBankName(receipt.getBankName());
        receiptDTO.setStatus(receipt.getStatus());

        receiptDTO.setReceivablePayable(receivablePayableDtoMapper.mapObjectToreceivablePayableDTO(receipt.getReceivablePayable()));

        return receiptDTO;
    }

    public Receipt mapDTOtoEntity(ReceiptDTO receiptDTO){
        Receipt receipt = new Receipt();
        MetaDataMapper metaDataMapper = new MetaDataMapper();
        ReceivablePayableDtoMapper receivablePayableDtoMapper = new ReceivablePayableDtoMapper();
        receipt.setReceiptId(receiptDTO.getReceiptId());
        receipt.setLoanAccountNumber(receiptDTO.getLoanAccountNumber());
        receipt.setTransactionAmount(receiptDTO.getTransactionAmount());
        receipt.setTransactionDate(receiptDTO.getTransactionDate());
        receipt.setCurrency(receiptDTO.getCurrency());
        receipt.setDateOfReceipt(receiptDTO.getDateOfReceipt());
        receipt.setRequiredAmount(receiptDTO.getRequiredAmount());
        receipt.setInstrumentNumber(receiptDTO.getInstrumentNumber());
        receipt.setInstrumentDate(receiptDTO.getInstrumentDate());
        receipt.setReceivedfrom(receiptDTO.getReceivedfrom());
        receipt.setReceiptbasis(receiptDTO.getReceiptbasis());
        receipt.setBankName(receiptDTO.getBankName());
        receipt.setStatus(receiptDTO.getStatus());
        receipt.setReceivablePayable(receivablePayableDtoMapper.mapObjectToReceivablePayable(receiptDTO.getReceivablePayable()));
        receipt.setMetaData(metaDataMapper.tempToMetaData(receiptDTO.getTempMetaData()));
        return receipt;
    }

    Receipt receipt;
    public ReceiptDTO mapEntityTemptoDTO(ReceiptTemp receiptTemp){
        ReceiptDTO receiptDTO = new ReceiptDTO();
        ReceivablePayableToReceivablePayableDTO receivablePayableToReceivablePayableDTO = new ReceivablePayableToReceivablePayableDTO();
        receiptDTO.setReceiptId(receiptTemp.getReceiptId());
        receiptDTO.setLoanAccountNumber(receiptTemp.getLoanAccountNumber());
        receiptDTO.setTransactionAmount(receiptTemp.getTransactionAmount());
        receiptDTO.setTransactionDate(receiptTemp.getTransactionDate());
        receiptDTO.setCurrency(receiptTemp.getCurrency());
        receiptDTO.setDateOfReceipt(receiptTemp.getDateOfReceipt());
        receiptDTO.setRequiredAmount(receiptTemp.getRequiredAmount());
        receiptDTO.setInstrumentNumber(receiptTemp.getInstrumentNumber());
        receiptDTO.setInstrumentDate(receiptTemp.getInstrumentDate());
        receiptDTO.setReceivedfrom(receiptTemp.getReceivedfrom());
        receiptDTO.setReceiptbasis(receiptTemp.getReceiptbasis());
        receiptDTO.setBankName(receiptTemp.getBankName());
        receiptDTO.setStatus(receiptTemp.getStatus());
        receiptDTO.setReceivablePayable(receivablePayableToReceivablePayableDTO.mapObjectToreceivablePayableDTO(receiptTemp.getReceivablePayable()));
        receiptDTO.setPaymentMode(receiptTemp.getPaymentMode());
        receiptDTO.setTempMetaData(receiptTemp.getMetaData());
        return receiptDTO;
    }


    public ReceiptTemp mapDTOtoEntityTemp(ReceiptDTO receiptDTO){
        ReceiptTemp receiptTemp = new ReceiptTemp();
        ReceivablePayableDtoMapper receivablePayableDtoMapper = new ReceivablePayableDtoMapper();
        receiptTemp.setReceiptId(receiptDTO.getReceiptId());
        receiptTemp.setLoanAccountNumber(receiptDTO.getLoanAccountNumber());
        receiptTemp.setTransactionAmount(receiptDTO.getTransactionAmount());
        receiptTemp.setTransactionDate(receiptDTO.getTransactionDate());
        receiptTemp.setCurrency(receiptDTO.getCurrency());
        receiptTemp.setDateOfReceipt(receiptDTO.getDateOfReceipt());
        receiptTemp.setRequiredAmount(receiptDTO.getRequiredAmount());
        receiptTemp.setInstrumentNumber(receiptDTO.getInstrumentNumber());
        receiptTemp.setInstrumentDate(receiptDTO.getInstrumentDate());
        receiptTemp.setReceivedfrom(receiptDTO.getReceivedfrom());
        receiptTemp.setReceiptbasis(receiptDTO.getReceiptbasis());
        receiptTemp.setBankName(receiptDTO.getBankName());
        receiptTemp.setStatus(receiptDTO.getStatus());
        receiptTemp.setReceivablePayable(receivablePayableDtoMapper.mapObjectToReceivablePayable(receiptDTO.getReceivablePayable()));
        receiptTemp.setPaymentMode(receiptDTO.getPaymentMode());
        receiptTemp.setMetaData(receiptDTO.getTempMetaData());
        return receiptTemp;
    }

}
