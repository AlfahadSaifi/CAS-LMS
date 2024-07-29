//Abhishek Nayak

package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nucleus.dao.LoanApplicationDAO;
import org.nucleus.dao.LoanApplicationTempDAO;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.service.LoanApplicationServiceImpl;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class loanApplicationTest {

    @Mock
    private LoanApplicationTempDAO loanApplicationTemporaryDAO;

    @Mock
    private LoanApplicationDAO loanApplicationDAO;

    @Mock
    private LoanApplicationTempDAO loanApplicationTempDAO;

    @InjectMocks
    private LoanApplicationServiceImpl loanApplicationServiceImpl;


    @Test
    void testReadAny_FromPermanentSource() {
        String loanApplicationId = "123";
        LoanApplicationDTO expectedDTO = new LoanApplicationDTO();

        // Mock readPermanentByLoanApplicationId to return a LoanApplicationDTO
        when(loanApplicationDAO.readByLoanApplicationId(loanApplicationId)).thenReturn(expectedDTO);

        // Call the method to be tested
        LoanApplicationDTO result = loanApplicationServiceImpl.readAny(loanApplicationId);

        // Verify that readPermanentByLoanApplicationId was called with the correct parameter
        verify(loanApplicationDAO, times(1)).readByLoanApplicationId(loanApplicationId);

        assertNotNull(result, "Expected non-null LoanApplicationDTO");
        assertSame(expectedDTO, result, "Expected LoanApplicationDTO from permanent source");
    }

    @Test
    void testReadAny_FromTemporarySource() {
        String loanApplicationId = "123";
        LoanApplicationDTO expectedDTO = new LoanApplicationDTO();

        when(loanApplicationDAO.readByLoanApplicationId(loanApplicationId)).thenReturn(null);

        when(loanApplicationTemporaryDAO.readByLoanApplicationId(loanApplicationId)).thenReturn(expectedDTO);

        LoanApplicationDTO result = loanApplicationServiceImpl.readAny(loanApplicationId);

        // Verify that readPermanentByLoanApplicationId and readTemporaryByLoanApplicationId were called
        verify(loanApplicationDAO, times(1)).readByLoanApplicationId(loanApplicationId);
        verify(loanApplicationTemporaryDAO, times(1)).readByLoanApplicationId(loanApplicationId);

        // Verify the result
        assertNotNull(result, "Expected non-null LoanApplicationDTO");
        assertSame(expectedDTO, result, "Expected LoanApplicationDTO from temporary source");
    }

    @Test
    void testUpdateTemporary() {
        // Mocking LoanApplicationDTO
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setRecordStatus(RecordStatus.A); // Set record status to A for testing
        loanApplicationDTO.setModifiedDate(null); // Initially, modified date is null
        loanApplicationDTO.setModifiedBy(null); // Initially, modified by is null

        // Mocking Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Mocking DAO methods
//        when(loanApplicationTemporaryDAO.create(any(LoanApplicationDTO.class))).thenReturn(1L);
        when(loanApplicationTemporaryDAO.merge(any(LoanApplicationDTO.class))).thenReturn(true);

        // Call the method to be tested
        boolean result = loanApplicationServiceImpl.updateTemporary(loanApplicationDTO);

        // Verify the changes made to loanApplicationDTO
        assertEquals(RecordStatus.M, loanApplicationDTO.getRecordStatus()); // Record status should be updated to M
        assertNotNull(loanApplicationDTO.getModifiedDate()); // Modified date should be set
        assertEquals("testUser", loanApplicationDTO.getModifiedBy()); // Modified by should be set to the authenticated user

        // Verify DAO method calls based on record status
        if (loanApplicationDTO.getRecordStatus() == RecordStatus.A) {
            verify(loanApplicationTemporaryDAO, times(1)).create(any(LoanApplicationDTO.class)); // create should be called once
            verify(loanApplicationTemporaryDAO, never()).merge(any(LoanApplicationDTO.class)); // merge should not be called
        } else {
            verify(loanApplicationTemporaryDAO, never()).create(any(LoanApplicationDTO.class)); // create should not be called
            verify(loanApplicationTemporaryDAO, times(1)).merge(any(LoanApplicationDTO.class)); // merge should be called once
        }
        assertTrue(result);
    }

    @Test
    void testDeleteTemporary() {
        String loanApplicationId = "123";

        when(loanApplicationTemporaryDAO.delete(loanApplicationId)).thenReturn(true);
        boolean result = loanApplicationServiceImpl.deleteTemporary(loanApplicationId);
        verify(loanApplicationTemporaryDAO, times(1)).delete(loanApplicationId);

        assertTrue(result, "Expected deleteTemporary to return true");
    }


}

