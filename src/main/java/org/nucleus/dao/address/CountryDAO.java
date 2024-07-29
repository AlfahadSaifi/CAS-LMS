package org.nucleus.dao.address;

import org.nucleus.dto.CountryDTO;

import java.util.List;

public interface CountryDAO {
    boolean saveCountry(CountryDTO countryDTO);
    boolean updateCountry(CountryDTO countryDTO);
    boolean deleteCountry(Integer id);
    List<CountryDTO> getAllCountries();
    CountryDTO getCountryById(Integer Id);
    List<CountryDTO> getCountryWithApprovedStatus();
    CountryDTO getCountryByCountryName(String countryName);

}
