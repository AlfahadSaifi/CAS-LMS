package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CountryDTO;
import org.nucleus.entity.temporary.CountryTemp;
import org.springframework.stereotype.Component;

@Component
public class CountryDTOMapperTempImpl implements CountryDTOMapperTemp {

    public CountryTemp dtoToCountryTemp(CountryDTO countryDTO)

    {
        CountryTemp country = new CountryTemp();
        if(countryDTO==null){
            return null;
        }

        country.setId(countryDTO.getId());
        country.setCountryGroup(countryDTO.getCountryGroup());
        country.setCountryName(countryDTO.getCountryName());
        country.setCountryIsdCode(countryDTO.getCountryIsdCode());
        country.setCountryIsoCode(countryDTO.getCountryIsoCode());
        country.setNationality(countryDTO.getNationality());
        country.setRegion(countryDTO.getRegion());
        country.setStatus(countryDTO.getStatus());
        country.setMetaData(countryDTO.getMetaData());

        return country;

    }

    public CountryDTO countryTempToDto(CountryTemp countryTemp)
    {
        CountryDTO countryDTO = new CountryDTO();
        if(countryTemp==null){
            return null;
        }
        countryDTO.setId(countryTemp.getId());
        countryDTO.setCountryName(countryTemp.getCountryName());
        countryDTO.setCountryGroup(countryTemp.getCountryGroup());
        countryDTO.setNationality(countryTemp.getNationality());
        countryDTO.setCountryIsdCode(countryTemp.getCountryIsdCode());
        countryDTO.setCountryIsoCode(countryTemp.getCountryIsoCode());
        countryDTO.setRegion(countryTemp.getRegion());
        countryDTO.setStatus(countryTemp.getStatus());
        countryDTO.setMetaData(countryTemp.getMetaData());

        return countryDTO;

    }


}
