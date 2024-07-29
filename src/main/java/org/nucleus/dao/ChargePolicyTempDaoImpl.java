package org.nucleus.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.ChargePolicyTempDto;
import org.nucleus.entity.temporary.ChargePolicyTemp;
import org.nucleus.exception.PolicyCodeAlreadyExistException;
import org.nucleus.utility.dtomapper.ChargePolicyTempDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChargePolicyTempDaoImpl implements ChargePolicyTempDao {
    private final static Logger logger = LogManager.getLogger(ChargePolicyTempDaoImpl.class);


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ChargePolicyTempDtoMapper chargePolicyTempDtoMapper;


    @Transactional
    @Override
    public boolean saveChargePolicy(ChargePolicyTempDto chargePolicyTempDto) throws PolicyCodeAlreadyExistException {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            ChargePolicyTemp chargePolicy = chargePolicyTempDtoMapper.dtoToEntityConverter(chargePolicyTempDto);
            ChargePolicyTempDto chargePolicy1 = getChargePolicy(chargePolicy.getPolicyCode());

            if(chargePolicy1!=null)
            {
                throw new PolicyCodeAlreadyExistException("This Policy Code already exist");
            }
            session.merge(chargePolicy);
            return true;
        }
        catch (PolicyCodeAlreadyExistException e)
        {
            logger.error(e.getMessage());
            throw new PolicyCodeAlreadyExistException("This Policy Code already exist");
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }
        return false;
    }


    @Transactional
    @Override
    public boolean updateChargePolicy(ChargePolicyTempDto chargePolicyTempDto) {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            ChargePolicyTemp chargePolicy = chargePolicyTempDtoMapper.dtoToEntityConverter(chargePolicyTempDto);
            session.update(chargePolicy);

            return true;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return false;
    }

    @Transactional
    @Override
    public ChargePolicyTempDto getChargePolicy(String policyCode) {
        ChargePolicyTempDto chargePolicyTempDto=null;
        try
        {
            Session session = sessionFactory.getCurrentSession();
            ChargePolicyTemp chargePolicy1 = session.get(ChargePolicyTemp.class,policyCode);
            if(chargePolicy1!=null)
            {
                 chargePolicyTempDto = chargePolicyTempDtoMapper.entityToDtoConverter(chargePolicy1);
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return chargePolicyTempDto;
    }


    @Transactional
    @Override
    public boolean deleteChargePolicy(String policyCode) {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            ChargePolicyTemp chargePolicy = session.get(ChargePolicyTemp.class,policyCode);
            session.delete(chargePolicy);
            return true;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return false;
    }


    @Transactional
    @Override
    public List<ChargePolicyTempDto> getAllChargePolicy() {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            List<ChargePolicyTemp> chargePolicies = session.createNativeQuery(" select * from CHARGE_POLICY_TEMP_TBL_BATCH_6 where saveFlag = ?1", ChargePolicyTemp.class)
                    .setParameter(1,0)
                    .list();

            List<ChargePolicyTempDto> chargePolicyTempDtoList = new ArrayList<>();
            for(ChargePolicyTemp chargePolicy: chargePolicies)
            {
                ChargePolicyTempDto chargePolicyTempDto = chargePolicyTempDtoMapper.entityToDtoConverter(chargePolicy);
                chargePolicyTempDtoList.add(chargePolicyTempDto);
            }

            return chargePolicyTempDtoList;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return null;
    }


    @Transactional
    @Override
    public ChargePolicyTempDto getChargePolicyByEditFlag(Boolean flagForEdit) {
        ChargePolicyTempDto chargePolicyTempDto=null;
        try
        {
            Session session = sessionFactory.getCurrentSession();

            List<ChargePolicyTemp> chargePolicies = session.createNativeQuery(" select * from CHARGE_POLICY_TEMP_TBL_BATCH_6 where saveFlag = ?1", ChargePolicyTemp.class)
                    .setParameter(1,flagForEdit)
                    .list();
            if(chargePolicies!=null && !chargePolicies.isEmpty())
            {
                 chargePolicyTempDto = chargePolicyTempDtoMapper.entityToDtoConverter(chargePolicies.get(0));
            }

        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return chargePolicyTempDto;
    }
}
