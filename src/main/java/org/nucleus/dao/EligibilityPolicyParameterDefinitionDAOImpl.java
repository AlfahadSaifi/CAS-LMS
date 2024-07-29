package org.nucleus.dao;


import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.entity.permanent.EligibilityPolicyParameterDefinition;
import org.nucleus.utility.dtomapper.EligibilityPolicyParameterDefinitionDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EligibilityPolicyParameterDefinitionDAOImpl implements EligibilityPolicyParameterDefinitionDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    EligibilityPolicyParameterDefinitionDTOMapper eligibilityParameterDTOMapper;
    @Override
    public boolean updatePerEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO) {
        if(eligibilityParameterDTO == null){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error("Invalid Eligibility Parameter Provided");
            return false;
        }
        EligibilityPolicyParameterDefinition eligibilityParameterPer = eligibilityParameterDTOMapper.dTOToEligibilityParameterPer(eligibilityParameterDTO);
        Session session= sessionFactory.getCurrentSession();
        try{
            session.merge(eligibilityParameterPer);
            return true;
        }catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public boolean deletePerEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO) {
        if(eligibilityParameterDTO == null){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error("Invalid Eligibility Parameter Provided");
            return false;
        }
        Session session= sessionFactory.getCurrentSession();
        EligibilityPolicyParameterDefinition eligibilityParameterPer = eligibilityParameterDTOMapper.dTOToEligibilityParameterPer(eligibilityParameterDTO);
        try{
            session.delete(eligibilityParameterPer);
            return true;
        }
        catch (Exception e){

            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean deletePerEligibilityParameterByCode(String code) {
        if(code == null){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error("Invalid Eligibility Parameter Code Provided");
            return false;
        }
        Session session= sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("delete from EligibilityPolicyParameterDefinition where eligibilityParameterCode = ?0");
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


    @Override
    public EligibilityPolicyParameterDefinitionDTO getEligibilityParameterById(Long eligibilityParameterId) {
        Session session= sessionFactory.getCurrentSession();
        try{
            return eligibilityParameterDTOMapper.perEligibilityParameterToDTO(session.get(EligibilityPolicyParameterDefinition.class, eligibilityParameterId));
        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean deleteEligibilityParameterPerById(Long id) {
        Session session= sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("delete from EligibilityPolicyParameterDefinition where eligibilityParameterId = ?0");
            query.setParameter(0, id);
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

    @Override
    public EligibilityPolicyParameterDefinitionDTO searchEligibilityParameterPerByCode(String code) {
        Session session= sessionFactory.getCurrentSession();
        try{
            EligibilityPolicyParameterDefinition eligibilityPolicyParameterDefinition = session.createQuery(" from EligibilityPolicyParameterDefinition where eligibilityParameterCode = ?0",EligibilityPolicyParameterDefinition.class)
                    .setParameter(0, code)
                    .list().get(0);
            if(eligibilityPolicyParameterDefinition == null){
                return null;
            }
            EligibilityPolicyParameterDefinitionDTO eligibilityPolicyParameterDefinitionDTO = eligibilityParameterDTOMapper.perEligibilityParameterToDTO(eligibilityPolicyParameterDefinition);
            return eligibilityPolicyParameterDefinitionDTO;

        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error(e.getMessage(), e);
            return null;
        }
    }


    public boolean insertPerEligibilityParameter(EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO) {
        if(eligibilityParameterDTO == null){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error("Invalid Eligibility Parameter Provided");
            return false;
        }
        EligibilityPolicyParameterDefinition eligibilityParameterPer = eligibilityParameterDTOMapper.dTOToEligibilityParameterPer(eligibilityParameterDTO);
        Session session= sessionFactory.getCurrentSession();
        try{
            session.save(eligibilityParameterPer);

            return true;
        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error(e.getMessage(), e);
            return false;
        }

    }





    @Override
    public List<EligibilityPolicyParameterDefinitionDTO> getAllPerEligibilityParameter() {
        List<EligibilityPolicyParameterDefinition> eligibilityParameterPerList ;
        List<EligibilityPolicyParameterDefinitionDTO> eligibilityParameterDTOList = new ArrayList<>();
        Session session= sessionFactory.getCurrentSession();
        try{
            eligibilityParameterPerList = session.createQuery("from EligibilityPolicyParameterDefinition" , EligibilityPolicyParameterDefinition.class)
                    .list();
        }
        catch (Exception e){
            LogManager.getLogger(EligibilityPolicyParameterDefinitionDAOImpl.class).error(e.getMessage(), e);
            return null;
        }

        for (EligibilityPolicyParameterDefinition eligibilityParameterPer : eligibilityParameterPerList) {
            EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = eligibilityParameterDTOMapper.perEligibilityParameterToDTO(eligibilityParameterPer);
            eligibilityParameterDTOList.add(eligibilityParameterDTO);
        }
        return eligibilityParameterDTOList;
    }


}
