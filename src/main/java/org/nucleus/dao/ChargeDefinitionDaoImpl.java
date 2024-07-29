package org.nucleus.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.entity.permanent.ChargeDefinition;
import org.nucleus.exception.ChargeDefinitionCodeAlreadyExist;
import org.nucleus.utility.dtomapper.ChargeDefinitionDTOToChargeDefinitionMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//AUTHOR: HARKIRAT SINGH
@Repository
public class ChargeDefinitionDaoImpl implements ChargeDefinitionDao {
    private static Logger logger = LogManager.getLogger(ChargeDefinitionDaoImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    ChargeDefinitionDTOToChargeDefinitionMapper chargeDefinitionDTOMapping;


    @Override
    @Transactional
    public boolean saveNewRecordToMasterTable(ChargeDefinitionDTO chargeDefinitionDTO) throws ChargeDefinitionCodeAlreadyExist {
        Session session = sessionFactory.getCurrentSession();
        ChargeDefinition chargeDefinition = chargeDefinitionDTOMapping.dtoToEntityConvertor(chargeDefinitionDTO);


        try{
            if(!isChargeDefinitionCodeExists(chargeDefinition.getChargeDefinitionCode())){
                chargeDefinition.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
                session.save(chargeDefinition);
            } else if (chargeDefinition.getMetaData().getRecordStatus()== RecordStatus.A) {
                //delete
                deleteChargeFromTheMasterTableByCode(chargeDefinition.getChargeDefinitionCode());
                chargeDefinition.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
                session.save(chargeDefinition);

            } else{
                throw new ChargeDefinitionCodeAlreadyExist("Charge Code Already Exist in the master table");
            }

            return true;
        }catch (Exception e){

            throw new ChargeDefinitionCodeAlreadyExist("Charge Code Already Exist in the master table");

        }
    }

    @Transactional
    private void deleteChargeFromTheMasterTableByCode(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try{
            ChargeDefinition chargeDefinition = session.createQuery("from ChargeDefinition c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", ChargeDefinition.class)
                    .setParameter("chargeDefinitionCode",chargeDefinitionCode)
                    .uniqueResult();

            session.delete(chargeDefinition);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<ChargeDefinitionDTO> fetchRecordsFromMasterTable() {
        Session session = sessionFactory.getCurrentSession();
        try{
            List<ChargeDefinitionDTO> chargeDefinitionDTOS = new ArrayList<>();
            List<ChargeDefinition> chargeDefinitions =session.createQuery("from ChargeDefinition",ChargeDefinition.class).getResultList();
            if(chargeDefinitions!=null){
                for(ChargeDefinition chargeDefinition:chargeDefinitions){
                    chargeDefinitionDTOS.add(chargeDefinitionDTOMapping.entityToDtoConvertor(chargeDefinition));

                }
            }return chargeDefinitionDTOS;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }

    }

    @Transactional
    public boolean isChargeDefinitionCodeExists(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try {
            // Begin transaction


            // Create query to check if the chargeDefinitionCode already exists
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(c) FROM ChargeDefinition c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", Long.class);
            query.setParameter("chargeDefinitionCode", chargeDefinitionCode);

            // Execute query and get result
            Long count = query.uniqueResult();

            return count > 0;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
    @Transactional
    @Override
    public List<ChargeDefinitionDTO> fetchAllRecordsOfTheMasterTable() {
        Session session = sessionFactory.getCurrentSession();
        try{
           List<ChargeDefinition> chargeDefinitionList= session.createQuery("from ChargeDefinition", ChargeDefinition.class)
                    .getResultList();
           List<ChargeDefinitionDTO> chargeDefinitionsDTOList = new ArrayList<>();
           if(chargeDefinitionList!=null){
               for(ChargeDefinition chargeDefinition : chargeDefinitionList){
                   ChargeDefinitionDTO chargeDefinitionDTO = chargeDefinitionDTOMapping.entityToDtoConvertor(chargeDefinition);
                   chargeDefinitionsDTOList.add(chargeDefinitionDTO);

               }
               return chargeDefinitionsDTOList;
           }
        }catch (Exception e){
            logger.error(e.getMessage());

        }
        return null;
    }
    @Transactional
    @Override
    public ChargeDefinitionDTO getChargeDefinitionFromTheMasterTableByCode(String chargeDefinitionCode) {
        ChargeDefinitionDTO chargeDefinitionDTO;
        Session session = sessionFactory.getCurrentSession();
        try{
            ChargeDefinition chargeDefinition =session.createQuery("FROM ChargeDefinition c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", ChargeDefinition.class)
                    .setParameter("chargeDefinitionCode",chargeDefinitionCode)
                    .uniqueResult();
            if(chargeDefinition!=null) {

               chargeDefinitionDTO = chargeDefinitionDTOMapping.entityToDtoConvertor(chargeDefinition);
                return chargeDefinitionDTO;
            }

        }catch (Exception e){
            logger.error(e.getMessage());

        }
        return null;
    }
    @Transactional
    @Override
    public boolean deleteSavedRecordFromTheMasterTable(ChargeDefinitionDTO chargeDefinitionDTO) {
        Session session = sessionFactory.getCurrentSession();
        try{
            ChargeDefinition chargeDefinition = getChargeDefiniteObjectFromCode(chargeDefinitionDTO.getChargeDefinitionCode());
            session.delete(chargeDefinition);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }
    @Transactional
    private ChargeDefinition getChargeDefiniteObjectFromCode(String chargeDefinitionCode) {
        ChargeDefinition chargeDefinition;
        Session session = sessionFactory.getCurrentSession();
        try {
            chargeDefinition = session.createQuery("FROM ChargeDefinition c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", ChargeDefinition.class)
                    .setParameter("chargeDefinitionCode",chargeDefinitionCode)
                    .uniqueResult();
            return chargeDefinition;
        }catch (Exception e){
           logger.error(e.getMessage());

        }
        return null;
    }
}
