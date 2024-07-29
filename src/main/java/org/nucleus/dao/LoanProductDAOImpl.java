//developer: vaishnavi agrawal

package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.entity.permanent.LoanProduct;
import org.nucleus.utility.dtomapper.LoanProductDTOMapper;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class LoanProductDAOImpl implements LoanProductDAO {

    private LoanProductDTOMapper loanProductDTOMapper;
    private SessionFactory sessionFactory;

    @Autowired
    public LoanProductDAOImpl(LoanProductDTOMapper loanProductDTOMapper, SessionFactory sessionFactory) {
        this.loanProductDTOMapper = loanProductDTOMapper;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean addLoanProduct(LoanProductDTO loanProductDTO) {
        try {
            Session session = sessionFactory.getCurrentSession();
            LoanProduct loanProduct = loanProductDTOMapper.dtoToLoanProduct(loanProductDTO);
            session.save(loanProduct);
            return true;
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error inserting LoanProduct into MST: ", e);
            return false;
        }
    }

    @Override
    public boolean updateLoanProduct(LoanProductDTO loanProductDTO) {
        try {
            Session session = sessionFactory.getCurrentSession();
            LoanProduct loanProduct = loanProductDTOMapper.dtoToLoanProduct(loanProductDTO);
            session.merge(loanProduct);
            return true;
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error updating LoanProduct from MST: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteLoanProduct(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            LoanProduct loanProduct = session.get(LoanProduct.class, id);
            if (loanProduct != null) {
                session.delete(loanProduct);
                return true;
            }
            return false;
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error deleting LoanProduct from MST: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public LoanProductDTO getLoanProductByID(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            LoanProduct loanProduct = session.get(LoanProduct.class, id);
            return loanProductDTOMapper.loanProductToDto(loanProduct);
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error fetching LoanProduct from MST: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public LoanProductDTO getLoanProductByCode(String code) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String query = "from LoanProduct where productCode = :productCode";
            TypedQuery<LoanProduct> query1 = session.createQuery(query, LoanProduct.class);
            query1.setParameter("productCode", code);
            LoanProduct loanProduct = query1.getSingleResult();
            return loanProductDTOMapper.loanProductToDto(loanProduct);
        } catch (NoResultException e) {
            LogManager.getLogger(LoanProductDAOImpl.class).info("No Loan Product found with given Code");
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error fetching LoanProduct from MST: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<LoanProductDTO> getAllLoanProducts() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM LoanProduct", LoanProduct.class)
                    .getResultList()
                    .stream()
                    .map(loanProductDTOMapper::loanProductToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error fetching LoanProduct from MST: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<LoanProductDTO> getAllValidLoanProducts() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM LoanProduct lp WHERE lp.metaData.activeInactiveFlag IS NULL OR lp.metaData.activeInactiveFlag = true", LoanProduct.class)
                    .getResultList()
                    .stream()
                    .map(loanProductDTOMapper::loanProductToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error fetching LoanProduct from MST: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getAllLoanProductCodes() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("SELECT lp.loanProductCode FROM LoanProduct lp WHERE lp.metaData.activeInactiveFlag IS NULL OR lp.metaData.activeInactiveFlag = true", String.class)
                    .getResultList();
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error fetching LoanProducts from MST: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<LoanProductDTO> getAllLoanProductsByProductTypeId(Long productTypeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createNativeQuery("select * FROM LOAN_PRODUCT_TBL_BATCH_6 lp WHERE lp.productTypeId = :productTypeId ", LoanProduct.class)
                    .setParameter("productTypeId", productTypeId)
                    .getResultList()
                    .stream()
                    .map(loanProductDTOMapper::loanProductToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(LoanProductDAOImpl.class).error("Error fetching LoanProducts from MST: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
