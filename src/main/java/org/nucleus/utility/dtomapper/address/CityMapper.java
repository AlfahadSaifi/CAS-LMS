package org.nucleus.utility.dtomapper.address;


import org.nucleus.dto.CityDTO;
import org.nucleus.entity.temporary.CityTemp;

import java.util.List;

public interface CityMapper {
    CityTemp cityDtoToCityEntity(CityDTO cityDTO);
    CityDTO cityEntityToCityDto(CityTemp city);
    List<CityDTO> convertCityListToDTOList(List<CityTemp> cityTemp);

}
