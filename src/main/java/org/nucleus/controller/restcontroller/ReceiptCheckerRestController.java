package org.nucleus.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.ReceiptService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/checker")
@PreAuthorize("hasRole('CHECKER')")
public class ReceiptCheckerRestController {
    @Autowired
    ReceiptService receiptService;

    @RequestMapping(value = "receipt-Generate-table", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateReceiptTable(@RequestParam("receivablePayableId") String receivablePayableId,
                                                                 @RequestParam("toDate") String toDate,
                                                                 @RequestParam("fromDate") String fromDate
    ) throws JsonProcessingException {  //show List of Receipt on given receivablePayable-id
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
        if (receivablePayableId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<ReceiptDTO> receiptDTOList = receiptService.getReceiptDTOListByReceivableId(Long.parseLong(receivablePayableId), Date.valueOf(fromDate), Date.valueOf(toDate));
        if (receiptDTOList != null) {
            System.out.println("hiiiiii");
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(receiptDTOList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("null");
        }
    }

    @GetMapping(value = "/approve-Receipt/{receiptId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> approveReceipt(@PathVariable Long receiptId) {
        if (receiptId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ReceiptDTO receiptDTO = receiptService.getReceiptTempById(receiptId);
        if (receiptDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ReceiptDTO receipt = receiptService.approveReceiptByReceiptId(receiptDTO);
        if (receipt == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        boolean isCreated = receiptService.createReceiptDTO(receiptDTO);
        if (!isCreated) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        boolean isDeleted = receiptService.deleteReceiptTemp(receiptDTO);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/reject-Receipt/{receiptId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> rejectReceipt(@PathVariable Long receiptId) {
        if (receiptId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ReceiptDTO receiptDTO = receiptService.getReceiptTempById(receiptId); //Fetch receiptDTO from temp table given receipt-id
        if (receiptDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        TempMetaData tempMetaData = receiptDTO.getTempMetaData();  //Set Temp Data
        if ((RecordStatus.N).equals(tempMetaData.getRecordStatus())) {
            tempMetaData.setRecordStatus(RecordStatus.NR);
        }else {
            tempMetaData.setRecordStatus(RecordStatus.MR);
        }
        receiptDTO.setTempMetaData(tempMetaData);
        boolean isUpdated = receiptService.updateReceiptTemp(receiptDTO);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
