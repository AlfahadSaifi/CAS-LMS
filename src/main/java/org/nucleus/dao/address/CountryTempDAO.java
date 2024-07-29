package org.nucleus.dao.address;


import org.nucleus.dto.CountryDTO;

import java.util.List;

public interface CountryTempDAO {
    boolean saveCountry(CountryDTO countryDTO);
    boolean updateCountry(CountryDTO countryDTO);
    boolean deleteCountry(Integer id);
    CountryDTO getCountryById(Integer id);
    List<CountryDTO> getAllCountries();
    List<CountryDTO> getCountriesWithSaveFlag();
    CountryDTO getCountryByCountryName(String country);

    CountryDTO getCountryWithSaveFlag();





}

