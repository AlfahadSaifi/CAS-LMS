package org.nucleus.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.ChargeDefinitionTempDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.temporary.ChargeDefinitionTemp;
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
public class ChargeDefinitionTempDaoImpl implements ChargeDefinitionTempDao {
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    ChargeDefinitionDTOToChargeDefinitionMapper chargeDefinitionDTOMapping;

    private static final Logger logger = LogManager.getLogger(ChargeDefinitionTempDaoImpl.class);
    @Override
    @Transactional
    public boolean saveChargeDefinition(ChargeDefinitionTempDTO chargeDefinitionTempDTO) throws ChargeDefinitionCodeAlreadyExist { // SAVE CHARGEDEFINITION ENTITY INTO TEMPORARY TABLE
        ChargeDefinitionTemp chargeDefinitionTemp = chargeDefinitionDTOMapping.tempDtoToTempEntityConvertor(chargeDefinitionTempDTO);
        Session session = sessionFactory.getCurrentSession();
        try {
            if(chargeDefinitionTemp.getMetaData().getSaveFlag()){
                ChargeDefinitionTemp chargeDefinitionTemp1 = fetchSaveData1();
                if(chargeDefinitionTemp1 !=null){
                    session.delete(chargeDefinitionTemp1);

                }
            }
            if(!isChargeDefinitionCodeExists(chargeDefinitionTemp.getChargeDefinitionCode())){
                session.save(chargeDefinitionTemp);
                chargeDefinitionTemp.getMetaData().setCreationDate(Date.valueOf(LocalDate.now()));
                return true;
            }else {
                throw new ChargeDefinitionCodeAlreadyExist("Charge Definition Code Already Exist");
            }


        }catch (ChargeDefinitionCodeAlreadyExist e ){
            throw new ChargeDefinitionCodeAlreadyExist("Charge Definition code exist already");
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }

    }

