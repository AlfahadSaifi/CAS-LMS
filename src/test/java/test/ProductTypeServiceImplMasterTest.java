package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nucleus.dao.ProductTypeDAO;
import org.nucleus.dao.ProductTypeTempDAO;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.ProductTypeServiceImpl;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductTypeServiceImplMasterTest {

    @Mock
    private ProductTypeDAO productTypeDAO;

    @Mock
    private ProductTypeTempDAO productTypeTempDAO;

    @InjectMocks
    private ProductTypeServiceImpl productTypeService;

    private ProductTypeDTO productTypeDTO;

    @BeforeEach
    void setUp() {
        productTypeDTO = new ProductTypeDTO();
        productTypeDTO.setProductTypeId(1L);
        productTypeDTO.setProductTypeCode("PTC01");
        productTypeDTO.setDescription("Test Product Type");
        productTypeDTO.setFundBasedFlag(null);
        productTypeDTO.setSecuredFlag('Y');
        productTypeDTO.setMetaData(new TempMetaData());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testSaveProductType() {
        when(productTypeTempDAO.getProductTypeByCode(anyString())).thenReturn(null);
        when(productTypeDAO.getProductTypeByCode(anyString())).thenReturn(null);
        when(productTypeTempDAO.addProductType(any(ProductTypeDTO.class))).thenReturn(true);

        String result = productTypeService.saveProductType(productTypeDTO);
        assertEquals("Product type requested to be added!", result);

        when(productTypeTempDAO.getProductTypeByCode(anyString())).thenReturn(productTypeDTO);
        productTypeDTO.getMetaData().setSaveFlag(true);

        result = productTypeService.saveProductType(productTypeDTO);
        assertEquals("Product type requested to be Added!", result);
    }

    @Test
    void testUpdateProductType() {
        when(productTypeDAO.getProductTypeByID(anyLong())).thenReturn(productTypeDTO);
        when(productTypeTempDAO.addProductType(any(ProductTypeDTO.class))).thenReturn(true);
        when(productTypeTempDAO.getProductTypeByID(anyLong())).thenReturn(productTypeDTO);
        when(productTypeTempDAO.updateProductType(any(ProductTypeDTO.class))).thenReturn(true);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.A);
        String result = productTypeService.updateProductType(productTypeDTO);
        assertEquals("Product type requested to be updated!", result);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.NR);
        result = productTypeService.updateProductType(productTypeDTO);
        assertEquals("Product Type Updated Successfully!", result);
    }
    @Test
    void testApproveProductType() {
        when(productTypeTempDAO.getProductTypeByID(anyLong())).thenReturn(productTypeDTO);
        when(productTypeTempDAO.deleteProductType(anyLong())).thenReturn(true);
        when(productTypeDAO.updateProductType(any(ProductTypeDTO.class))).thenReturn(true);
        when(productTypeDAO.getProductTypeByCode(anyString())).thenReturn(productTypeDTO);
        when(productTypeDAO.addProductType(any(ProductTypeDTO.class))).thenReturn(true);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.M);
        String result = productTypeService.approveProductType(1L);
        assertEquals("Modification Successfully Approved!", result);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.D);
        result = productTypeService.approveProductType(1L);
        assertEquals("Deletion Successfully Approved!", result);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.N);
        result = productTypeService.approveProductType(1L);
        assertEquals("Successfully Approved!", result);
    }
}