package org.nucleus.controller.restcontroller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.entity.permanent.RepaySchedule;
import org.nucleus.service.RepayScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checker")
@PreAuthorize("hasRole('CHECKER')")
public class RepayScheduleCheck {
    @Autowired
    RepayScheduleService repayScheduleService;

    private static List<RepaySchedule> repaySchedules = new ArrayList<>();

    @RequestMapping(value = "/generate-repay-schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepayScheduleWithLoanId(@RequestParam("loanId")Long loanId) throws JsonProcessingException {
        List<RepaySchedule> repayScheduleDTOS = repayScheduleService.generateRepayScheduleForApproval(loanId);
        repaySchedules = repayScheduleDTOS;

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(repayScheduleDTOS);

        return ResponseEntity.ok().body(jsonData);
    }
    public static List<RepaySchedule> getRepaySchedules(){
        return repaySchedules;
    }
}

