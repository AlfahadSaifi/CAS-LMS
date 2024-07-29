package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CountryDTO;
import org.nucleus.entity.permanent.Country;

public interface CountryDTOMapper {
    Country dtoToCountry(CountryDTO countryDTO);
    CountryDTO countryToDto(Country country);
}
