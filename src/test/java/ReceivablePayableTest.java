import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.nucleus.dao.*;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.service.LoanApplicationService;
import org.nucleus.service.LoanApplicationServiceImpl;
import org.nucleus.service.ReceivablePayableService;
import org.nucleus.service.ReceivablePayableServiceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ReceivablePayableTest {
    @Mock
    private ReceivablePayableDao receivablePayableDao;


    @InjectMocks
    private ReceivablePayableServiceImpl receivablePayableService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testInsertReceivablePayable() {
        ReceivablePayableDTO receivablePayableDTO = new ReceivablePayableDTO();
        //receivablePayableDTO.setReceivablePayableId(Long.valueOf(1001));
        receivablePayableDTO.setReceivablePayableAmount(1000.00);
        receivablePayableDTO.setPrincipalComponent(5000.00);
        receivablePayableDTO.setInterestComponent(5000.00);
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        loanAccountDTO.setLoanAccountId(Long.valueOf(10001));
        loanAccountDTO.setLoanAccountNumber("ACC000000262");
        receivablePayableDTO.setLoanAccount(loanAccountDTO);

        when(receivablePayableDao.insertReceivablePayable(receivablePayableDTO)).thenReturn(false);
        boolean result = receivablePayableService.insertReceivablePayable(receivablePayableDTO);

        // Verify interactions and assertions
        assertFalse(result); // Assert that the result is true


    }
    @Test
    void testUpdateReceivablePayable() {
        ReceivablePayableDTO receivablePayableDTO = new ReceivablePayableDTO();
        receivablePayableDTO.setReceivablePayableId(Long.valueOf(1001));
        receivablePayableDTO.setReceivablePayableAmount(1000.00);
        receivablePayableDTO.setPrincipalComponent(5000.00);
        receivablePayableDTO.setInterestComponent(5000.00);
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        loanAccountDTO.setLoanAccountId(Long.valueOf(10001));
        loanAccountDTO.setLoanAccountNumber("ACC000000262");
        receivablePayableDTO.setLoanAccount(loanAccountDTO);

        when(receivablePayableDao.updateReceivablePayable(receivablePayableDTO)).thenReturn(true);


        boolean result = receivablePayableService.updateReceivablePayable(receivablePayableDTO);

        // Verify interactions and assertions
        assertTrue(result); // Assert that the result is true

        // Optionally, verify that DAO method was called with correct arguments
        verify(receivablePayableDao, times(1)).updateReceivablePayable(receivablePayableDTO);
    }

    @Test
    void testDeleteReceivablePayable() {
        ReceivablePayableDTO receivablePayableDTO=null;
        // Set up any necessary data for the test

        // Mock behavior of dependencies
        when(receivablePayableDao.deleteReceivablePayable(receivablePayableDTO)).thenReturn(false);

        // Call the method under test
        boolean result = receivablePayableService.deleteReceivablePayable(receivablePayableDTO);

        // Verify interactions and assertions
        assertFalse(result); // Assert that the result is true

        // Optionally, verify that DAO method was called with correct arguments
        verify(receivablePayableDao, times(1)).deleteReceivablePayable(receivablePayableDTO);
    }

}