    @Override
    @Transactional
    public ChargeDefinitionTempDTO fetchSaveData() { //THIS METHOD FETCH THAT DATA WHICH WAS SAVED WHEN WE CLICK ON SAVE BUTTON
        Session session = sessionFactory.getCurrentSession();
        try{
            List<ChargeDefinitionTemp> charges = session.createQuery(
                            "FROM ChargeDefinitionTemp WHERE saveFlag = :flag", ChargeDefinitionTemp.class)
                    .setParameter("flag", true)
                    .getResultList();
            if(charges.isEmpty()){
                return null;
            }else{
                session.delete(charges.get(0));
                ChargeDefinitionTempDTO chargeDefinitionTempDTO = null;
                chargeDefinitionTempDTO = chargeDefinitionDTOMapping.tempEntityToTempDtoConvertor(charges.get(0));
                return chargeDefinitionTempDTO;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public List<ChargeDefinitionTempDTO> fetchSaveDataByFlag() { // FETCH ALL THE DATA WHERE SAVE AND REQUEST BUTTON CLICKED
        Session session = sessionFactory.getCurrentSession();
        try {
            List<ChargeDefinitionTemp> chargeDefinitionsListTemp = session.createQuery("From ChargeDefinitionTemp Where saveFlag = :flag", ChargeDefinitionTemp.class)
                    .setParameter("flag",false)
                    .getResultList();
            List<ChargeDefinitionTempDTO> chargeDefinitionTempDTOList = null;
            if(!chargeDefinitionsListTemp.isEmpty()){
                chargeDefinitionTempDTOList = new ArrayList<>();
                for(ChargeDefinitionTemp chargeDefinitionTemp : chargeDefinitionsListTemp){
                    ChargeDefinitionTempDTO chargeDefinitionTempDTO = chargeDefinitionDTOMapping.tempEntityToTempDtoConvertor(chargeDefinitionTemp);
                    chargeDefinitionTempDTOList.add(chargeDefinitionTempDTO);
                }
            }
            return chargeDefinitionTempDTOList;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ChargeDefinitionTempDTO fetchChargeDefinitionByCode(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try{
            List<ChargeDefinitionTemp> chargeDefinitionTemps = session.createQuery("from ChargeDefinitionTemp where chargeDefinitionCode = : chargeDefinitionCode", ChargeDefinitionTemp.class)
                    .setParameter("chargeDefinitionCode",chargeDefinitionCode)
                    .getResultList();
            if(!chargeDefinitionTemps.isEmpty()){
                return chargeDefinitionDTOMapping.tempEntityToTempDtoConvertor(chargeDefinitionTemps.get(0));

            }

        }catch (Exception e){

            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public boolean updateChargeDefinition(ChargeDefinitionTempDTO chargeDefinitionTempDTO) {
//
        Session session =sessionFactory.getCurrentSession();
        ChargeDefinitionTemp chargeDefinitionTemp = chargeDefinitionDTOMapping.tempDtoToTempEntityConvertor(chargeDefinitionTempDTO);
        try{
            Query<Long> query = session.createQuery(
                    "SELECT c.chargeDefiniteId FROM ChargeDefinitionTemp c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", Long.class);
            query.setParameter("chargeDefinitionCode", chargeDefinitionTemp.getChargeDefinitionCode());

            Long chargeDefiniteId = query.uniqueResult();
            chargeDefinitionTemp.setChargeDefiniteId(chargeDefiniteId);
            session.update(chargeDefinitionTemp);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteChargeDefinition(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try{
            ChargeDefinitionTemp chargeDefinitionTemp = fetchChargeDefinitionByCode1(chargeDefinitionCode);
            if(chargeDefinitionTemp !=null){
                session.delete(chargeDefinitionTemp);
                return true;
            }
        }catch (Exception e){
            logger.error(e.getMessage());

        }
        return false;

    }

    @Transactional
    public boolean isChargeDefinitionCodeExists(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try {

            // Create query to check if the chargeDefinitionCode already exists
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(c) FROM ChargeDefinitionTemp c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", Long.class);
            query.setParameter("chargeDefinitionCode", chargeDefinitionCode);

            // Execute query and get result
            Long count = query.uniqueResult();



            return count > 0;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public List<ChargeDefinitionTempDTO> fetchChargeDefinitionFromTemporaryTable() {
//SELECT n FROM Note n WHERE n.priority IN ?1
        Session session = sessionFactory.getCurrentSession();
        try{
            List<ChargeDefinitionTemp> chargeDefinitionTemps = session.createNativeQuery("select * from CHARGE_DEFINITION_TEMP_TBL_BATCH_6 c where c.recordStatus IN ?1", ChargeDefinitionTemp.class)
                    .setParameter(1,List.of("N", "M", "D"))
                    .getResultList();
            List<ChargeDefinitionTempDTO> chargeDefinitionTempDTOList = new ArrayList<>();
            if(!chargeDefinitionTemps.isEmpty()){
                for(ChargeDefinitionTemp chargeDefinitionTemp : chargeDefinitionTemps){
                    ChargeDefinitionTempDTO chargeDefinitionTempDTO = chargeDefinitionDTOMapping.tempEntityToTempDtoConvertor(chargeDefinitionTemp);
                    chargeDefinitionTempDTOList.add(chargeDefinitionTempDTO);
                }
                return chargeDefinitionTempDTOList;
            }

        }catch (Exception e){
            logger.error(e.getMessage());

        }return null;
    }

    @Override
    @Transactional
    public ChargeDefinitionTempDTO approveChargeDefinition(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try{
            Query<ChargeDefinitionTemp> query = session.createQuery(
                    " FROM ChargeDefinitionTemp c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", ChargeDefinitionTemp.class);
            query.setParameter("chargeDefinitionCode", chargeDefinitionCode);
            ChargeDefinitionTemp chargeDefinitionTemp1 = query.uniqueResult();
            ChargeDefinitionTempDTO chargeDefinitionTempDTO =chargeDefinitionDTOMapping.tempEntityToTempDtoConvertor(chargeDefinitionTemp1);
            TempMetaData tempMetaData = chargeDefinitionTemp1.getMetaData();
            chargeDefinitionTemp1.setMetaData(tempMetaData);
            session.delete(chargeDefinitionTemp1);
            return chargeDefinitionTempDTO;


        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public boolean rejectChargeDefinition(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try{

            Query<ChargeDefinitionTemp> query = session.createQuery(
                    " FROM ChargeDefinitionTemp c WHERE c.chargeDefinitionCode = :chargeDefinitionCode", ChargeDefinitionTemp.class);
            query.setParameter("chargeDefinitionCode", chargeDefinitionCode);
            ChargeDefinitionTemp chargeDefinitionTemp1 = query.uniqueResult();
            TempMetaData tempMetaData = chargeDefinitionTemp1.getMetaData();
            RecordStatus recordStatus = tempMetaData.getRecordStatus();
            if(recordStatus==RecordStatus.N) {
                tempMetaData.setRecordStatus(RecordStatus.NR);
            }else if (recordStatus==RecordStatus.D){
                tempMetaData.setRecordStatus(RecordStatus.DR);
            } else if (recordStatus==RecordStatus.M) {
                tempMetaData.setRecordStatus(RecordStatus.MR);
            }
            tempMetaData.setAuthorizedDate(Date.valueOf(LocalDate.now()));
            chargeDefinitionTemp1.setMetaData(tempMetaData);
            session.update(chargeDefinitionTemp1);

            return true;


        }catch (Exception e){

            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean saveModifyRequestDetails(ChargeDefinitionTempDTO chargeDefinitionTempDTO) {
        ChargeDefinitionTemp chargeDefinitionTemp = chargeDefinitionDTOMapping.tempDtoToTempEntityConvertor(chargeDefinitionTempDTO);
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(chargeDefinitionTemp);
            return true;

        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean saveDeleteRequestDetails(ChargeDefinitionTempDTO chargeDefinitionTempDTO) {
        ChargeDefinitionTemp chargeDefinitionTemp = chargeDefinitionDTOMapping.tempDtoToTempEntityConvertor(chargeDefinitionTempDTO);
        if(!isChargeDefinitionCodeExists(chargeDefinitionTemp.getChargeDefinitionCode())){
            Session session = sessionFactory.getCurrentSession();
            try{
                chargeDefinitionTemp.getMetaData().setSaveFlag(false);
                chargeDefinitionTemp.getMetaData().setRecordStatus(RecordStatus.D);
                session.save(chargeDefinitionTemp);
                return true;
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }return false;
    }

    @Transactional
    public ChargeDefinitionTemp fetchSaveData1() {
        Session session = sessionFactory.getCurrentSession();
        try{
            List<ChargeDefinitionTemp> charges = session.createQuery(
                            "FROM ChargeDefinitionTemp WHERE saveFlag = :flag", ChargeDefinitionTemp.class)
                    .setParameter("flag", true)
                    .getResultList();
            if(charges.isEmpty()){
                return null;
            }else{
                session.delete(charges.get(0));
                return charges.get(0);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
    @Transactional
    public ChargeDefinitionTemp fetchChargeDefinitionByCode1(String chargeDefinitionCode) {
        Session session = sessionFactory.getCurrentSession();
        try{
            List<ChargeDefinitionTemp> chargeDefinitionTemps = session.createQuery("from ChargeDefinitionTemp where chargeDefinitionCode = : chargeDefinitionCode", ChargeDefinitionTemp.class)
                    .setParameter("chargeDefinitionCode",chargeDefinitionCode)
                    .getResultList();
            if(!chargeDefinitionTemps.isEmpty()){
                return chargeDefinitionTemps.get(0);
            }

        }catch (Exception e){
            logger.error(e.getMessage());

        }
        return null;
    }

}



