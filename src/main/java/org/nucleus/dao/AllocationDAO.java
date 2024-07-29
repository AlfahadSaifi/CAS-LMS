package org.nucleus.dao;

import org.nucleus.dto.AllocationDTO;

import java.util.List;
import java.util.Set;

public interface AllocationDAO {
    boolean insertAllocationDetail(AllocationDTO allocationDTO);
    boolean updateAllocation(AllocationDTO allocationDTO);
    boolean batchInsertAllocation(Set<AllocationDTO> allocationDTOList);
    List<AllocationDTO>getAllocationList(int batchNumber, int batchSize);
    List<AllocationDTO> getAllocationByLoanAccountId(Long loanAccountId);
}
