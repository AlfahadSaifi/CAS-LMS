package entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.nucleus.dao.ChargePolicyDao;
import org.nucleus.dao.ChargePolicyDaoImpl;
import org.nucleus.dao.ChargePolicyTempDao;
import org.nucleus.dao.ChargePolicyTempDaoImpl;
import org.nucleus.dto.ChargePolicyDto;
import org.nucleus.dto.ChargePolicyTempDto;
import org.nucleus.exception.PolicyCodeAlreadyExistException;
import org.nucleus.service.*;
import org.nucleus.utility.dtomapper.ChargePolicyDtoMapper;
import org.nucleus.utility.dtomapper.ChargePolicyTempDtoMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChargePolicyServiceImplTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private ChargePolicyDtoMapper chargePolicyDtoMapper;
    @InjectMocks
    private ChargePolicyDaoImpl chargePolicyDaoImpl;
    @Mock
    private ChargePolicyDao chargePolicyDao;
    @InjectMocks
    private ChargePolicyServiceImpl chargePolicyService;
    @Mock
    private ChargePolicyTempDtoMapper chargePolicyTempDtoMapper;
    @InjectMocks
    private ChargePolicyTempDaoImpl chargePolicyTempDaoImpl;
    @Mock
    private ChargePolicyTempDao chargePolicyTempDao;
    @InjectMocks
    private ChargePolicyTempServiceImpl chargePolicyTempService;

    @Test
    public void testForSaveChargePolicy()
    {
        ChargePolicyDto chargePolicyDto = new ChargePolicyDto();
        chargePolicyDto.setPolicyCode("Pol123");
        chargePolicyDto.setPolicyName("Home Loan Policy");

        when(chargePolicyDao.saveChargePolicy(chargePolicyDto)).thenReturn(true);
        chargePolicyService.saveChargePolicy(chargePolicyDto);

        boolean result=chargePolicyService.saveChargePolicy(chargePolicyDto); ;
        assertTrue(result);
    }

    @Test
    public void testForSaveChargePolicyTemp() throws PolicyCodeAlreadyExistException {
        ChargePolicyTempDto chargePolicyTempDto = new ChargePolicyTempDto();
        chargePolicyTempDto.setPolicyCode("Pol123");
        chargePolicyTempDto.setPolicyName("Home Loan Policy");

        when(chargePolicyTempDao.saveChargePolicy(chargePolicyTempDto)).thenReturn(true);
        chargePolicyTempService.saveChargePolicy(chargePolicyTempDto);

        boolean result=chargePolicyTempService.saveChargePolicy(chargePolicyTempDto); ;
        assertTrue(result);
    }

    @Test
    public void testDeleteChargePolicy()
    {
        ChargePolicyDto chargePolicyDto = new ChargePolicyDto();
        chargePolicyDto.setPolicyCode("POL00001");

        when(chargePolicyDao.deleteChargePolicy("POL00001")).thenReturn(true);

        chargePolicyService.deleteChargePolicy("POL00001");
        boolean result = chargePolicyService.deleteChargePolicy("POL00001");

        assertTrue(result);
    }

    @Test
    public void testDeleteTempChargePolicy()
    {
        ChargePolicyTempDto chargePolicyTempDto = new ChargePolicyTempDto();
        chargePolicyTempDto.setPolicyCode("POL00001");

        when(chargePolicyTempDao.deleteChargePolicy("POL00001")).thenReturn(true);

        chargePolicyTempService.deleteChargePolicy("POL00001");
        boolean result = chargePolicyTempService.deleteChargePolicy("POL00001");

        assertTrue(result);
    }

    @Test
    public void testGetChargePolicy()
    {
        ChargePolicyDto chargePolicyDto = new ChargePolicyDto();
        chargePolicyDto.setPolicyCode("POL00001");

        when(chargePolicyDao.getChargePolicy("POL00001")).thenReturn(chargePolicyDto);

        ChargePolicyDto chargePolicyDtoGet = chargePolicyService.getChargePolicy("POL00001");
        assertEquals(chargePolicyDto,chargePolicyDtoGet);
    }
    @Test
    public void testGetTempChargePolicy()
    {
        ChargePolicyTempDto chargePolicyTempDto = new ChargePolicyTempDto();
        chargePolicyTempDto.setPolicyCode("POL00001");

        when(chargePolicyTempDao.getChargePolicy("POL00001")).thenReturn(chargePolicyTempDto);

        ChargePolicyTempDto chargePolicyTempDtoGet = chargePolicyTempService.getChargePolicy("POL00001");
        assertEquals(chargePolicyTempDto,chargePolicyTempDtoGet);
    }
    @Test
    public void testEditChargePolicy()
    {
        ChargePolicyDto chargePolicyDto = new ChargePolicyDto();
        chargePolicyDto.setPolicyCode("POL00001");

        when(chargePolicyDao.editChargePolicy(chargePolicyDto)).thenReturn(true);

        chargePolicyService.editChargePolicy(chargePolicyDto);
        boolean result = chargePolicyService.editChargePolicy(chargePolicyDto);

        assertTrue(result);
    }
    @Test
    public void testUpdateTempChargePolicy()
    {
        ChargePolicyTempDto chargePolicyTempDto = new ChargePolicyTempDto();
        chargePolicyTempDto.setPolicyCode("POL00001");

        when(chargePolicyTempDao.updateChargePolicy(chargePolicyTempDto)).thenReturn(true);

        chargePolicyTempService.updateChargePolicy(chargePolicyTempDto);
        boolean result = chargePolicyTempService.updateChargePolicy(chargePolicyTempDto);

        assertTrue(result);
    }
}
