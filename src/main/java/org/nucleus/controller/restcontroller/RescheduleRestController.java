package org.nucleus.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.dto.RescheduleResponseDTO;
import org.nucleus.service.RescheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("reschedule")
@PreAuthorize("hasRole('MAKER') or hasRole('CHECKER')")
public class RescheduleRestController {
    private RescheduleService rescheduleService;
    private String loanAccountNumber;
    public RescheduleRestController(RescheduleService rescheduleService){
        this.rescheduleService = rescheduleService;
    }
    @GetMapping("/getCustomerNameByLoanAccountNumber")
    public ResponseEntity<String> getCustomerNameByLoanAccountNumber(@RequestParam String loanAccountNumber) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
        this.loanAccountNumber = loanAccountNumber;
        String customerName = rescheduleService.getCustomerNameByLoanAccountNumber(loanAccountNumber);
        if(customerName == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(rescheduleService.getLastStatus()));
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(customerName));
    }
    @GetMapping(value = "/getRescheduleResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRescheduleResponse(@RequestParam String loanAccountNumber) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
        RescheduleResponseDTO rescheduleResponseDTO = rescheduleService.getRescheduleResponseDTOByLoanAccountNumber(loanAccountNumber);
        System.out.println("asasasas"+rescheduleResponseDTO);
        if(rescheduleResponseDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(rescheduleService.getLastStatus()));
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(rescheduleResponseDTO));
    }
    @GetMapping("/checkRepaySchedule")
    public ResponseEntity<String> checkRepaySchedule(@RequestParam String key, @RequestParam String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
        if("dueDate".equals(key) && Integer.parseInt(value)<29 && Integer.parseInt(value)>0){
            List<RepayScheduleDTO> repayScheduleDTOS = rescheduleService.rescheduleByDueDate(loanAccountNumber, Integer.parseInt(value));
            if(repayScheduleDTOS.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rescheduleService.getLastStatus());
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(repayScheduleDTOS));
        }
        else if("tenure".equals(key)){
            String[] parts = value.split(",");
            List<RepayScheduleDTO> repayScheduleDTOS = rescheduleService.rescheduleByTenure(loanAccountNumber, Integer.parseInt(parts[0]), parts[1]);
            if(repayScheduleDTOS.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rescheduleService.getLastStatus());
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(repayScheduleDTOS));
        }
        else if("installment".equals(key)){
            List<RepayScheduleDTO> repayScheduleDTOS = rescheduleService.rescheduleByInstallmentAmount(loanAccountNumber, Double.parseDouble(value));
            if(repayScheduleDTOS.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rescheduleService.getLastStatus());
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(repayScheduleDTOS));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Data Entered");
    }
    @PutMapping
    public ResponseEntity<String> updateRepaySchedule() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if(rescheduleService.saveUpdatedRepaySchedule())
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(rescheduleService.getLastStatus()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(rescheduleService.getLastStatus()));
    }
}
