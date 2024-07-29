package org.nucleus.utility.dtomapper;

import org.nucleus.entity.permanent.Allocation;
import org.nucleus.dto.AllocationDTO;

import java.util.stream.Collectors;

public class AllocationToDTOMapper {

    public static AllocationDTO mapObjectToDTO(Allocation allocation){

        AllocationDTO allocationDTO = new AllocationDTO();
        LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();

        allocationDTO.setAllocationId(allocation.getAllocationId());
        if(allocation.getLoanAccount()!=null)allocationDTO.setLoanAccount(loanAccountDTOMapper.mapObjectToDTO(allocation.getLoanAccount()));
        allocationDTO.setDepositDate(allocation.getDepositDate());
        allocationDTO.setPenaltyDTOS(allocation.getPenaltyList().stream().map(PenaltyDTOMapper::penaltyEntityToPenaltyDTO).collect(Collectors.toList()));
        allocationDTO.setInterestComponentReceived(allocation.getInterestComponentReceived());
        allocationDTO.setPrincipalComponentReceived(allocation.getPrincipalComponentReceived());

        return allocationDTO;

    }

}
