package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CountryDTO;
import org.nucleus.entity.permanent.Country;
import org.nucleus.utility.dtomapper.MetaDataMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryDTOMapperImpl implements CountryDTOMapper {

    public Country dtoToCountry(CountryDTO countryDTO)

    {
        Country country = new Country();
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
        if(countryDTO!=null){
            country.setMetaData(MetaDataMapper.tempToMetaData(countryDTO.getMetaData()));
        }


        return country;

    }

    public CountryDTO countryToDto(Country country)
    {
        CountryDTO countryDTO = new CountryDTO();
        if(country==null){
            return null;
        }

        countryDTO.setId(country.getId());
        countryDTO.setCountryName(country.getCountryName());
        countryDTO.setCountryGroup(country.getCountryGroup());
        countryDTO.setNationality(country.getNationality());
        countryDTO.setCountryIsdCode(country.getCountryIsdCode());
        countryDTO.setCountryIsoCode(country.getCountryIsoCode());
        countryDTO.setRegion(country.getRegion());
        countryDTO.setStatus(country.getStatus());
        countryDTO.setMetaData(MetaDataMapper.metaDataToTemp(country.getMetaData()));

        return countryDTO;

    }
}