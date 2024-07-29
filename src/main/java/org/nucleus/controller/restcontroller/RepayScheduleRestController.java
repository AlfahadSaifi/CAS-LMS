package org.nucleus.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.service.RepayScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RepayScheduleRestController {

    @Autowired
    RepayScheduleService repayScheduleService;

    @PostMapping(value = "{xxxx}/getRepay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepay(@RequestParam(required = false , name = "loanApplicationId") String loanApplicationId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(repayScheduleService.fetchRepaySchedule(loanApplicationId));
        return ResponseEntity.ok(jsonData);
    }

}