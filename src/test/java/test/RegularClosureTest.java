package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nucleus.dao.LoanAccountDAO;
import org.nucleus.dao.LoanClosureDAO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;

import org.nucleus.service.LoanClosureServiceTempImpl;
import org.nucleus.utility.enums.LoanStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegularClosureTest {
    @Mock
    private LoanClosureDAO loanClosureDAO;

    @Mock
    private LoanAccountDAO loanAccountDAO;

    @InjectMocks
    private LoanClosureServiceTempImpl loanClosureServiceTemp;

    @Test
    void testSaveLoanClosureData() {
        LoanClosureDTO loanClosureDTO = new LoanClosureDTO(); // Create a sample DTO
        Long expectedId = 100123L;
        when(loanClosureDAO.save(any(LoanClosureDTO.class))).thenReturn(true);

        // Execute
        Boolean result = loanClosureServiceTemp.save(loanClosureDTO);

        // Verify
        assertNotNull(result);
        assertEquals(true, result);

    }

    @Test
    void testRegularClosureData() {
        List<LoanClosureDTO> result = loanClosureServiceTemp.getAllRegularClosureData();
        assertTrue(result.isEmpty());
    }

    @Test
    void updateLoanStatus(){
        List<LoanAccountDTO> loanAccountDTOS = new ArrayList<>();
        LoanAccountDTO loanAccountDTO=new LoanAccountDTO();
        loanAccountDTO.setLoanAccountId(100023L);
        loanAccountDTO.setLoanStatus(LoanStatus.CLOSED);
        loanAccountDTOS.add(loanAccountDTO);
        assertFalse(loanAccountDAO.updateAccountStatus(loanAccountDTOS));
    }


}
