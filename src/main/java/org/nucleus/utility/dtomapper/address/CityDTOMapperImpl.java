package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CityDTO;
import org.nucleus.entity.permanent.City;
import org.nucleus.utility.dtomapper.MetaDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityDTOMapperImpl implements CityDTOMapper {
    @Autowired
    private StateMapper stateMapper;

    @Override
    public City dtoToCity(CityDTO cityDTO) {
        if(cityDTO==null)return null;
        City city = new City();
        city.setCityCode(cityDTO.getCityCode());
        city.setCityName(cityDTO.getCityName());
        city.setCityMICRCode(cityDTO.getCityMICRCode());
        city.setStdCode(cityDTO.getStdCode());
        city.setId(cityDTO.getId());
        city.setMetaData(MetaDataMapper.tempToMetaData(cityDTO.getMetaData()));
        city.setLocationType(cityDTO.getLocationType());
        city.setState(stateMapper.stateDtoToStatePermanent(cityDTO.getStateDto()));
        city.setState(stateMapper.stateDtoToStatePermanent(cityDTO.getStateDto()));

        return city;
    }

    @Override
    public CityDTO cityToDto(City city) {
        if(city==null)return null;
        CityDTO cityDTO = new CityDTO();

        cityDTO.setId(city.getId());
        cityDTO.setStateDto(stateMapper.statePermanentToStateDto(city.getState()));
        cityDTO.setMetaData(MetaDataMapper.metaDataToTemp(city.getMetaData()));
        cityDTO.setCityCode(city.getCityCode());
        cityDTO.setCityName(city.getCityName());
        cityDTO.setStdCode(city.getStdCode());
        cityDTO.setCityMICRCode(city.getCityMICRCode());
        cityDTO.setLocationType(city.getLocationType());

        return cityDTO;
    }
}
