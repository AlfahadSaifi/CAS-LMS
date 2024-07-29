package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CityDTO;
import org.nucleus.entity.temporary.CityTemp;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapperImpl implements CityMapper{
    @Override
    public CityTemp cityDtoToCityEntity(CityDTO cityDTO) {
        CityTemp city = new CityTemp();
        //city.setCountry(cityDTO.getCountry());
        city.setCityCode(cityDTO.getCityCode());
        city.setCityMICRCode(cityDTO.getCityMICRCode());
        //city.setState(cityDTO.getState());
        city.setCityName(cityDTO.getCityName());
        city.setStdCode(cityDTO.getStdCode());

        return city;
    }

    @Override
    public CityDTO cityEntityToCityDto(CityTemp city) {
        return null;
    }



    @Override
    public List<CityDTO> convertCityListToDTOList(List<CityTemp> cityTemp) {
        List<CityDTO> cityDTOList = new ArrayList<>();
        for (CityTemp city : cityTemp) {
            CityDTO cityDTO = new CityDTO();

            cityDTO.setCityCode(city.getCityCode());
            cityDTO.setCityName(cityDTO.getCityName());
            cityDTO.setCityMICRCode(cityDTO.getCityMICRCode());
            cityDTO.setStateDto(cityDTO.getStateDto());
            //cityDTO.setCountry(cityDTO.getCountry());
            cityDTO.setStdCode(cityDTO.getCityCode());

            cityDTOList.add(cityDTO);
        }
        return cityDTOList;
    }
}
