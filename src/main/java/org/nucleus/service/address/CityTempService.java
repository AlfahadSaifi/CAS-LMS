package org.nucleus.service.address;


import org.nucleus.dto.CityDTO;

import java.util.List;

public interface CityTempService {
    String saveCity(CityDTO city, String action);
    String updateCityForm(CityDTO cityDTO);
    boolean updateCity(CityDTO cityDTO);
    String deleteCity(Integer id, String recordStatus);
    CityDTO getCityById(Integer id);
    List<CityDTO> getAllCities();
    List<CityDTO> getPendingCities();
    CityDTO getCityWithSaveFlag();
    CityDTO getCityByName(String cityName);
}
