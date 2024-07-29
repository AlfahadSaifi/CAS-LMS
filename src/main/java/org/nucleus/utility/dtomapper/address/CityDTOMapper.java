package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CityDTO;
import org.nucleus.entity.permanent.City;
import org.springframework.stereotype.Component;

@Component
public interface CityDTOMapper {
    City dtoToCity(CityDTO cityDTO);
    CityDTO cityToDto(City city);
}
