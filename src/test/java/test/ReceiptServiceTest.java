package test;/*
Developer: Vibhav Sehrawat
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.nucleus.dao.ReceiptDAO;
import org.nucleus.dao.ReceiptDAOTemp;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.ReceiptServiceImpl;
import org.nucleus.service.ReceivablePayableService;
import org.nucleus.utility.enums.PaymentMode;
import org.nucleus.utility.enums.ReceivablePayableType;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ReceiptServiceTest {

    @Mock
    private ReceiptDAO receiptDAO;

    @Mock
    private ReceiptDAOTemp receiptDAOTemp;

    @Mock
    private ReceivablePayableService receivablePayableService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @BeforeEach
    void setup() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
    }

    @Test
    void testCreateReceiptDTO() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAO.createReceipt(receiptDTO)).thenReturn(true);

        boolean result = receiptService.createReceiptDTO(receiptDTO);

        assertTrue(result);
        verify(receiptDAO, times(1)).createReceipt(receiptDTO);
    }

    @Test
    void testGetReceiptDTOById() {
        Long id = 1L;
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAO.getReceiptById(id)).thenReturn(receiptDTO);

        ReceiptDTO result = receiptService.getReceiptDTOById(id);

        assertEquals(receiptDTO, result);
        verify(receiptDAO, times(1)).getReceiptById(id);
    }

    @Test
    void testGetAllReceiptDTOs() {
        List<ReceiptDTO> receiptList = Arrays.asList(new ReceiptDTO(), new ReceiptDTO());
        when(receiptDAO.getAllReceipts()).thenReturn(receiptList);

        List<ReceiptDTO> result = receiptService.getAllReceiptDTOs();

        assertEquals(receiptList, result);
        verify(receiptDAO, times(1)).getAllReceipts();
    }

    @Test
    void testUpdateReceiptDTO() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAO.updateReceipt(receiptDTO)).thenReturn(true);

        boolean result = receiptService.updateReceiptDTO(receiptDTO);

        assertTrue(result);
        verify(receiptDAO, times(1)).updateReceipt(receiptDTO);
    }

    @Test
    void testDeleteReceiptDTO() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAO.deleteReceipt(receiptDTO)).thenReturn(true);

        boolean result = receiptService.deleteReceiptDTO(receiptDTO);

        assertTrue(result);
        verify(receiptDAO, times(1)).deleteReceipt(receiptDTO);
    }

    @Test
    void testCreateReceiptTemp() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAOTemp.createReceiptTemp(receiptDTO)).thenReturn(true);

        boolean result = receiptService.createReceiptTemp(receiptDTO);

        assertTrue(result);
        verify(receiptDAOTemp, times(1)).createReceiptTemp(receiptDTO);
    }

    @Test
    void testGetReceiptTempById() {
        Long id = 1L;
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAOTemp.getReceiptTempById(id)).thenReturn(receiptDTO);

        ReceiptDTO result = receiptService.getReceiptTempById(id);

        assertEquals(receiptDTO, result);
        verify(receiptDAOTemp, times(1)).getReceiptTempById(id);
    }

    @Test
    void testGetAllReceiptTemps() {
        List<ReceiptDTO> receiptList = Arrays.asList(new ReceiptDTO(), new ReceiptDTO());
        when(receiptDAOTemp.getAllReceiptTemps()).thenReturn(receiptList);

        List<ReceiptDTO> result = receiptService.getAllReceiptTemps();

        assertEquals(receiptList, result);
        verify(receiptDAOTemp, times(1)).getAllReceiptTemps();
    }

    @Test
    void testUpdateReceiptTemp() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAOTemp.updateReceiptTemp(receiptDTO)).thenReturn(true);

        boolean result = receiptService.updateReceiptTemp(receiptDTO);

        assertTrue(result);
        verify(receiptDAOTemp, times(1)).updateReceiptTemp(receiptDTO);
    }

    @Test
    void testDeleteReceiptTemp() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        when(receiptDAOTemp.deleteReceiptTemp(receiptDTO)).thenReturn(true);

        boolean result = receiptService.deleteReceiptTemp(receiptDTO);

        assertTrue(result);
        verify(receiptDAOTemp, times(1)).deleteReceiptTemp(receiptDTO);
    }

    @Test
    void testGetListReceiptTempById() {
        Long id = 1L;
        List<ReceiptDTO> receiptList = Arrays.asList(new ReceiptDTO(), new ReceiptDTO());
        when(receiptDAOTemp.getListReceiptTempById(id)).thenReturn(receiptList);

        List<ReceiptDTO> result = receiptService.getListReceiptTempById(id);

        assertEquals(receiptList, result);
        verify(receiptDAOTemp, times(1)).getListReceiptTempById(id);
    }

    @Test
    void testCalculateReceivableDetails() {
        Long receivablePayableId = 1L;
        ReceivablePayableDTO receivablePayableDTO = new ReceivablePayableDTO();
        receivablePayableDTO.setReceivablePayableType(ReceivablePayableType.RECEIVABLE);
        receivablePayableDTO.setReceivablePayableDueDate(Date.valueOf(LocalDate.now().minusDays(10)));
        receivablePayableDTO.setReceivablePayableAmount(1000.0);

        when(receivablePayableService.getReceivablePayable(receivablePayableId)).thenReturn(receivablePayableDTO);

        ReceiptDTO result = receiptService.calculateReceivableDetails(receivablePayableId);

        assertNotNull(result);
        assertEquals(2000.0, result.getRequiredAmount()); // Assuming 10 days penalty of 100 per day
    }

    @Test
    void testSubmitReceipt() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setRequiredAmount(1000.0);
        receiptDTO.setTransactionAmount(800.0);
        receiptDTO.setPaymentMode(PaymentMode.CASH);
        receiptDTO.setReceivablePayable(new ReceivablePayableDTO());
        receiptDTO.getReceivablePayable().setReceivablePayableId(1L);

        when(receivablePayableService.getReceivablePayable(1L)).thenReturn(new ReceivablePayableDTO());
        when(receiptDAOTemp.createReceiptTemp(receiptDTO)).thenReturn(true);

        boolean result = receiptService.submitReceipt(receiptDTO);

        assertTrue(result);
        assertEquals(RecordStatus.N, receiptDTO.getTempMetaData().getRecordStatus());
        assertEquals("testUser", receiptDTO.getTempMetaData().getCreatedBy());
    }

    @Test
    void testGetReceiptDTOListByReceivableId() {
        Long receivablePayableId = 1L;
        Date fromDate = Date.valueOf(LocalDate.now().minusDays(10));
        Date toDate = Date.valueOf(LocalDate.now());
        ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setTransactionDate(Date.valueOf(LocalDate.now().minusDays(5)));

        when(receiptDAOTemp.getListReceiptTempById(receivablePayableId)).thenReturn(Arrays.asList(receiptDTO));

        List<ReceiptDTO> result = receiptService.getReceiptDTOListByReceivableId(receivablePayableId, fromDate, toDate);

        assertEquals(1, result.size());
    }

    @Test
    void testApproveReceiptByReceiptId() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        TempMetaData tempMetaData = new TempMetaData();
        receiptDTO.setTempMetaData(tempMetaData);

        ReceiptDTO result = receiptService.approveReceiptByReceiptId(receiptDTO);

        assertNotNull(result);
        assertEquals(RecordStatus.A, result.getTempMetaData().getRecordStatus());
        assertEquals("testUser", result.getTempMetaData().getAuthorizedBy());
    }
}