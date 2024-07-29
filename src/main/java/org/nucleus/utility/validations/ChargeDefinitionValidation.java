
package org.nucleus.utility.validations;


import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.ChargeDefinitionTempDTO;

import java.util.regex.Pattern;

public class ChargeDefinitionValidation {
    public static boolean chargeDefinitionAmountComparison(ChargeDefinitionTempDTO chargeDefinitionTempDTO){
        if(chargeDefinitionTempDTO.getMaximumAmount()>= chargeDefinitionTempDTO.getMinimumAmount()){
            return true;
        }
        return false;
    }
    public static boolean chargeDefinitionChargeName(ChargeDefinitionDTO chargeDefinitionDTO){
        String chargeName = chargeDefinitionDTO.getChargeName();
        String regex = "^[a-zA-Z\\s]+$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Check if the string matches the pattern
        return pattern.matcher(chargeName).matches();

    }

    public static boolean chargeDefinitionTempChargeName(ChargeDefinitionTempDTO chargeDefinitionTempDTO) {
        String chargeName = chargeDefinitionTempDTO.getChargeName();
        String regex = "^[a-zA-Z\\s]+$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Check if the string matches the pattern
        return pattern.matcher(chargeName).matches();

    }
}

