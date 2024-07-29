package org.nucleus.dao.address;

import org.hibernate.SessionFactory;
import org.nucleus.dto.CityDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.entity.permanent.City;
import org.nucleus.entity.permanent.State;
import org.nucleus.service.address.StateService;
import org.nucleus.utility.dtomapper.MetaDataMapper;
import org.nucleus.utility.dtomapper.address.CityDTOMapper;
import org.nucleus.utility.dtomapper.address.StateMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CityDAOImpl implements CityDAO{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private CityDTOMapper cityDTOMapper;
    @Autowired
    private StateService stateService;
    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private StateDAO stateDAO;
    @Override
    public boolean saveCity(CityDTO cityDTO) {

        City city = cityDTOMapper.dtoToCity(cityDTO);
        String stateName = cityDTO.getStateDto().getStateName();
        StateDTO stateDTO = stateService.getStateByStateName(stateName);
        State state = stateMapper.stateDtoToStatePermanent(stateDTO);
        city.setState(state);
        city.getMetaData().setRecordStatus(RecordStatus.A);
        Integer id =(Integer) sessionFactory.getCurrentSession().save(city);

        System.out.println("State: "+state);
        System.out.println("cityDto: "+city);
        if(id!=null && state!=null){
            state.getCities().add(city);
            return true;
        }
        return false;
    }


    @Override
    public boolean updateCity(CityDTO cityDTO) {
        try{
            sessionFactory.getCurrentSession().update(cityDTOMapper.dtoToCity(cityDTO));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCity(Integer id) {
        City city = new City();
        if(id!=null){
            city = sessionFactory.getCurrentSession()
                    .get(City.class, id);
        }
        try{
            sessionFactory.getCurrentSession().remove(city);
            return  true;
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
        City city =  sessionFactory.getCurrentSession().get(City.class ,id);
        return cityDTOMapper.cityToDto(city);

    }
    @Override
    public List<CityDTO> getAllCities() {
        List<City> cities = sessionFactory.getCurrentSession().createQuery("From City").getResultList();
        List<CityDTO> cityDTOS = new ArrayList<>();
        for(City city : cities){
            cityDTOS.add(cityDTOMapper.cityToDto(city));
        }
        return cityDTOS;
    }

    @Override
    public List<CityDTO> getCityWithApprovedStatus(){
        List<City> cities = sessionFactory.getCurrentSession().createQuery("From City where recordStatus='A'").getResultList();
        List<CityDTO> cityDTOS= new ArrayList<>();

        for(City city : cities){
            cityDTOS.add(cityDTOMapper.cityToDto(city));
        }
        return cityDTOS;

    }
    @Override

    public CityDTO getCityByCityName(String cityName){
        City city =(City) sessionFactory.getCurrentSession().createQuery("From City where cityName=:cityName")
                .setParameter("cityName", cityName)
                .uniqueResult();

        return cityDTOMapper.cityToDto(city);
    }


    //get all cities of a particular state
    @Override
    public List<CityDTO> getCityByStateName(String stateName){
        List<CityDTO> cityDTOS = new ArrayList<>();
        if(stateName==null || stateName.isEmpty()){
            return null;
        }
        StateDTO stateDTO = stateDAO.getStateByStateName(stateName);

        if(stateDTO!=null){
            Integer id = stateDTO.getId();
            List<City> cities = sessionFactory.getCurrentSession()
                    .createQuery("From City where state.id=:id")
                    .setParameter("id", id).getResultList();
            for(City city : cities){
                cityDTOS.add(cityDTOMapper.cityToDto(city));
            }
        }
        return cityDTOS;

    }


}
