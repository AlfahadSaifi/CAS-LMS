package entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.nucleus.dao.address.StateDAO;
import org.nucleus.dao.address.StateTempDAO;
import org.nucleus.dto.CountryDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.service.address.StateServiceImpl;
import org.nucleus.service.address.StateTempServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StateServiceTest {

    @Mock
    private StateDAO stateDAO;

    @InjectMocks
    private StateServiceImpl stateService;

    @Mock
    private StateTempDAO stateTempDAO;

    @InjectMocks
    private StateTempServiceImpl stateTempService;



    @Test
    public void testSaveState() {

        CountryDTO countryDTO  = new CountryDTO();
        countryDTO.setId(101);
        countryDTO.setCountryName("India");
        countryDTO.setCountryIsdCode(91);
        countryDTO.setCountryIsoCode("IND");
        countryDTO.setNationality("Indian");

        StateDTO inputStateDTO = new StateDTO();
        inputStateDTO.setId(110);
        inputStateDTO.setStateCode("HR");
        inputStateDTO.setStateName("Haryana");
        inputStateDTO.setRegion("North");
        inputStateDTO.setCountry(countryDTO);

        when(stateDAO.saveState(inputStateDTO)).thenReturn(true);

        boolean saveState =  stateService.saveState(inputStateDTO);

        assertTrue(saveState);

    }

    @Test
    public void testDeleteState() {

        when(stateDAO.deleteState(101)).thenReturn(true);

        boolean deleteState = stateService.deleteState(101);

        assertEquals(true,deleteState);

    }


    @Test
    public void testSaveTempState()
    {
        CountryDTO countryDTO  = new CountryDTO();
        countryDTO.setId(101);
        countryDTO.setCountryName("India");
        countryDTO.setCountryIsdCode(91);
        countryDTO.setCountryIsoCode("IND");
        countryDTO.setNationality("Indian");

        StateDTO inputStateDTO = new StateDTO();
        inputStateDTO.setStateCode("HR");
        inputStateDTO.setStateName("Haryana");
        inputStateDTO.setRegion("North");
        inputStateDTO.setCountry(countryDTO);

        when(stateTempService.saveState(inputStateDTO)).thenReturn(true);

        boolean savedState = stateTempService.saveState(inputStateDTO);

        assertEquals(true,savedState);
    }


    @Test
    public void testExistingState()
    {
        StateDTO stateDTO = new StateDTO();

        stateDTO.setStateCode("23234");
        stateDTO.setStateName("Goa");
        stateDTO.setRegion("South");


        when(stateTempDAO.doesStateExist("Goa")).thenReturn(true);

        boolean resultExist = stateTempService.doesStateExist("Goa");

        assertEquals(true,resultExist);

    }


    @Test
    public void testDeleteTempState()
    {
        StateDTO inputStateDTO = new StateDTO();
        inputStateDTO.setStateCode("HR");
        inputStateDTO.setStateName("Haryana");
        inputStateDTO.setRegion("North");

        when(stateTempDAO.deleteState(102)).thenReturn(true);

        stateTempService.deleteState(102);

        boolean resultDelete = stateTempService.deleteState(102);

        assertEquals(true,resultDelete);
    }

    @Test
    public void testStateDtoById()
    {
        StateDTO inputStateDTO = new StateDTO();
        inputStateDTO.setId(1);
        inputStateDTO.setStateCode("HR");
        inputStateDTO.setStateName("Haryana");
        inputStateDTO.setRegion("North");

        when(stateDAO.getStateDtoById(1)).thenReturn(inputStateDTO);

        StateDTO stateDTO =stateService.getStateDtoById(1);

        assertEquals(stateDTO,inputStateDTO);

    }

    @Test
    public void testStateDtoByIdFromStateTemp()
    {
        StateDTO inputStateDTO = new StateDTO();
        inputStateDTO.setId(1);
        inputStateDTO.setStateCode("HR");
        inputStateDTO.setStateName("Haryana");
        inputStateDTO.setRegion("North");

        when(stateTempDAO.getStateDtoById(1)).thenReturn(inputStateDTO);

        StateDTO stateDTO =stateTempService.getStateDtoById(1);

        assertEquals(stateDTO,inputStateDTO);

    }







}

