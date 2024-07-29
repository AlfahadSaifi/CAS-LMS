package org.nucleus.service.address;

import org.nucleus.dto.CountryDTO;

import java.util.List;

public interface CountryService {

    List<CountryDTO> getAllCountries();

    CountryDTO getCountryById(Integer Id);

    String approveCountry(Integer id);

    String rejectCountry(Integer id);

    List<CountryDTO> getCountryWithApprovedStatus();
    CountryDTO getCountryByCountryName(String countryName);
}
