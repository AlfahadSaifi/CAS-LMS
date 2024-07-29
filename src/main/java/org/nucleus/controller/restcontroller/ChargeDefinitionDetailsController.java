package org.nucleus.controller.restcontroller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.service.ChargeDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/maker")
public class ChargeDefinitionDetailsController {
    @Autowired
    private ChargeDefinitionService chargeDefinitionService;
    @GetMapping(value = "/charge-details/{chargeCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMinAndMaxAmount(@PathVariable(name = "chargeCode", required = false) String chargeCode) throws JsonProcessingException {
        ChargeDefinitionDTO chargeDefinitionDTO = chargeDefinitionService.getChargeDefinitionFromTheMasterTableByCode(chargeCode);
        if(chargeDefinitionDTO==null)
        {
            return ResponseEntity.notFound().build();
        }
        Map<String,ChargeDefinitionDTO> result = new HashMap<>();
//        result.put("minAmount",chargeDefinitionDTO.getMinimumAmount());
//        result.put("maxAmount",chargeDefinitionDTO.getMaximumAmount());
//        result.put("chargeName",chargeDefinitionDTO.getChargeName());

        result.put("chargeDefinition",chargeDefinitionDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(result);

        return ResponseEntity.ok().body(jsonData);
    }
}