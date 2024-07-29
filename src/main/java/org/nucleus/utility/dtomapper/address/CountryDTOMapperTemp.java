package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CountryDTO;
import org.nucleus.entity.temporary.CountryTemp;

public interface CountryDTOMapperTemp {

    CountryTemp dtoToCountryTemp(CountryDTO countryDTO);
    CountryDTO countryTempToDto(CountryTemp country);

}