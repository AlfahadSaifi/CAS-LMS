package org.nucleus.controller.restcontroller;

import org.nucleus.dto.ReceiptDTO;
import org.nucleus.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('MAKER')")
@RestController
@RequestMapping("/maker")
public class ReceiptMakerRestController {
    @Autowired
    ReceiptService receiptService;
    @GetMapping(value = "receipt-Search/{receivablePayableId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReceiptDTO> getReceivableDetails(
            @PathVariable Long receivablePayableId,
            Model model) {
        if (receivablePayableId == null) {
            return ResponseEntity.badRequest().build();
        }
        ReceiptDTO receiptDTO =
                receiptService.calculateReceivableDetails(receivablePayableId);
        if (receiptDTO != null) {
            return ResponseEntity.ok(receiptDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
