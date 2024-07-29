package org.nucleus.service.address;

import org.nucleus.dao.address.CityDAO;
import org.nucleus.dao.address.CityTempDAO;
import org.nucleus.dto.CityDTO;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
                                /*******
                                 * UNZALA
                                 * ***/
@Service

public class CityTempServiceImpl implements CityTempService {
    @Autowired
    private CityTempDAO cityTempDao;

    @Autowired
    private CityDAO cityDAO;


    @Override
    @Transactional
    public String saveCity(CityDTO cityDTO , String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();

        System.out.println(cityDTO);
        System.out.println(action);

        //getting city by city name
        String message;
        CityDTO existingCity = cityTempDao.getCityByName(cityDTO.getCityName());

        if("save".equalsIgnoreCase(action))
        {
            cityDTO.getMetaData().setRecordStatus(RecordStatus.N);
            cityDTO.getMetaData().setSaveFlag(true);
        }
        else if("saveAndRequest".equalsIgnoreCase(action)){
            cityDTO.getMetaData().setRecordStatus(RecordStatus.N);
            cityDTO.getMetaData().setSaveFlag(false);
        }
        //save
        if(existingCity==null){
            try{
                cityTempDao.saveCity(cityDTO);
                message="City saved successfully";
            }
            catch (Exception e){
                message="City cannot be saved";

            }
        }

        //update if city is present and request haven't sent to the checker
        else if(Boolean.TRUE.equals(existingCity.getMetaData().getSaveFlag())){
            if(cityTempDao.updateCity(cityDTO)){
                message="City updated successfully";
            }
            else{
                message="City cannot be updated";
            }
        }
        else{
            message="City already exists";
        }
        return message;
    }

    @Override
    @Transactional
    public String updateCityForm(CityDTO cityDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();

        RecordStatus recordStatus = cityDTO.getMetaData().getRecordStatus();

        if(recordStatus.equals(RecordStatus.A)){

            //existing city
            CityDTO existingCityDTOPerm = cityDAO.getCityById(cityDTO.getId());
            cityDTO.setMetaData(existingCityDTOPerm.getMetaData());
            cityDTO.getMetaData().setModifiedBy(username);
            cityDTO.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            cityDTO.getMetaData().setRecordStatus(RecordStatus.M);
            if(cityTempDao.saveCity(cityDTO)){
                return "City requested to be updated";
            }
            else{
                return "Request failed";
            }

        }

        else{

            CityDTO existingCityDTOTemp = cityTempDao.getCityById(cityDTO.getId());

            if(recordStatus.equals(RecordStatus.NR)){
                existingCityDTOTemp.getMetaData().setRecordStatus(RecordStatus.N);
            } else if (recordStatus.equals(RecordStatus.MR)) {
                existingCityDTOTemp.getMetaData().setRecordStatus(RecordStatus.M);
            }

            existingCityDTOTemp.setCityName(cityDTO.getCityName());
            existingCityDTOTemp.setStateDto(cityDTO.getStateDto());
            existingCityDTOTemp.setCityCode(cityDTO.getCityCode());
            existingCityDTOTemp.setCityMICRCode(cityDTO.getCityMICRCode());
            existingCityDTOTemp.setLocationType(cityDTO.getLocationType());
            existingCityDTOTemp.setStdCode(cityDTO.getStdCode());
            existingCityDTOTemp.getMetaData().setRecordStatus(RecordStatus.M);
            existingCityDTOTemp.getMetaData().setModifiedBy(username);
            existingCityDTOTemp.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));

            if(cityTempDao.updateCity(existingCityDTOTemp)){
                return "City updated successfully";
            }
            else{
                return "City cannot be updated";
            }
        }

    }

    @Override
    @Transactional
    public String deleteCity(Integer id, String recordStatus) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        String message ="";
        System.out.println(recordStatus);
        if ("A".equals(recordStatus)) {
                CityDTO cityDTO = cityDAO.getCityById(id);
                System.out.println(cityDTO);
                cityDTO.getMetaData().setRecordStatus(RecordStatus.D);
                cityDTO.getMetaData().setModifiedBy(username);
                cityDTO.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                if (cityTempDao.saveCity(cityDTO)) {

                    return "City requested for deletion!";
                } else {
                    return "Request failed";
                }

        } else {
            if (cityTempDao.deleteCity(id)) {
                return "City deleted successfully!";
            } else {
                return "Request Failed";
            }
        }
    }
    @Override
    @Transactional
    public boolean updateCity(CityDTO cityDTO){
        return cityTempDao.updateCity(cityDTO);
    }

    @Override
    @Transactional
    public CityDTO getCityById(Integer id) {
        return cityTempDao.getCityById(id);
    }

    @Override
    @Transactional
    public List<CityDTO> getAllCities() {
        return cityTempDao.getAllCities();
    }

    @Override
    @Transactional
    public List<CityDTO> getPendingCities() {
        return cityTempDao.getPendingCities();
    }

    @Override
    @Transactional
    public CityDTO getCityWithSaveFlag() {
        return cityTempDao.getCityWithSaveFlag();
    }

    @Override
    @Transactional
    public CityDTO getCityByName(String cityName){
        return cityTempDao.getCityByName(cityName);
    }
}
