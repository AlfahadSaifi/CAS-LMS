package org.nucleus.dao.address;

import org.hibernate.SessionFactory;
import org.nucleus.dto.CountryDTO;
import org.nucleus.entity.temporary.CountryTemp;
import org.nucleus.utility.dtomapper.address.CountryDTOMapperTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*Author Unzala*/
@Repository
public class CountryTempDAOImpl implements CountryTempDAO {

    private SessionFactory sessionFactory;


    private CountryDTOMapperTemp countryDTOMapperTemp;


    @Autowired
    public CountryTempDAOImpl(SessionFactory sessionFactory ,CountryDTOMapperTemp countryDTOMapperTemp){
        this.sessionFactory = sessionFactory;
        this.countryDTOMapperTemp =countryDTOMapperTemp;
    }

    //maker actions

    //save temporary country in to temporary table
    @Override
    public boolean saveCountry(CountryDTO countryDTO) {
        if(countryDTO!=null){

            try{
                CountryTemp countryTemp = countryDTOMapperTemp.dtoToCountryTemp(countryDTO);
                Integer id = (Integer)sessionFactory.getCurrentSession().save(countryTemp);
                if(id!=null){
                    return true;
                }
            }
            catch (Exception e){
                //do logging
                e.getMessage();
                return false;
            }


        }
        return false;
    }

    @Override
    public boolean updateCountry(CountryDTO countryDTO) {

        if(countryDTO!=null){
            CountryTemp countryTemp = countryDTOMapperTemp.dtoToCountryTemp(countryDTO);
            System.out.println(countryTemp);
            sessionFactory.getCurrentSession().merge(countryTemp);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCountry(Integer id) {
        CountryTemp countryTemp = sessionFactory.getCurrentSession().get(CountryTemp.class, id);
        System.out.println("Country temp from dao"+ countryTemp);
        try{
            sessionFactory.getCurrentSession().delete(countryTemp);
            return true;
        }
        catch (Exception e){
            //do logging
            e.getMessage();
            return false;
        }

    }

    @Override
    public CountryDTO getCountryById(Integer id) {
        if(id==null) return null;

        CountryTemp countryTemp = sessionFactory.getCurrentSession().get(CountryTemp.class, id);
        return countryDTOMapperTemp.countryTempToDto(countryTemp);


    }

    @Override
    public List<CountryDTO> getAllCountries() {
        //getting temp countries
        List<CountryTemp> countryTemps =(List<CountryTemp>) sessionFactory.getCurrentSession().createQuery("From CountryTemp").list();
        //adding to dto list
        List<CountryDTO> countryDTOs = new ArrayList<>();
        for(CountryTemp countryTemp: countryTemps){
            countryDTOs.add(countryDTOMapperTemp.countryTempToDto(countryTemp));
        }
        return countryDTOs;
    }


    //all the countries list that will be sent to the checker for approval
    @Override
    public List<CountryDTO> getCountriesWithSaveFlag() {

        List<CountryDTO> countryDTOS = new ArrayList<>();

        //getting countries from temporary table with status flag= 1,
        //indicates that these lists needs to be send to the checker data table

        List<CountryTemp> countryTemps= (List<CountryTemp>)sessionFactory.getCurrentSession().createQuery("FROM CountryTemp where saveFlag = 0 AND recordStatus in('N', 'M','D')").getResultList();

        //Iterating over temp countries
        //adding to dto list after converting

        for(CountryTemp countryTemp: countryTemps){
            countryDTOS.add(countryDTOMapperTemp.countryTempToDto(countryTemp));
        }
        return countryDTOS;
    }


    @Override
    public CountryDTO getCountryByCountryName(String countryName){
        CountryTemp countryTemp = (CountryTemp) sessionFactory.getCurrentSession().createQuery("From CountryTemp where countryName=:countryName").setParameter("countryName", countryName).uniqueResult();
        if(countryTemp!=null){
            return countryDTOMapperTemp.countryTempToDto(countryTemp);
        }
        return null;

    }
    //country that will be displayed to the maker if he have only pressed save button
    @Override
    public CountryDTO getCountryWithSaveFlag(){
        CountryTemp countryTemp = (CountryTemp) sessionFactory.getCurrentSession().createQuery("from CountryTemp where saveFlag = 1");
        if(countryTemp!=null){
            return countryDTOMapperTemp.countryTempToDto(countryTemp);
        }
        return null;
    }
}
