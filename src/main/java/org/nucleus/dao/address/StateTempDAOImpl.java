package org.nucleus.dao.address;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.StateDTO;
import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;
import org.nucleus.utility.dtomapper.address.CountryDTOMapperTemp;
import org.nucleus.utility.dtomapper.address.StateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Repository
public class StateTempDAOImpl implements StateTempDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private CountryDTOMapperTemp countryDTOMapperTemp;
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    @Override
    @Transactional
    public boolean saveState(StateDTO stateDTO){

        StateTemp stateTemp = new StateTemp();
        System.out.println("stateDTO in TEMP DAO"+stateDTO);
        stateTemp = stateMapper.stateDtoToStateEntity(stateDTO);
        System.out.println("state in TEMP DAO"+stateTemp);

        //CountryTemp countryTemp = stateTemp.getCountry();
        //System.out.println(countryTemp);
//        stateTemp.getMetaDataTemp().setRecordStatus(RecordStatus.N);
//        stateTemp.getMetaDataTemp().setApprovalRequest(false);

        Integer id =(Integer) getCurrentSession().save(stateTemp);
        if(id!= null){

            //countryTemp.getStates().add(stateTemp);
            return true;
        }
        return false;
    }

/*    @Override
    public boolean saveStateAndRequestApproval(StateDTO stateDTO){


        StateTemp stateTemp = new StateTemp();
        stateTemp = stateMapper.stateDtoToStateEntity(stateDTO);
        Integer id =(Integer) getCurrentSession().save(stateTemp);
        if(id!= null){
            return true;
        }
        return false;

    }*/

    @Override
    public void editStateAndRequestApproval(StateDTO stateDTO){

        String hql = "SELECT st.id FROM StateTemp st WHERE st.stateCode = :stateCode";
        Integer id =(Integer) getCurrentSession().createQuery(hql)
                              .setParameter("stateCode", stateDTO.getStateCode()).uniqueResult();
        System.out.println(id);

        StateTemp stateTemp = new StateTemp();
        stateTemp =(StateTemp) getCurrentSession().get(StateTemp.class,id);



            String updateQuery = "UPDATE StateTemp s SET s.stateName = :value1, s.region = :value2 WHERE s.stateCode = :stateCode";
            Query query = getCurrentSession().createQuery(updateQuery);
            query.setParameter("value1", stateDTO.getStateName());
            query.setParameter("value2", stateDTO.getRegion());
            query.setParameter("stateCode", stateDTO.getStateCode());
            query.executeUpdate();
    }







    @Override
    public List<StateDTO> getAllStates(){

////        System.out.println(getCurrentSession().createQuery("FROM StateTemp" , StateTemp.class).getResultList());
//        return getCurrentSession().createQuery("FROM StateTemp" , StateTemp.class).getResultList();

        List<StateTemp> stateAllList = sessionFactory.getCurrentSession().createQuery("FROM StateTemp").getResultList();

        return stateMapper.convertStateTempListToDTOList(stateAllList);
    }

    @Override
    public boolean deleteState(Integer id){
            if(id!=0){
                StateTemp stateTemp = new StateTemp();
                stateTemp = getCurrentSession().get(StateTemp.class, id);
                System.out.println(stateTemp);
                getCurrentSession().remove(stateTemp);
                return true;
            }
            return false;
    }

    @Override
    public void updateState(StateDTO stateDTO){
        StateTemp stateTemp = new StateTemp();
        stateTemp = stateMapper.stateDtoToStateEntity(stateDTO);

        getCurrentSession().merge(stateTemp);

    }

    @Override
    public StateDTO getStateDtoById(Integer id)
    {
        if(id>0) return stateMapper.stateEntityToStateDto(getCurrentSession().get(StateTemp.class, id));
        return null;
    }

    @Override
    public boolean doesStateExist(String stateName) {
        Long count = (Long) getCurrentSession().createQuery("SELECT COUNT(*) FROM State WHERE stateName = :stateName")
        .setParameter("stateName", stateName).uniqueResult();

        return count != null && count > 0;
    }



}
