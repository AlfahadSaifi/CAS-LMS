package org.nucleus.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/maker")
public class RestLoanProductController {

    @Autowired
    private LoanProductService productService;

    @GetMapping(value = "/fetch-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllLoanProduct(@RequestParam(name = "productTypeId", required = false) Long productTypeId) throws JsonProcessingException {
        List<LoanProductDTO> productDTOList=productService.getAllLoanProductsByProductTypeId(productTypeId);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(productDTOList);

        return ResponseEntity.status(HttpStatus.OK).body(jsonData);
    }
}
