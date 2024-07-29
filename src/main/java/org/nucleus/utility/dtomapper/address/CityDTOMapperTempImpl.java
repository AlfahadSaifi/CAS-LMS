package org.nucleus.utility.dtomapper.address;
import org.nucleus.dto.CityDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.entity.temporary.CityTemp;
import org.nucleus.service.address.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CityDTOMapperTempImpl implements CityDTOMapperTemp {
    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private StateService stateService;
    @Override
    public CityTemp dtoToCityTemp(CityDTO cityDTO) {
        if(cityDTO==null)return null;

        CityTemp cityTemp = new CityTemp();
        cityTemp.setCityId(cityDTO.getId());
        cityTemp.setCityCode(cityDTO.getCityCode());
        cityTemp.setState(stateMapper.stateDtoToStatePermanent(cityDTO.getStateDto()));
        cityTemp.setCityName(cityDTO.getCityName());
        cityTemp.setCityMICRCode(cityDTO.getCityMICRCode());
        cityTemp.setStdCode(cityDTO.getStdCode());
        cityTemp.setLocationType(cityDTO.getLocationType());
        cityTemp.setMetaData(cityDTO.getMetaData());

        return cityTemp;
    }

    @Override
    public CityDTO cityTempToCityDto(CityTemp cityTemp) {
        if(cityTemp==null)return null;
        CityDTO cityDTO = new CityDTO();

        cityDTO.setCityCode(cityTemp.getCityCode());
        cityDTO.setCityName(cityTemp.getCityName());
        cityDTO.setCityMICRCode(cityTemp.getCityMICRCode());
        StateDTO stateDTO = stateService.getStateByStateName(cityTemp.getState().getStateName());
        cityDTO.setStateDto(stateDTO);
        cityDTO.setCityCode(cityTemp.getCityCode());
        cityDTO.setId(cityTemp.getCityId());
        cityDTO.setMetaData(cityTemp.getMetaData());
        cityDTO.setStdCode(cityTemp.getStdCode());

        return cityDTO;
    }
}
