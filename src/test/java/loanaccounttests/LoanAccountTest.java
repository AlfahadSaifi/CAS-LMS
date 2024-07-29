/*
 *Author: Rakesh Kumar
 *
 */

package loanaccounttests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nucleus.dao.LoanAccountDAO;
import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.dto.LoanAccountRequiredFieldDTO;
import org.nucleus.entity.permanent.ChargePolicyParameter;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.service.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanAccountTest {

    @Mock
    private LoanAccountDAO loanAccountDAO;

    @Mock
    private RepayScheduleService repayScheduleService;

    @Mock
    private LoanApplicationService loanApplicationService;

    @Mock
    private LoanProductService loanProductService;

    @Mock
    private ChargeDefinitionService chargeDefinitionService;

    @InjectMocks
    private LoanAccountServiceImpl loanAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertLoanAccount() {
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        when(loanAccountDAO.insertLoanAccount(loanAccountDTO)).thenReturn(true);

        boolean result = loanAccountService.insertLoanAccount(loanAccountDTO);

        assertTrue(result);
        verify(loanAccountDAO, times(1)).insertLoanAccount(loanAccountDTO);
    }

    @Test
    void testGetByLoanAccountId() {
        Long loanAccountId = 1L;
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        when(loanAccountDAO.getByLoanAccountId(loanAccountId)).thenReturn(loanAccountDTO);

        LoanAccountDTO result = loanAccountService.getByLoanAccountId(loanAccountId);

        assertEquals(loanAccountDTO, result);
        verify(loanAccountDAO, times(1)).getByLoanAccountId(loanAccountId);
    }

    @Test
    void testGetByAccountNumber() {
        String accountNumber = "123456";
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        when(loanAccountDAO.getByAccountNumber(accountNumber)).thenReturn(loanAccountDTO);

        LoanAccountDTO result = loanAccountService.getByAccountNumber(accountNumber);

        assertEquals(loanAccountDTO, result);
        verify(loanAccountDAO, times(1)).getByAccountNumber(accountNumber);
    }

    @Test
    void testUpdateLoanAccount() {
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        when(loanAccountDAO.updateLoanAccount(loanAccountDTO)).thenReturn(true);

        boolean result = loanAccountService.updateLoanAccount(loanAccountDTO);

        assertTrue(result);
        verify(loanAccountDAO, times(1)).updateLoanAccount(loanAccountDTO);
    }

    @Test
    void testDelete() {
        String accountNumber = "123456";
        when(loanAccountDAO.delete(accountNumber)).thenReturn(true);

        boolean result = loanAccountService.delete(accountNumber);

        assertTrue(result);
        verify(loanAccountDAO, times(1)).delete(accountNumber);
    }

    @Test
    void testGetAllLoanAccounts() {
        List<LoanAccountDTO> loanAccounts = Arrays.asList(new LoanAccountDTO(), new LoanAccountDTO());
        when(loanAccountDAO.getAllLoanAccounts()).thenReturn(loanAccounts);

        List<LoanAccountDTO> result = loanAccountService.getAllLoanAccounts();

        assertEquals(loanAccounts, result);
        verify(loanAccountDAO, times(1)).getAllLoanAccounts();
    }

    @Test
    void testIsLoanAccountPresent() {
        String accountNumber = "123456";
        List<LoanAccountDTO> loanAccounts = Arrays.asList(new LoanAccountDTO());
        when(loanAccountDAO.getAllLoanAccounts()).thenReturn(loanAccounts);
        loanAccounts.get(0).setLoanAccountNumber(accountNumber);

        boolean result = loanAccountService.isLoanAccountPresent(accountNumber);

        assertTrue(result);
        verify(loanAccountDAO, times(1)).getAllLoanAccounts();
    }
/*
    @Test
    void testGetAllFields() {
        String accountNumber = "ACC000000298";
        LoanApplication loanApplication = new LoanApplication();
        when(loanApplicationService.getApplicationByAccountNumber(accountNumber)).thenReturn(loanApplication);

        List<RepayScheduleDTO> repayScheduleDTOS = Arrays.asList(new RepayScheduleDTO());
        when(repayScheduleService.fetchRepaySchedule(String.valueOf(anyLong()))).thenReturn(repayScheduleDTOS);

        List<ChargePolicyParameter> chargePolicyParameters = Arrays.asList(new ChargePolicyParameter());
        when(chargeDefinitionService.getChargeDefinitionFromTheMasterTableByCode(anyString())).thenReturn(new ChargeDefinitionDTO());

        loanApplication.setLoanAmount(1000.0);

        LoanAccountRequiredFieldDTO result = loanAccountService.getAllFields(accountNumber);

        //assertNotNull(result);
        verify(loanApplicationService, times(1)).getApplicationByAccountNumber(accountNumber);
    }*/

    @Test
    void testGetRowCount() {
        Long rowCount = 10L;
        when(loanAccountDAO.getRowCount()).thenReturn(rowCount);

        Long result = loanAccountService.getRowCount();

        assertEquals(rowCount, result);
        verify(loanAccountDAO, times(1)).getRowCount();
    }

    @Test
    void testGetLoanAccountsInBatch() {
        int offset = 0;
        int batchSize = 10;
        List<LoanAccountDTO> loanAccounts = Arrays.asList(new LoanAccountDTO());
        when(loanAccountDAO.getLoanAccountsInBatch(offset, batchSize)).thenReturn(loanAccounts);

        List<LoanAccountDTO> result = loanAccountService.getLoanAccountsInBatch(offset, batchSize);

        assertEquals(loanAccounts, result);
        verify(loanAccountDAO, times(1)).getLoanAccountsInBatch(offset, batchSize);
    }
}
