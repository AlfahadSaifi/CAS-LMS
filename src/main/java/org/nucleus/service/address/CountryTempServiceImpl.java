package org.nucleus.service.address;

import org.nucleus.dao.address.CountryDAO;
import org.nucleus.dao.address.CountryTempDAO;
import org.nucleus.dto.CountryDTO;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
                /****
                 * UNZALA
                 * *******/

@Service

public class CountryTempServiceImpl implements CountryTempService {
    private CountryTempDAO countryTempDAO;
    private CountryDAO countryDAO;


    @Autowired
    public CountryTempServiceImpl(CountryTempDAO countryTempDAO , CountryDAO countryDAO){
        this.countryTempDAO = countryTempDAO;
        this.countryDAO=countryDAO;
    }
    @Override
    @Transactional
    public String saveCountry(CountryDTO countryDTO ,String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();

        //getting country by country name
        String message;
        CountryDTO existingCountry = countryTempDAO.getCountryByCountryName(countryDTO.getCountryName());

        if("save".equalsIgnoreCase(action))
        {
            countryDTO.getMetaData().setRecordStatus(RecordStatus.N);
            countryDTO.getMetaData().setSaveFlag(true);
        }
        else if("saveAndRequest".equalsIgnoreCase(action)){
            countryDTO.getMetaData().setRecordStatus(RecordStatus.N);
            countryDTO.getMetaData().setSaveFlag(false);
        }

        //save if country is not already present in the database
        if(existingCountry==null){
            try{
                countryTempDAO.saveCountry(countryDTO);
                message="Country saved successfully";
            }
            catch (Exception e){
                message="Country cannot be saved";
            }
        }

        //update if country is present and request haven't sent to the checker
        else if(Boolean.TRUE.equals(existingCountry.getMetaData().getSaveFlag())){
            if(countryTempDAO.updateCountry(countryDTO)){
                message="Country updated successfully";
            }
            else{
                message="Country cannot be updated";
            }
        }
        else{
            message="Country already exists";
        }
        return message;
    }

    @Override
    @Transactional
    public String updateCountryForm(CountryDTO countryDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        //getting record status of countryDTO
        RecordStatus recordStatus = countryDTO.getMetaData().getRecordStatus();

        if(recordStatus.equals(RecordStatus.A)){

            //fetching existing country from permanent country database
            CountryDTO existingCountryDTOPerm = countryDAO.getCountryById(countryDTO.getId());

            //setting metadata to save permanent country from perm database into temp database
            countryDTO.setMetaData(existingCountryDTOPerm.getMetaData());
            countryDTO.getMetaData().setModifiedBy(username);
            countryDTO.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            countryDTO.getMetaData().setRecordStatus(RecordStatus.M);

            //saving country in to temp country database
            if(countryTempDAO.saveCountry(countryDTO)){
                return "Country requested to be updated";
            }
            else{
                return "Request failed";
            }

        }

        else{
            //fetching existing temp country from temp database
            CountryDTO existingCountryDTOTemp = countryTempDAO.getCountryById(countryDTO.getId());

            if(recordStatus.equals(RecordStatus.NR)){
                existingCountryDTOTemp.getMetaData().setRecordStatus(RecordStatus.N);
            } else if (recordStatus.equals(RecordStatus.MR)) {
                existingCountryDTOTemp.getMetaData().setRecordStatus(RecordStatus.M);
            }

            existingCountryDTOTemp.setCountryGroup(countryDTO.getCountryGroup());
            existingCountryDTOTemp.setCountryIsdCode(countryDTO.getCountryIsdCode());
            existingCountryDTOTemp.setCountryIsdCode(countryDTO.getCountryIsdCode());
            existingCountryDTOTemp.setRegion(countryDTO.getRegion());
            existingCountryDTOTemp.setNationality(countryDTO.getNationality());
            existingCountryDTOTemp.setStatus(countryDTO.getStatus());
            existingCountryDTOTemp.getMetaData().setRecordStatus(RecordStatus.M);
            existingCountryDTOTemp.getMetaData().setModifiedBy(username);
            existingCountryDTOTemp.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));

            if(countryTempDAO.updateCountry(existingCountryDTOTemp)){
                return "Country updated successfully";
            }
            else{
                return "Country cannot be updated";
            }
        }

    }

    @Override
    @Transactional
    public String deleteCountry(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        String message ="";
        CountryDTO tempCountry= countryTempDAO.getCountryById(id);
        CountryDTO permCountry = countryDAO.getCountryById(id);

        if(permCountry==null){
            countryTempDAO.deleteCountry(id);
            message="Country deleted with id " + id;
        }

        if(tempCountry==null ){
            permCountry.getMetaData().setRecordStatus(RecordStatus.D);
            permCountry.getMetaData().setModifiedBy(username);
            permCountry.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
            permCountry.getMetaData().setSaveFlag(false);
            if(countryTempDAO.getCountryByCountryName(permCountry.getCountryName())==null){
                countryTempDAO.saveCountry(permCountry);
                message="Request sent to approve the deletion";
            }
            else{
                message="Request has already been sent";
            }
        }
        System.out.println("Message from service"+message);
        return message;
    }

    @Override
    @Transactional
    public CountryDTO getCountryById(Integer id) {
        return countryTempDAO.getCountryById(id);
    }

    @Override
    @Transactional
    public List<CountryDTO> getAllCountries() {
        return countryTempDAO.getAllCountries();
    }

    @Override
    @Transactional
    public List<CountryDTO> getCountriesWithSaveFlag() {
        return countryTempDAO.getCountriesWithSaveFlag();
    }

    @Override
    @Transactional
    public CountryDTO getCountryByCountryName(String countryName){
        return countryTempDAO.getCountryByCountryName(countryName);
    }



}
