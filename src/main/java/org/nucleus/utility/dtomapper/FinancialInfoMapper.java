package org.nucleus.utility.dtomapper;

import org.nucleus.entity.temporary.FinancialInfoTemp;
import org.nucleus.dto.FinancialInfoDTO;

public class FinancialInfoMapper {
    public static FinancialInfoDTO toDTO(FinancialInfoTemp financialInfoTemp){
        if(financialInfoTemp ==null)return null;

        FinancialInfoDTO financialInfoDTO=new FinancialInfoDTO();
        financialInfoDTO.setFinancialId(financialInfoTemp.getFinancialId());
        financialInfoDTO.setMonthlyIncome(financialInfoTemp.getMonthlyIncome());
        financialInfoDTO.setRentIncome(financialInfoTemp.getRentIncome());
        financialInfoDTO.setMedicalExpense(financialInfoTemp.getMedicalExpense());

        return financialInfoDTO;
    }

    public static FinancialInfoTemp toEntity(FinancialInfoDTO financialInfoDTO){
        if(financialInfoDTO==null)return null;

        FinancialInfoTemp financialInfoTemp =new FinancialInfoTemp();
        financialInfoTemp.setFinancialId(financialInfoDTO.getFinancialId());
        financialInfoTemp.setMonthlyIncome(financialInfoDTO.getMonthlyIncome());
        financialInfoTemp.setRentIncome(financialInfoDTO.getRentIncome());
        financialInfoTemp.setMedicalExpense(financialInfoDTO.getMedicalExpense());

        return financialInfoTemp;
    }
}
