package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CityDTO;
import org.nucleus.entity.temporary.CityTemp;

public interface CityDTOMapperTemp {
    CityTemp dtoToCityTemp(CityDTO cityDTO);
    CityDTO cityTempToCityDto(CityTemp cityTemp);
}
