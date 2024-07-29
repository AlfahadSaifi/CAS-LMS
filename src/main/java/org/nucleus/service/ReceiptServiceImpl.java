package org.nucleus.service;

import org.nucleus.dao.ReceiptDAO;
import org.nucleus.dao.ReceiptDAOTemp;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.PaymentMode;
import org.nucleus.utility.enums.ReceiptStatus;
import org.nucleus.utility.enums.ReceivablePayableType;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    ReceiptDAO receiptDAO;
    @Autowired
    ReceiptDAOTemp receiptDAOTemp;
    @Autowired
    ReceivablePayableService receivablePayableService;
    @Override
    public boolean createReceiptDTO(ReceiptDTO receiptDTO) {
        return receiptDAO.createReceipt(receiptDTO);
    }

    @Override
    public ReceiptDTO getReceiptDTOById(Long id) {
        return receiptDAO.getReceiptById(id);
    }

    @Override
    public List<ReceiptDTO> getAllReceiptDTOs() {
        return receiptDAO.getAllReceipts();
    }

    @Override
    public boolean updateReceiptDTO(ReceiptDTO updatedReceiptDTO) {
        return receiptDAO.updateReceipt(updatedReceiptDTO);
    }
    @Override
    public boolean deleteReceiptDTO(ReceiptDTO receiptDTO) {
        return receiptDAO.deleteReceipt(receiptDTO);
    }

    @Override
    public List<ReceiptDTO> getallReceiptbyLoanACC(String id, Date fromdate, Date todate) {
        return receiptDAO.getallReceiptbyLoanACC(id, fromdate, todate);
    }

    @Override
    public boolean createReceiptTemp(ReceiptDTO payment) {
        return receiptDAOTemp.createReceiptTemp(payment);
    }

    @Override
    public ReceiptDTO getReceiptTempById(Long id) {
        return receiptDAOTemp.getReceiptTempById(id);
    }

    @Override
    public List<ReceiptDTO> getAllReceiptTemps() {
        return receiptDAOTemp.getAllReceiptTemps();
    }

    @Override
    public boolean updateReceiptTemp(ReceiptDTO updatedReceiptTemp) {
        return receiptDAOTemp.updateReceiptTemp(updatedReceiptTemp);
    }

    @Override
    public boolean deleteReceiptTemp( ReceiptDTO receiptDTO) {
        return receiptDAOTemp.deleteReceiptTemp(receiptDTO);
    }

    @Override
    public List<ReceiptDTO> getListReceiptTempById(Long id) {
        return receiptDAOTemp.getListReceiptTempById(id);
    }

    @Override
    public ReceiptDTO calculateReceivableDetails(Long receivablePayableId) {

        ReceivablePayableDTO receivablePayableDTO =
                receivablePayableService.getReceivablePayable(receivablePayableId);
        if (receivablePayableDTO == null) {
            return null;
        }
        ReceiptDTO receiptDTO = new ReceiptDTO();
        Double amount = 0.0;
        if (ReceivablePayableType.RECEIVABLE.equals(receivablePayableDTO.getReceivablePayableType())) {
            Double penalty = Period.between(
                    receivablePayableDTO.getReceivablePayableDueDate().toLocalDate(),
                    LocalDate.now()).getDays() * 100.0;
            penalty = penalty < 0.0 ? 0.0 : penalty;
            amount = receivablePayableDTO.getReceivablePayableAmount() + penalty;
        }
        receiptDTO.setRequiredAmount(amount);
        receiptDTO.setLoanAccountNumber(receivablePayableDTO.getLoanAccount().getLoanAccountNumber());
        return receiptDTO;
    }
    @Override
    public boolean submitReceipt(ReceiptDTO receiptDTO) {
        if (receiptDTO.getRequiredAmount() - receiptDTO.getTransactionAmount() >= 0) {
            if((PaymentMode.CASH).equals(receiptDTO.getPaymentMode())){
                receiptDTO.setInstrumentDate(null);
                receiptDTO.setInstrumentNumber("");
            }
            Long receivablePayableId = receiptDTO.getReceivablePayable().getReceivablePayableId();
            TempMetaData tempMetaData = new TempMetaData();  //setMeta data
            tempMetaData.setCreationDate(Date.valueOf(LocalDate.now()));
            tempMetaData.setRecordStatus(RecordStatus.N);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            tempMetaData.setCreatedBy(username);
            receiptDTO.setTempMetaData(tempMetaData);
            receiptDTO.setStatus(ReceiptStatus.UNREALISED);
            receiptDTO.setReceivablePayable(receivablePayableService.getReceivablePayable(receivablePayableId));
            return createReceiptTemp(receiptDTO);  //Insert Into Receipt data into Temp Table
        }
        return false;
    }
    @Override
    public List<ReceiptDTO> getReceiptDTOListByReceivableId(Long receivablePayableId,Date fromDate,Date toDate) {
        List<ReceiptDTO> receipctDTOList = getListReceiptTempById(receivablePayableId);  //getList
        if (!receipctDTOList.isEmpty()) {
            return receipctDTOList
                            .stream()
                            .filter(receipt -> receipt.getTransactionDate().compareTo(fromDate) >= 0
                                    && receipt.getTransactionDate().compareTo(toDate) <= 0)
                            .collect(Collectors.toList());
    }
        return Collections.emptyList();
    }
    @Override
    public ReceiptDTO approveReceiptByReceiptId(ReceiptDTO receiptDTO) {
        if (receiptDTO != null) {
            TempMetaData tempMetaData = receiptDTO.getTempMetaData();
            tempMetaData.setAuthorizedDate(Date.valueOf(LocalDate.now()));
            tempMetaData.setRecordStatus(RecordStatus.A);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            tempMetaData.setAuthorizedBy(username);
            receiptDTO.setTempMetaData(tempMetaData);
            return receiptDTO;
        }
        return null;
    }
}
