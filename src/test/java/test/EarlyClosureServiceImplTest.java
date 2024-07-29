package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nucleus.dao.LoanClosureDAO;
import org.nucleus.dao.LoanClosureDAOTemp;
import org.nucleus.dto.*;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.service.CustomerService;
import org.nucleus.service.EarlyClosureServiceImpl;
import org.nucleus.service.LoanAccountService;
import org.nucleus.utility.enums.ClosureStatus;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EarlyClosureServiceImplTest {

    @Mock
    private LoanClosureDAOTemp loanClosureDaoTemp;
    @Mock
    private LoanAccountService loanAccountService;

    @Mock
    private LoanClosureDAO loanClosureDAO;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private EarlyClosureServiceImpl earlyClosureService;

    @Test
    void testCreateLoanClosureTemp_ValidInput() {
        // Setup
        LoanClosureDTO loanClosureDTO = new LoanClosureDTO(); // Create a sample DTO
        Long expectedId = 123L; // Sample ID returned by the DAO
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(loanClosureDaoTemp.addEarlyClosureData(any(LoanClosureDTO.class))).thenReturn(expectedId);

        // Execute
        Long result = earlyClosureService.createLoanClosureTemp(loanClosureDTO);

        // Verify
        assertNotNull(result);
        assertEquals(expectedId, result);

        // Additional Verification (Optional)
        assertNotNull(loanClosureDTO.getMetaData());
        assertEquals("testUser", loanClosureDTO.getMetaData().getCreatedBy());
    }
    @Test
    void testCreateLoanClosure_ValidInput() {
        // Setup
        LoanClosureDTO loanClosureDTO = new LoanClosureDTO(); // Create a sample DTO
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(loanClosureDAO.save(any(LoanClosureDTO.class))).thenReturn(true); // Simulate successful save

        // Execute
        boolean result = earlyClosureService.createLoanClosure(loanClosureDTO);

        // Verify
        assertTrue(result);  // Verify that true is returned for successful save

        // Additional Verification (Optional)
        assertNotNull(loanClosureDTO.getMetaData());
        assertEquals("testUser", loanClosureDTO.getMetaData().getCreatedBy());
        assertEquals("testUser", loanClosureDTO.getMetaData().getAuthorizedBy());
        assertEquals(RecordStatus.A, loanClosureDTO.getMetaData().getRecordStatus());
        assertNotNull(loanClosureDTO.getMetaData().getCreationDate());
        assertNotNull(loanClosureDTO.getMetaData().getAuthorizedDate());
    }

    @Test
    void testGetLoanClosureTempById_NullInput() {
        LoanClosureDTO result = earlyClosureService.getLoanClosureTempById(null);
        assertNull(result);
    }

    @Test
    void testGetLoanClosureTempById_ValidInput() {
        Long id = 123L;
        LoanClosureDTO expectedDTO = new LoanClosureDTO(); // Create a sample DTO
        when(loanClosureDaoTemp.getLoanClosureDetail(id)).thenReturn(expectedDTO);

        LoanClosureDTO result = earlyClosureService.getLoanClosureTempById(id);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    void testDeleteLoanClosure_NullInput() {
        boolean result = earlyClosureService.deleteLoanClosure(null);
        assertFalse(result);
    }

    @Test
    void testDeleteLoanClosure_ValidInput() {
        Long id = 123L;
        when(loanClosureDaoTemp.delete(id)).thenReturn(true); // Simulate successful delete

        boolean result = earlyClosureService.deleteLoanClosure(id);

        assertTrue(result);
    }

    @Test
    void testGetAllMakerFields()
    {
        String loanAccountNumber = "12345";
        String cifNumber = "67890";
        String customerName = "John Doe";
        double loanAmount = 10000.0;
        double loanAmountPaid = 2000.0;
        int balanceInstallments = 10;

        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        loanAccountDTO.setCifNumber(cifNumber);
        loanAccountDTO.setFinalSanctionedAmount(loanAmount);
        loanAccountDTO.setLoanAmountPaidByCustomer(loanAmountPaid);
        loanAccountDTO.setNumberOfInstallmentUnbilled(balanceInstallments);

        CustomerDTO customerDTO = new CustomerDTO();
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setFullName(customerName);
        customerDTO.setPersonInfoDTO(personInfoDTO);

        // Mock Service Calls
        when(loanAccountService.getByAccountNumber(loanAccountNumber)).thenReturn(loanAccountDTO);
        when(customerService.getByCIFNumber(cifNumber)).thenReturn(customerDTO);

        // Execute Method Under Test
        MakerEarlyClosureDTO result = earlyClosureService.getAllMakerFields(loanAccountNumber);

        // Assertions
        assertNotNull(result);
        assertEquals(loanAccountNumber, result.getLoanAccountNumber());
        assertEquals(customerName, result.getCustomerName());
        assertEquals(balanceInstallments, result.getBalanceInstallment());
        assertEquals(loanAmount, result.getLoanAmount());
        assertEquals(loanAmount - loanAmountPaid, result.getBalancePrinciple());
        assertEquals(loanAmount - loanAmountPaid, result.getTotalDuePrincipal());
        assertEquals(0.0, result.getDueCharges());
        assertEquals(loanAmount - loanAmountPaid, result.getTotalClosureAmount());
    }

    @Test
    void testGetAllCheckerFields_LoanAccountNotFound() {
        String loanAccountNumber = "12345";
        when(loanAccountService.getByAccountNumber(loanAccountNumber)).thenReturn(null);

        CheckerEarlyClosureDTO result = earlyClosureService.getAllCheckerFields(loanAccountNumber);

        assertNull(result);
    }

    @Test
    void testGetAllCheckerFields_LoanClosureNotFound() {
        String loanAccountNumber = "12345";
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        loanAccountDTO.setLoanAccountId(1L);
        when(loanAccountService.getByAccountNumber(loanAccountNumber)).thenReturn(loanAccountDTO);
        when(loanClosureDaoTemp.findLoanClosureByLoanAccountId(1L)).thenReturn(null);

        CheckerEarlyClosureDTO result = earlyClosureService.getAllCheckerFields(loanAccountNumber);

        assertNull(result);
    }

    @Test
    void testGetAllCheckerFields_ValidInput() {
        // Setup Mock Data
        String loanAccountNumber = "12345";
        Long loanAccountId = 1L;
        double finalSanctionedAmount = 10000.0;
        double loanAmountPaidByCustomer = 2000.0;
        LocalDate creationDate = LocalDate.now();
        Date loanClosureDate = Date.valueOf(LocalDate.now().plusDays(10)); // Example
        ClosureStatus closureStatus = ClosureStatus.EARLY_CLOSURE;

        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        loanAccountDTO.setLoanAccountId(loanAccountId);
        loanAccountDTO.setFinalSanctionedAmount(finalSanctionedAmount);
        loanAccountDTO.setLoanAmountPaidByCustomer(loanAmountPaidByCustomer);

        LoanClosureDTO loanClosureDTO = new LoanClosureDTO();
        loanClosureDTO.setLoanClosureId(123L); // Example
        loanClosureDTO.setClosureStatus(closureStatus);
        loanClosureDTO.setLoanClosureDate(loanClosureDate);
        MetaData metaData = new MetaData();
        metaData.setCreationDate(Date.valueOf(creationDate));
        loanClosureDTO.setMetaData(metaData);

        // Mock Service Calls
        when(loanAccountService.getByAccountNumber(loanAccountNumber)).thenReturn(loanAccountDTO);
        when(loanClosureDaoTemp.findLoanClosureByLoanAccountId(loanAccountId)).thenReturn(loanClosureDTO);

        // Execute Method Under Test
        CheckerEarlyClosureDTO result = earlyClosureService.getAllCheckerFields(loanAccountNumber);

        // Assertions
        assertNotNull(result);
        assertEquals(loanClosureDTO.getLoanClosureId(), result.getLoanClosureId());
        assertEquals(closureStatus, result.getClosureStatus());
        assertEquals(finalSanctionedAmount, result.getFinalSanctionedAmount());
        assertEquals(loanClosureDate, result.getLoanClosureDate());
        assertEquals(loanAccountNumber, result.getLoanAccountNumber());
        assertEquals(loanAmountPaidByCustomer, result.getLoanAmountPaidByCustomer());
        assertEquals(Date.valueOf(creationDate.plusMonths(1)), result.getDueDate());
        assertEquals(metaData, result.getMetaData());
    }
    @Test
    void testUpdateLoanClosureTemp_NullInput()
    {
        boolean result = earlyClosureService.updateLoanClosureTemp(null);
        assertFalse(result);
    }

    @Test
    void testUpdateLoanClosureTemp_ValidInput()
    {
        // Setup
        LoanClosureDTO loanClosureDTO = new LoanClosureDTO();
        MetaData metaData = new MetaData();
        loanClosureDTO.setMetaData(metaData); // Set metadata
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(loanClosureDaoTemp.update(loanClosureDTO)).thenReturn(true);

        // Execute
        boolean result = earlyClosureService.updateLoanClosureTemp(loanClosureDTO);

        // Verify
        assertTrue(result);
        assertEquals(RecordStatus.NR, loanClosureDTO.getMetaData().getRecordStatus());
        assertEquals("testUser", loanClosureDTO.getMetaData().getModifiedBy());
        assertNotNull(loanClosureDTO.getMetaData().getModifiedDate());
    }

    @Test
    void testGetLoanClosureByAccountId()
    {
        LoanClosureDTO loanClosureDTO = new LoanClosureDTO();
        when(loanClosureDaoTemp.findLoanClosureByLoanAccountId(1L)).thenReturn(loanClosureDTO);
        LoanClosureDTO result = earlyClosureService.getLoanClosureByAccountId(1L);
        verify(loanClosureDaoTemp, times(1)).findLoanClosureByLoanAccountId(1L);
        assertNotNull(result);
        assertEquals(loanClosureDTO, result);
    }

    @Test
    void testUpdateLoanAccount()
    {
        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        when(loanAccountService.updateLoanAccount(loanAccountDTO)).thenReturn(true);
        boolean result = earlyClosureService.updateLoanAccount(loanAccountDTO);
        assertTrue(result);
    }

    @Test
    void testDeleteLoanClosureByDTO()
    {
        LoanClosureDTO loanClosureDTO = new LoanClosureDTO();

        when(loanClosureDaoTemp.delete(loanClosureDTO)).thenReturn(true);

        boolean result = earlyClosureService.deleteLoanClosureByDTO(loanClosureDTO);

        assertTrue(result);
    }
}

