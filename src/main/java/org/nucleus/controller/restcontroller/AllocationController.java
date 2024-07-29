package org.nucleus.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.nucleus.dto.AllocationDTO;
import org.nucleus.service.AllocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Objects;

@PreAuthorize("hasRole('CHECKER')")
@RestController
@RequestMapping("checker")
public class AllocationController {

    @Autowired
    AllocationService allocationService;

    @Autowired
    private Environment env;

    private static final Logger logger = LogManager.getLogger(AllocationController.class);

    @RequestMapping(value = "/allocation-details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllocationDetails() throws JsonProcessingException {


        if (allocationService.batchAllocationForReceipt()) {
            logger.info("batch processing of the receipt was successful");

        } else {
            logger.error("batch processing can't be completed");

        }

        List<AllocationDTO> allocationDTOS = allocationService.getAllocationList(
                Integer.parseInt(Objects.requireNonNull(env.getProperty("batchSize"))), 0);



        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(allocationDTOS);


        return ResponseEntity.ok().body(jsonData);
    }

}
