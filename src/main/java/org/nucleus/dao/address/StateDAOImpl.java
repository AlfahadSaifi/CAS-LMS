package org.nucleus.dao.address;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.CountryDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.entity.permanent.Country;
import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;
import org.nucleus.utility.dtomapper.address.CountryDTOMapper;
import org.nucleus.utility.dtomapper.address.StateMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StateDAOImpl implements StateDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CountryTempDAO countryTempDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private StateTempDAO stateTempDAO;

    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private CountryDTOMapper countryDTOMapper;

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }




    //move temp state to permanent table
    @Transactional
    @Override
    public boolean saveState(StateDTO stateDTO){

//        if (stateDTO==null) return false;
//
//        State state = new State();
//        state = stateMapper.stateDtoToStatePermanent(stateDTO);
//
//        state.getMetaData().setRecordStatus(RecordStatus.A);
//
//        System.out.println("Inside StateDao: "+state);
//        Country country = state.getCountry();
//        Integer id =(Integer) getCurrentSession().save(state);
//
//        if(country!=null && id!=null) {
//            country.getStates().add(state);
//            return true;
//        }
//        return false;

        if (stateDTO == null) return false;

        State state = stateMapper.stateDtoToStatePermanent(stateDTO);
        if (state == null) return false;

        state.getMetaData().setRecordStatus(RecordStatus.A);

        System.out.println("Inside StateDao: " + state);
        Country country = state.getCountry();
        if (country == null) return false;

        Integer id = (Integer) getCurrentSession().save(state);
        if (id == null) return false;

        country.getStates().add(state);
        return true;
    }


    //approvalStatus 0 - only saved , not sent for approval
    //approvalStatus 1 - saved and requested for approval
    @Override
    public List<StateDTO> getAllPendingStates(){
        List<StateTemp> stateTempList = sessionFactory.getCurrentSession().createQuery("FROM StateTemp where saveFlag= 0 and recordStatus in ('N','M','D')").list();
        System.out.println(stateTempList);
        return stateMapper.convertStateTempListToDTOList(stateTempList);
    }

    @Override
    public StateDTO getStateDtoById(Integer id)
    {
        return  stateMapper.statePermanentToStateDto(getCurrentSession().get(State.class, id));
    }
    @Override
    public List<StateDTO> getAllStates(){
        List<State> stateAllList = sessionFactory.getCurrentSession().createQuery("FROM State ").list();

        return stateMapper.convertStateListToDTOList(stateAllList);
    }

    @Override
    public boolean deleteState(Integer id){
        State state = new State();
        if(id!=0){
            state = getCurrentSession().get(State.class, id);
        }
        try{
            getCurrentSession().delete(state);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStateByName(String stateName){
        try{
            if(!stateName.isEmpty())
            {
                StateDTO statDTO = getStateByStateName(stateName);
                State state = stateMapper.stateDtoToStatePermanent(statDTO);
                getCurrentSession().delete(state);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public void editApprovedStateAndRequestApproval(StateDTO stateDTO){

        String hql = "SELECT st.id FROM State st WHERE st.stateCode = :stateCode";
        Integer id =(Integer) getCurrentSession().createQuery(hql)
                .setParameter("stateCode", stateDTO.getStateCode()).uniqueResult();
        System.out.println(id);

        State state = new State();
        state = getCurrentSession().get(State.class,id);

        if(getCurrentSession().get(State.class,id) !=null )
        {
            String updateQuery = "UPDATE StateTemp s SET s.stateName = :value1, s.region = :value2 WHERE s.stateCode = :stateCode";
            Query query = getCurrentSession().createQuery(updateQuery);
            query.setParameter("value1", stateDTO.getStateName());
            query.setParameter("value2", stateDTO.getRegion());
            query.setParameter("stateCode", stateDTO.getStateCode());
            query.executeUpdate();
        }
    }
    @Override
    public List<StateDTO> getAllStatesWithApprovedStatus(){
        List<State> stateAllList = sessionFactory.getCurrentSession().createQuery("FROM State where recordStatus='A' ").list();

        return stateMapper.convertStateListToDTOList(stateAllList);
    }


    //To populate states in the dropdown for a particular country


    @Override
    public List<StateDTO> getStatesByCountryName(String countryName){

        if(countryName==null || countryName.isEmpty()){
            return null;
        }
        CountryDTO countryDTO = countryDAO.getCountryByCountryName(countryName);
        if(countryDTO==null) {
            return null;
        }

        Integer id = countryDTO.getId();
        List<State> states = (List<State>)getCurrentSession().createQuery("from State where country.id=:id")
                .setParameter("id",id).getResultList();

        List<StateDTO> stateDTOS = new ArrayList<>();
        for(State state: states ){
            stateDTOS.add(stateMapper.statePermanentToStateDto(state));
        }
        return stateDTOS;
    }
    @Override
    public StateDTO getStateByStateName(String stateName){
        if(stateName==null || stateName.isEmpty()){
            return null;
        }
        State state = getCurrentSession().createQuery("From State where stateName=:stateName", State.class)
                .setParameter("stateName" , stateName).uniqueResult();
        return stateMapper.statePermanentToStateDto(state);
    }

    @Override
    public StateDTO convertToPermanentEntity(StateDTO stateDTO){

        State state = new State();
        state.setId(stateDTO.getId());
        state.setStateCode(stateDTO.getStateCode());
        state.setStateName(stateDTO.getStateName());
        state.setRegion(stateDTO.getRegion());
        state.setIsUnionTerritory(stateDTO.getUnionTerritory());

        StateDTO stateDTO2 = new StateDTO();
        stateDTO2 = stateMapper.statePermanentToStateDto(state);

        return stateDTO2;
    }

    @Override
    public void updateState(StateDTO stateDTO){

        State state = new State();
        state = stateMapper.stateDtoToStatePermanent(stateDTO);
        state.getMetaData().setRecordStatus(RecordStatus.A);

        getCurrentSession().merge(state);

    }

}
