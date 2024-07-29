package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.entity.temporary.EligibilityPolicyParameterDefinitionTemp;
import org.nucleus.utility.dtomapper.EligibilityPolicyParameterDefinitionDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository

public class EligibilityPolicyParameterDefinitionTempDAOImpl implements EligibilityPolicyParameterDefinitionTempDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    EligibilityPolicyParameterDefinitionDTOMapper eligibilityParameterDTOMapper;

    @Override
    public boolean insertTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        if(eligibilityParameterDto == null){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error("Invalid Eligibility Parameter Provided");
            return false;
        }
        EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp = eligibilityParameterDTOMapper.dTOToEligibilityParameterTemp(eligibilityParameterDto);
        Session session=sessionFactory.getCurrentSession();
        try {
            if(session.save(eligibilityParameterTemp)!=null)
                return true;
            else{
                LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error("Not inserted");
                return false;
            }
        } catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return false;
        }
    }


    @Override
    public boolean updateTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp = eligibilityParameterDTOMapper.dTOToEligibilityParameterTemp(eligibilityParameterDto);
        Session session=sessionFactory.getCurrentSession();
        try{
            session.update(eligibilityParameterTemp);
            return true;
        } catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return false;
        }
    }


    @Override
    public boolean deleteTempEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDto) {
        EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp = eligibilityParameterDTOMapper.dTOToEligibilityParameterTemp(eligibilityParameterDto);
        Session session = sessionFactory.getCurrentSession();
        try{
            session.delete(eligibilityParameterTemp);
            return true;
        } catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<EligibilityPolicyParameterDefinitionDTO> getAllReqEligibilityParameter() {
        List<EligibilityPolicyParameterDefinitionTemp> eligibilityParameterTempList ;
        List<EligibilityPolicyParameterDefinitionDTO> eligibilityParameterDTOList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        try{
            eligibilityParameterTempList = session.createQuery("from EligibilityPolicyParameterDefinitionTemp where saveFlag = 0 and recordstatus = 'N' or recordstatus = 'M' or recordstatus = 'D' " , EligibilityPolicyParameterDefinitionTemp.class)
                    .list();
        } catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return new ArrayList<>();
        }
        for (EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp : eligibilityParameterTempList) {
            EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = eligibilityParameterDTOMapper.tempEligibilityParameterToDTO(eligibilityParameterTemp);
            eligibilityParameterDTOList.add(eligibilityParameterDTO);
        }
        return eligibilityParameterDTOList;
    }

    @Override
    public List<EligibilityPolicyParameterDefinitionDTO> getAllTempEligibilityParameter() {
        List<EligibilityPolicyParameterDefinitionTemp> eligibilityParameterTempList ;
        List<EligibilityPolicyParameterDefinitionDTO> eligibilityParameterDTOList = new ArrayList<>();
        Session session= sessionFactory.getCurrentSession();
        try{
            eligibilityParameterTempList = session.createQuery("from EligibilityPolicyParameterDefinitionTemp where saveFlag = 0" , EligibilityPolicyParameterDefinitionTemp.class)
                    .list();
        } catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return new ArrayList<>();
        }
        for (EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp : eligibilityParameterTempList) {
            EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = eligibilityParameterDTOMapper.tempEligibilityParameterToDTO(eligibilityParameterTemp);
            eligibilityParameterDTOList.add(eligibilityParameterDTO);
        }
        return eligibilityParameterDTOList;
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO searchEligibilityParameterPerByCode(String code) {
        Session session= sessionFactory.getCurrentSession();
        try{
            Query query = session.createQuery(" from EligibilityPolicyParameterDefinitionTemp where eligibilityParameterCode = ?0");
            query.setParameter(0, code);
            EligibilityPolicyParameterDefinitionTemp eligibilityPolicyParameterDefinitionTemp = (EligibilityPolicyParameterDefinitionTemp)query.uniqueResult();
            if(eligibilityPolicyParameterDefinitionTemp==null)
                return null;
            return eligibilityParameterDTOMapper.tempEligibilityParameterToDTO(eligibilityPolicyParameterDefinitionTemp);


        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return null;
        }
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO getSavedParameter() {
        Session session= sessionFactory.getCurrentSession();
        try{
            Query query = session.createQuery(" from EligibilityPolicyParameterDefinitionTemp where  saveFlag= 1");
            return eligibilityParameterDTOMapper.tempEligibilityParameterToDTO((EligibilityPolicyParameterDefinitionTemp)query.uniqueResult());


        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return null;
        }
    }

    @Override
    public EligibilityPolicyParameterDefinitionDTO getTempEligibilityParameterById(Long eligibilityParameterId) {
        Session session= sessionFactory.getCurrentSession();
        EligibilityPolicyParameterDefinitionTemp eligibilityParameterTemp;
        try {
            eligibilityParameterTemp = session.get(EligibilityPolicyParameterDefinitionTemp.class, eligibilityParameterId);
        }catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return null;
        }
        return eligibilityParameterDTOMapper.tempEligibilityParameterToDTO(eligibilityParameterTemp);

    }

    @Override
    public boolean deleteEligibilityParameterById(Long id) {
        Session session= sessionFactory.getCurrentSession();

        try{
            Query query = session.createQuery("delete from EligibilityPolicyParameterDefinitionTemp where eligibilityParameterId = ?0");
            query.setParameter(0, id);
            int rowsAffected = query.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return false;
        }
    }

    public boolean deleteTempEligibilityParameterById(Long id) {
        Session session= sessionFactory.getCurrentSession();

        try{
            Query query = session.createQuery("delete from EligibilityPolicyParameterDefinitionTemp where eligibilityParameterId = ?0");
            query.setParameter(0, id);
            int rowsAffected = query.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionTempDAOImpl.class).error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateEligibilityParameterById(Long id) {
        return false;
    }

    @Override
    public boolean deleteTempEligibilityParameterByCode(String code) {
        if(code == null){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error("Invalid Eligibility Parameter Code Provided");
            return false;
        }
        Session session= sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("delete from EligibilityPolicyParameterDefinitionTemp where eligibilityParameterCode = ?0");
            query.setParameter(0, code);
            int rowsAffected = query.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error(e.getMessage(), e);
            return false;
        }
    }

}
