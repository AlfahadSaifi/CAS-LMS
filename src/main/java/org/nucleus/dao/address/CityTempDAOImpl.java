package org.nucleus.dao.address;


import org.hibernate.SessionFactory;
import org.nucleus.dto.CityDTO;
import org.nucleus.entity.temporary.CityTemp;
import org.nucleus.entity.temporary.StateTemp;
import org.nucleus.utility.dtomapper.address.CityDTOMapper;
import org.nucleus.utility.dtomapper.address.CityDTOMapperTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CityTempDAOImpl implements CityTempDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CityDTOMapper cityDTOMapper;

    @Autowired
    private CityDTOMapperTemp cityDTOMapperTemp;


    @Override
    public boolean saveCity(CityDTO cityDTO) {

        CityTemp cityTemp = cityDTOMapperTemp.dtoToCityTemp(cityDTO);
        Integer id =(Integer) sessionFactory.getCurrentSession().save(cityTemp);
        //StateTemp stateTemp = cityTemp.getState();

        if(id!=null){
            //stateTemp.getCities().add(cityTemp);
            return true;
        }
        return false;

    }

    @Override
    public boolean updateCity(CityDTO cityDTO) {
        if(cityDTO ==null) return  false;
        try{
            if (cityDTO!=null) {
//                CityDTO cityById = getCityById(cityDTO.getId());
                System.out.println("city at 47 dao:"+ cityDTO);
                CityTemp cityTemp = cityDTOMapperTemp.dtoToCityTemp(cityDTO);
                System.out.println("city at 49 dao:"+ cityTemp);
                System.out.println(cityTemp);
                sessionFactory.getCurrentSession().update(cityTemp);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCity(Integer id) {

        try{
            if(id!=null){
                CityTemp cityTemp = new CityTemp();
                cityTemp = sessionFactory.getCurrentSession()
                        .get(CityTemp.class , id);
                sessionFactory.getCurrentSession().remove(cityTemp);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CityDTO getCityById(Integer id) {
        if(id==null){
            return null;
        }
        System.out.println("line 81 dao: "+sessionFactory.getCurrentSession().get(CityTemp.class, id));

        CityTemp cityTemp = sessionFactory.getCurrentSession().get(CityTemp.class , id);
        return cityDTOMapperTemp.cityTempToCityDto(cityTemp);
    }

    @Override
    public List<CityDTO> getAllCities() {
        List<CityDTO> cityDTOS = new ArrayList<>();
        List<CityTemp> cityTemps = sessionFactory.getCurrentSession().createQuery("From CityTemp").getResultList();
        for(CityTemp cityTemp : cityTemps){
            cityDTOS.add(cityDTOMapperTemp.cityTempToCityDto(cityTemp));
        }
        return cityDTOS;
    }

    @Override
    public List<CityDTO> getPendingCities() {
        List<CityTemp> cityTemps = sessionFactory.getCurrentSession().createQuery("From CityTemp where saveFlag=0 and recordStatus in ('N','M','D')").getResultList();
        List<CityDTO> cityDTOS = new ArrayList<>();
        for(CityTemp cityTemp: cityTemps){
            cityDTOS.add(cityDTOMapperTemp.cityTempToCityDto(cityTemp));
        }
        return cityDTOS;
    }

    @Override
    public CityDTO getCityByName(String cityName){
        if(cityName==null) return null;
        CityTemp cityTemp =(CityTemp) sessionFactory.getCurrentSession().createQuery("From CityTemp where cityName=:cityName").setParameter("cityName", cityName).uniqueResult();
        return cityDTOMapperTemp.cityTempToCityDto(cityTemp);

    }

    @Override
    public CityDTO getCityWithSaveFlag() {
        return null;
    }
}
