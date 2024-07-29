package org.nucleus.dao.address;

import org.hibernate.SessionFactory;
import org.nucleus.dto.CountryDTO;
import org.nucleus.entity.permanent.Country;
import org.nucleus.utility.dtomapper.address.CountryDTOMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {
    private SessionFactory sessionFactory;
    private CountryDTOMapper countryDTOMapper;

    public CountryDAOImpl(SessionFactory sessionFactory , CountryDTOMapper countryDTOMapper){
        this.countryDTOMapper=countryDTOMapper;
        this.sessionFactory = sessionFactory;
    }
    @Override
    public boolean saveCountry(CountryDTO countryDTO) {
        Country country =  countryDTOMapper.dtoToCountry(countryDTO);
        Integer id =(Integer)sessionFactory.getCurrentSession().save(country);
        if(id!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCountry(CountryDTO countryDTO) {
        Country country = countryDTOMapper.dtoToCountry(countryDTO);
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(country);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;

        }

    }

    @Override
    public boolean deleteCountry(Integer id) {
        Country country = new Country();
        if(id!=null){
            country = sessionFactory.getCurrentSession().get(Country.class, id);
        }
        try{
            sessionFactory.getCurrentSession().remove( country);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;

        }

    }


    @Override
    public List<CountryDTO> getAllCountries() {
        List<CountryDTO> countryDTOS = new ArrayList<>();
        List<Country> countries = sessionFactory.getCurrentSession().createQuery("From Country").getResultList();
        for(Country country: countries){
            countryDTOS.add(countryDTOMapper.countryToDto(country));
        }
        return countryDTOS;
    }

    @Override
    public CountryDTO getCountryById(Integer id) {
        if(id==null) return null;
        Country country =sessionFactory.getCurrentSession().get(Country.class, id);
        return countryDTOMapper.countryToDto(country);


    }

    //List to be populated in address form.
    @Override
    public List<CountryDTO> getCountryWithApprovedStatus(){
        List<Country> countries = sessionFactory.getCurrentSession().createQuery("From Country where recordStatus='A' ").getResultList();
        List<CountryDTO> countryDTOS = new ArrayList<>();
        for(Country country : countries){
            countryDTOS.add(countryDTOMapper.countryToDto(country));
        }
        return countryDTOS;
    }

    @Override
    public CountryDTO getCountryByCountryName(String countryName){
        Country country = (Country)sessionFactory.getCurrentSession()
                .createQuery("From Country where countryName=:countryName")
                .setParameter("countryName", countryName).uniqueResult();

        return countryDTOMapper.countryToDto(country);
    }
}

