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
                         ******/
@Service

public class CountryServiceImpl implements CountryService {
    private CountryDAO countryDAO;

    private CountryTempDAO countryTempDAO;

    @Autowired
    public CountryServiceImpl(CountryDAO countryDAO , CountryTempDAO countryTempDAO){
        this.countryDAO=countryDAO;
        this.countryTempDAO=countryTempDAO;
    }

    @Override
    @Transactional
    public String approveCountry(Integer id) throws NullPointerException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();

        CountryDTO countryDTO = countryTempDAO.getCountryById(id);

        RecordStatus recordStatus = countryDTO.getMetaData().getRecordStatus();
        if(countryDTO!=null){

            if( countryDTO.getMetaData().getRecordStatus()==RecordStatus.N || countryDTO.getMetaData().getRecordStatus()==RecordStatus.M ){
                countryDTO.getMetaData().setRecordStatus(RecordStatus.A);
                countryDTO.getMetaData().setAuthorizedBy(username);
                countryDTO.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
                if(countryDAO.saveCountry(countryDTO)){
                    countryTempDAO.deleteCountry(id);
                    return "Approved Successfully";
                }
            }
            if(recordStatus==RecordStatus.D){
                CountryDTO countryByName = countryDAO.getCountryByCountryName(countryDTO.getCountryName());

                if(countryDAO.deleteCountry(countryByName.getId())){
                    if(countryTempDAO.deleteCountry(id)){
                        return "Approved Successfully";
                    }
                }
            }

        }
        return "Reject action unsuccessful";


    }

    @Override
    @Transactional
    public String rejectCountry(Integer id){
        CountryDTO countryDTO = countryTempDAO.getCountryById(id);
        RecordStatus recordStatus = countryDTO.getMetaData().getRecordStatus();

        if(countryDTO!=null && recordStatus==RecordStatus.N ){
            countryDTO.getMetaData().setRecordStatus(RecordStatus.NR);
        }
        else if(countryDTO!=null && recordStatus==RecordStatus.M){
            countryDTO.getMetaData().setRecordStatus(RecordStatus.MR);
        }
        else if(countryDTO!=null && recordStatus==RecordStatus.D){
            countryDTO.getMetaData().setRecordStatus(RecordStatus.DR);
        }
        if(countryTempDAO.updateCountry(countryDTO)){
            return "Successfully rejected request";
        }
        else{
            return "Request Rejection Unsuccessful";
        }
    }

    @Override
    @Transactional
    public List<CountryDTO> getCountryWithApprovedStatus(){
        return countryDAO.getCountryWithApprovedStatus();
    }

    @Override
    @Transactional
    public CountryDTO getCountryByCountryName(String countryName){
        return countryDAO.getCountryByCountryName(countryName);
    }


    @Override
    @Transactional
    public List<CountryDTO> getAllCountries() {
        return countryDAO.getAllCountries();
    }

    @Override
    @Transactional
    public CountryDTO getCountryById(Integer id) {
        return countryDAO.getCountryById(id);
    }


}
