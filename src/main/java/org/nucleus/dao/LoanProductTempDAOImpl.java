/*developer: vaishnavi agrawal*/

package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.entity.temporary.LoanProductTemp;
import org.nucleus.utility.dtomapper.LoanProductDTOMapper;
import org.nucleus.utility.enums.RecordStatus;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LoanProductTempDAOImpl implements LoanProductTempDAO {

    private SessionFactory sessionFactory;
    private LoanProductDTOMapper loanProductDTOMapper;

    @Autowired
    public LoanProductTempDAOImpl(SessionFactory sessionFactory, LoanProductDTOMapper loanProductDTOMapper) {
        this.sessionFactory = sessionFactory;
        this.loanProductDTOMapper = loanProductDTOMapper;
    }

    @Override
    public boolean addLoanProduct(LoanProductDTO loanProductDTO) {
        try {
            Session session = sessionFactory.getCurrentSession();
            LoanProductTemp loanProductTemp = loanProductDTOMapper.dtoToLoanProductTemp(loanProductDTO);
            session.save(loanProductTemp);
            return true;
        }
        catch(Exception e){
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error inserting LoanProduct from TMP: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateLoanProduct(LoanProductDTO loanProductDTO) {
        try{
            Session session= sessionFactory.getCurrentSession();
            LoanProductTemp loanProductTemp = loanProductDTOMapper.dtoToLoanProductTemp(loanProductDTO);
            session.merge(loanProductTemp);
            return true;
        }
        catch(Exception e){
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error updating LoanProduct from TMP: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteLoanProduct(Long id) {
        try{
            Session session=sessionFactory.getCurrentSession();
            LoanProductTemp loanProductTemp=session.get(LoanProductTemp.class, id);
            if(loanProductTemp != null) {
                session.delete(loanProductTemp);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error deleting LoanProduct from TMP: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public LoanProductDTO getLoanProductByID(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            LoanProductTemp loanProductTemp = session.get(LoanProductTemp.class, id);
            return loanProductDTOMapper.loanProductTempToDto(loanProductTemp);
        } catch (Exception e) {
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error fetching LoanProduct from TMP: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public LoanProductDTO getLoanProductByCode(String code) {
        try {
            Session session=sessionFactory.getCurrentSession();
            String query = "FROM LoanProductTemp where productCode = :productCode";
            TypedQuery<LoanProductTemp> query1 = session.createQuery(query, LoanProductTemp.class);
            query1.setParameter("productCode", code);
            LoanProductTemp loanProductTemp = query1.getSingleResult();
            return loanProductDTOMapper.loanProductTempToDto(loanProductTemp);
        } catch (NoResultException e) {
            LogManager.getLogger(LoanProductTempDAOImpl.class).info("No Loan Product found with given Code found...");
        } catch (Exception e) {
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error fetching LoanProduct from TMP: {}", e.getMessage());
        }
        return null;
    }


    @Override
    public List<LoanProductDTO> getAllLoanProducts() {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<LoanProductTemp> query = session.createQuery("FROM LoanProductTemp lp WHERE lp.metaData.saveFlag = false", LoanProductTemp.class);
            return query.getResultList()
                    .stream()
                    .map(loanProductDTOMapper::loanProductTempToDto)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error fetching LoanProduct from TMP: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public LoanProductDTO getLoanProductWithSaveFlag() {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<LoanProductTemp> query = session.createQuery("FROM LoanProductTemp lp WHERE lp.metaData.saveFlag = true", LoanProductTemp.class);
            LoanProductTemp loanProductTemp=query.getSingleResult();
            return loanProductDTOMapper.loanProductTempToDto(loanProductTemp);
        } catch (NoResultException e) {
            LogManager.getLogger(LoanProductTempDAOImpl.class).info("No LoanProduct found with saveFlag = true");
        }
        catch (HibernateException e) {
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error fetching LoanProduct from TMP: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<LoanProductDTO> getAllValidLoanProducts() {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<LoanProductTemp> query = session.createQuery("FROM LoanProductTemp lp WHERE lp.metaData.saveFlag = false AND lp.metaData.recordStatus NOT IN (:statusList)", LoanProductTemp.class);
            query.setParameter("statusList", Arrays.asList(RecordStatus.NR, RecordStatus.MR, RecordStatus.DR));
            return query.getResultList()
                    .stream()
                    .map(loanProductDTOMapper::loanProductTempToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(LoanProductTempDAOImpl.class).error("Error fetching loanProducts from TMP: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

}
