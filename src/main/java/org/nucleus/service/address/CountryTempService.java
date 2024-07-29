package org.nucleus.service.address;


import org.nucleus.dto.CountryDTO;

import java.util.List;

public interface CountryTempService {
    String saveCountry(CountryDTO countryDTO , String action );
    String updateCountryForm(CountryDTO countryDTO);
    String deleteCountry(Integer id);
    CountryDTO getCountryById(Integer id);

    CountryDTO getCountryByCountryName(String countryName);

    List<CountryDTO> getAllCountries();

    List<CountryDTO> getCountriesWithSaveFlag();


}