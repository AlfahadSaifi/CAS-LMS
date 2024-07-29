package org.nucleus.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.entity.permanent.Allocation;
import org.nucleus.dto.AllocationDTO;
import org.nucleus.utility.dtomapper.AllocationToDTOMapper;
import org.nucleus.utility.dtomapper.DTOToAllocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class AllocationDAOImpl implements AllocationDAO{
    @Autowired
    private SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger(AllocationDAOImpl.class);
    @Override
    public boolean insertAllocationDetail(AllocationDTO allocationDTO) {
        try {
            Allocation allocation = DTOToAllocationMapper.mapDTOToObject(allocationDTO);
            Session session = sessionFactory.getCurrentSession();
            session.save(allocation);
            return true;
        }catch (HibernateException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAllocation(AllocationDTO allocationDTO) {
        try {
            Allocation allocation = DTOToAllocationMapper.mapDTOToObject(allocationDTO);
            Session session = sessionFactory.getCurrentSession();
            session.update(allocation);
            return true;
        }catch (HibernateException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean batchInsertAllocation(Set<AllocationDTO> allocationDTOList) {
        try {
            List<Allocation> allocationList = allocationDTOList
                    .stream()
                    .map(DTOToAllocationMapper::mapDTOToObject)
                    .collect(Collectors.toList());
            Session session = sessionFactory.getCurrentSession();
            allocationList.forEach(session::save);
            return true;
        }catch (HibernateException e){
            //log over here
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<AllocationDTO> getAllocationList(int batchNumber, int batchSize){
        if(batchSize>0) {
            try {
                Session session = sessionFactory.getCurrentSession();
                Query query = session.createQuery("From Allocation", Allocation.class);
                query.setFirstResult(batchNumber * batchSize);
                query.setMaxResults(batchSize);
                List<Allocation> allocationList = query.getResultList();
                return allocationList
                        .stream()
                        .map(AllocationToDTOMapper::mapObjectToDTO)
                        .collect(Collectors.toList());

            } catch (HibernateException e) {
                logger.error(e.getMessage());
                return null;
            }
        }else{
            return null;
        }
    }

    @Override
    public List<AllocationDTO> getAllocationByLoanAccountId(Long loanAccountId) {
        List<AllocationDTO> allocationList = new ArrayList<>();
        if(loanAccountId>0)
        {
            try {
                Session session = sessionFactory.getCurrentSession();
                String hql = "from Allocation where LOANID =?1";
                 allocationList = session.createQuery(hql, Allocation.class).setParameter(1, loanAccountId).getResultList().stream().map(AllocationToDTOMapper::mapObjectToDTO).collect(Collectors.toList());;
                return allocationList;
            }catch (HibernateException e)
            {
                logger.error(e.getMessage());
                return allocationList;
            }
        }
       return allocationList;
    }
}
