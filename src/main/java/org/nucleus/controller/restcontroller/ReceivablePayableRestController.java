package org.nucleus.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.service.LoanAccountService;
import org.nucleus.service.LoanApplicationService;
import org.nucleus.service.ReceivablePayableService;
import org.nucleus.service.ReceivablePayableTempService;
import org.nucleus.utility.enums.LoanStatus;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.Date;
import java.util.List;

@RestController
public class ReceivablePayableRestController {
    @Autowired
    private LoanAccountService loanAccountService;
    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private ReceivablePayableTempService receivablePayableTempService;

    @Autowired
    private ReceivablePayableService receivablePayableService;

    @GetMapping("/searchDetails")
    public ResponseEntity<LoanApplicationDTO> searchDetails(@RequestParam String loanAccountNumber) {
        System.out.println("here");
// Call the service to fetch details based on the loan account number
        LoanApplicationDTO loanApplication = loanApplicationService.readPermanentByLoanAccountNumber(loanAccountNumber);

        return ResponseEntity.ok(loanApplication);
    }

    @GetMapping("/searchDetailsForMaker")
    public ResponseEntity<LoanAccountDTO> searchDetailsForMaker(@RequestParam String loanAccountNumber) {
        System.out.println("here " + loanAccountNumber);
// Call the service to fetch details based on the loan account number
        LoanAccountDTO accountDTO = loanAccountService.getByAccountNumber(loanAccountNumber);
        System.out.println(accountDTO.getLoanApplication().getBranch());
        if(accountDTO!=null) {

            return ResponseEntity.ok(accountDTO);
        }
        return  ResponseEntity.notFound().build();
    }


    @RequestMapping(value="/receivable-generate-table",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReceivablePayableTable(
            @RequestParam("loanAccountNumber") String loanAccountNumber,
            @RequestParam("fromDate") String startDate,
            @RequestParam("toDate") String endDate

    ) throws JsonProcessingException {
        System.out.println(startDate);
        LoanAccountDTO loanAccountDTO = loanAccountService.getByAccountNumber(loanAccountNumber);
        System.out.println(loanAccountDTO);
        List<ReceivablePayableDTO> receivableDtoList = receivablePayableTempService.getReceivablePayableAgainstLoanIdAndDateRange(loanAccountDTO, Date.valueOf(startDate),Date.valueOf(endDate));
        System.out.println(receivableDtoList);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(receivableDtoList);

        return ResponseEntity.ok(jsonData);

    }


    @RequestMapping("/approve-receivable-payable/id/{receivablePayableId}")
    ResponseEntity<String> approved(
            @PathVariable("receivablePayableId") Long receivableId
    ){
        //fetch the receivablePayable from temporary table using the receivablePayableId present in the path
        ReceivablePayableDTO receivablePayable = receivablePayableTempService.getReceivablePayable(receivableId);

        //delete receivablePayable from temporary table
        receivablePayableTempService.deleteReceivablePayable(receivablePayable);

        //change its rejection status to approved
        receivablePayable.getTempMetaData().setRecordStatus(RecordStatus.A);

        //insert receivablePayable into the permanent table
        if(receivablePayableService.insertReceivablePayable(receivablePayable))
            return ResponseEntity.status(200).body("operation done");
        return ResponseEntity.status(500).body("insertion failed");
    }

    @RequestMapping("/reject-receivable-payable/id/{receivablePayableId}")
    ResponseEntity<String> rejected(
            @PathVariable("receivablePayableId") Long receivableId
    ){
        //fetch the receivablePayable from temporary table using the receivablePayableId present in the path
        ReceivablePayableDTO receivablePayable = receivablePayableTempService.getReceivablePayable(receivableId);

        //update the temporary table.
        if(receivablePayableTempService.updateReceivablePayable(receivablePayable))
            return ResponseEntity.status(200).body("rejection successfully done");
        return ResponseEntity.status(200).body("rejection failed");
    }
}
