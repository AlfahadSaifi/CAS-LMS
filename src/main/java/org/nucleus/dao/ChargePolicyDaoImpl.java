package org.nucleus.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.ChargePolicyDto;
import org.nucleus.entity.permanent.ChargePolicy;
import org.nucleus.utility.dtomapper.ChargePolicyDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChargePolicyDaoImpl implements ChargePolicyDao {
    private final static Logger logger = LogManager.getLogger(ChargePolicyDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ChargePolicyDtoMapper chargePolicyDtoMapper;

    @Transactional
    @Override
    public boolean saveChargePolicy(ChargePolicyDto chargePolicyDto){
        try
        {
            Session session = sessionFactory.getCurrentSession();
            if(chargePolicyDto!=null) {
                ChargePolicy chargePolicy = chargePolicyDtoMapper.dtoToEntityConverter(chargePolicyDto);
                session.merge(chargePolicy);
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean editChargePolicy(ChargePolicyDto chargePolicyDto) {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            ChargePolicy chargePolicy = chargePolicyDtoMapper.dtoToEntityConverter(chargePolicyDto);
            session.saveOrUpdate(chargePolicy);
            return true;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return false;
    }

    @Override
    @Transactional
    public ChargePolicyDto getChargePolicy(String policyCode) {
        ChargePolicyDto chargePolicyTempDto = null;
        try
        {
            Session session = sessionFactory.getCurrentSession();
            ChargePolicy chargePolicy1 = session.get(ChargePolicy.class,policyCode);

            if(chargePolicy1!=null)
                chargePolicyTempDto = chargePolicyDtoMapper.entityToDtoConverter(chargePolicy1);
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
            ChargePolicy chargePolicy = session.get(ChargePolicy.class,policyCode);
            session.delete(chargePolicy);
            return true;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return false;
    }

    @Override
    @Transactional
    public List<ChargePolicyDto> getAllChargePolicy() {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            List<ChargePolicy> chargePolicies = session.createNativeQuery("Select * from CHARGE_POLICY_TBL_BATCH_6", ChargePolicy.class)
                    .list();

            List<ChargePolicyDto> chargePolicyTempDtoList = new ArrayList<>();
            for(ChargePolicy chargePolicy: chargePolicies)
            {
                ChargePolicyDto chargePolicyDto = chargePolicyDtoMapper.entityToDtoConverter(chargePolicy);
                chargePolicyTempDtoList.add(chargePolicyDto);
            }

            return chargePolicyTempDtoList;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return null;
    }

}
