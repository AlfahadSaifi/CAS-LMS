package org.nucleus.service.address;

import org.nucleus.dao.address.CityDAO;
import org.nucleus.dao.address.CityTempDAO;
import org.nucleus.dto.CityDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service

public class CityServiceImpl implements CityService {
    @Autowired
    private CityDAO cityDAO;

    @Autowired
    private CityTempDAO cityTempDAO;

    @Override
    public boolean saveCity(CityDTO cityDTO) {
        return cityDAO.saveCity(cityDTO);

    }
    @Transactional
    public boolean approveCity(CityDTO cityDTO){


//        stateDTO.getMetaData().setRecordStatus(RecordStatus.A);
        System.out.println("StateDTo inside approveStatus"+cityDTO);
        if(RecordStatus.D.equals(cityDTO.getMetaData().getRecordStatus()))
        {
            cityDAO.deleteCity(cityDTO.getId());
            cityTempDAO.deleteCity(cityDTO.getId());
            return true;
        }
        if(cityDAO.saveCity(cityDTO)){
            System.out.println( cityDTO.getId());
            return cityTempDAO.deleteCity(cityDTO.getId());

        }
        return false;
    }




    @Override
    @Transactional
    public boolean updateCity(CityDTO cityDTO) {
       return cityDAO.updateCity(cityDTO);
    }

    @Override
    @Transactional
    public boolean deleteCity(Integer id) {
        return cityDAO.deleteCity(id);
    }

    @Override
    @Transactional
    public CityDTO getCityById(Integer id) {
        return cityDAO.getCityById(id);
    }

    @Override
    @Transactional
    public List<CityDTO> getAllCities() {
        return cityDAO.getAllCities();
    }

    @Override
    public List<CityDTO> getCityWithApprovedStatus() {
        return cityDAO.getCityWithApprovedStatus();
    }

    @Override
    @Transactional
    public List<CityDTO> getCityByStateName(String stateName){
        return cityDAO.getCityByStateName(stateName);
    }
    @Override
    @Transactional
    public CityDTO getCityByCityName(String cityName){
        return  cityDAO.getCityByCityName(cityName);
    }
}
