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
public class ProductTypeServiceImplTest {

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
    }

    @Test
    void testGetAllProductTypesForMaker() {
        List<ProductTypeDTO> productTypeTempList = new ArrayList<>();
        List<ProductTypeDTO> productTypeList = new ArrayList<>();
        productTypeTempList.add(productTypeDTO);
        productTypeList.add(productTypeDTO);

        when(productTypeTempDAO.getAllProductTypes()).thenReturn(productTypeTempList);
        when(productTypeDAO.getAllValidProductTypes()).thenReturn(productTypeList);

        List<ProductTypeDTO> result = productTypeService.getAllProductTypesForMaker();
        assertEquals(1, result.size());
        verify(productTypeTempDAO, times(1)).getAllProductTypes();
        verify(productTypeDAO, times(1)).getAllValidProductTypes();
    }

    @Test
    void testGetProductTypeWithSaveFlag() {
        when(productTypeTempDAO.getProductTypeWithSaveFlag()).thenReturn(productTypeDTO);
        ProductTypeDTO result = productTypeService.getProductTypeWithSaveFlag();
        assertEquals(productTypeDTO, result);

        when(productTypeTempDAO.getProductTypeWithSaveFlag()).thenReturn(null);
        result = productTypeService.getProductTypeWithSaveFlag();
        assertEquals(new ProductTypeDTO(), result);
    }

    @Test
    void testGetProductTypeByIDAndStatus() {
        when(productTypeDAO.getProductTypeByID(anyLong())).thenReturn(productTypeDTO);
        when(productTypeTempDAO.getProductTypeByID(anyLong())).thenReturn(productTypeDTO);

        ProductTypeDTO result = productTypeService.getProductTypeByIDAndStatus(1L, RecordStatus.A);
        assertEquals(productTypeDTO, result);

        result = productTypeService.getProductTypeByIDAndStatus(1L, RecordStatus.N);
        assertEquals(productTypeDTO, result);
    }

    @Test
    void testDeleteProductType() {
        when(productTypeDAO.getProductTypeByID(anyLong())).thenReturn(productTypeDTO);
        when(productTypeTempDAO.addProductType(any(ProductTypeDTO.class))).thenReturn(true);
        when(productTypeTempDAO.deleteProductType(anyLong())).thenReturn(true);

        String result = productTypeService.deleteProductType(1L, RecordStatus.A);
        assertEquals("Product type requested for deletion!", result);

        result = productTypeService.deleteProductType(1L, RecordStatus.N);
        assertEquals("Product type deleted successfully!", result);
    }

    @Test
    void testGetAllProductTypesForChecker() {
        List<ProductTypeDTO> productTypeTempList = new ArrayList<>();
        List<ProductTypeDTO> productTypeList = new ArrayList<>();
        productTypeTempList.add(productTypeDTO);
        productTypeList.add(productTypeDTO);

        when(productTypeTempDAO.getAllValidProductTypes()).thenReturn(productTypeTempList);
        when(productTypeDAO.getAllProductTypes()).thenReturn(productTypeList);

        List<ProductTypeDTO> result = productTypeService.getAllProductTypesForChecker();
        assertEquals(1, result.size());
        verify(productTypeTempDAO, times(1)).getAllValidProductTypes();
        verify(productTypeDAO, times(1)).getAllProductTypes();
    }

    @Test
    void testRejectProductType() {
        when(productTypeTempDAO.getProductTypeByID(anyLong())).thenReturn(productTypeDTO);
        when(productTypeTempDAO.updateProductType(any(ProductTypeDTO.class))).thenReturn(true);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.N);
        String result = productTypeService.rejectProductType(1L);
        assertEquals("Successfully Rejected!", result);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.M);
        result = productTypeService.rejectProductType(1L);
        assertEquals("Successfully Rejected!", result);

        productTypeDTO.getMetaData().setRecordStatus(RecordStatus.D);
        result = productTypeService.rejectProductType(1L);
        assertEquals("Successfully Rejected!", result);
    }

    @Test
    void testGetAllProductTypeCodes() {
        List<String> codes = new ArrayList<>();
        codes.add("PTC01");
        when(productTypeDAO.getAllProductTypeCodes()).thenReturn(codes);

        List<String> result = productTypeService.getAllProductTypeCodes();
        assertEquals(1, result.size());
        verify(productTypeDAO, times(1)).getAllProductTypeCodes();
    }

    @Test
    void testGetProductTypeByCode() {
        when(productTypeDAO.getProductTypeByCode(anyString())).thenReturn(productTypeDTO);

        ProductTypeDTO result = productTypeService.getProductTypeByCode("PTC01");
        assertEquals(productTypeDTO, result);
    }
}