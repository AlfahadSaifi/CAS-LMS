package org.nucleus.dao;/*
Developer: Vibhav Sehrawat
*/

import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.entity.permanent.ProductType;
import org.nucleus.utility.dtomapper.ProductTypeDTOMapper;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductTypeDAOImpl implements ProductTypeDAO
{
    private final SessionFactory sessionFactory;
    private final ProductTypeDTOMapper productTypeDTOMapper;

    @Autowired
    public ProductTypeDAOImpl(SessionFactory sessionFactory, ProductTypeDTOMapper productTypeDTOMapper) {
        this.sessionFactory = sessionFactory;
        this.productTypeDTOMapper = productTypeDTOMapper;
    }

    @Override
    public boolean addProductType(ProductTypeDTO productType) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(productTypeDTOMapper.dtoToMaster(productType));
            return true;
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error inserting ProductType into MST: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProductType(ProductTypeDTO productType) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(productTypeDTOMapper.dtoToMaster(productType));
            return true;
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error updating ProductType from MST: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProductType(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            ProductType productType = session.get(ProductType.class, id);
            if (productType != null) {
                session.delete(productType);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error deleting ProductType from MST: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public ProductTypeDTO getProductTypeByID(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return productTypeDTOMapper.masterToDto(session.get(ProductType.class, id));
        }
        catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error fetching ProductType from MST: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public ProductTypeDTO getProductTypeByCode(String code) {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<ProductType> query = session.createQuery("FROM ProductType WHERE productTypeCode = :productTypeCode", ProductType.class);
            query.setParameter("productTypeCode", code);
            return productTypeDTOMapper.masterToDto(query.getSingleResult());
        } catch (NoResultException e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).info("No ProductType found with given Code");
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error fetching ProductType from MST: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<ProductTypeDTO> getAllProductTypes() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM ProductType", ProductType.class)
                    .getResultList()
                    .stream()
                    .map(productTypeDTOMapper::masterToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error fetching ProductTypes from MST: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<ProductTypeDTO> getAllValidProductTypes() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM ProductType pt WHERE pt.metaData.activeInactiveFlag IS NULL OR pt.metaData.activeInactiveFlag = true", ProductType.class)
                    .getResultList()
                    .stream()
                    .map(productTypeDTOMapper::masterToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error fetching ProductTypes from MST: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getAllProductTypeCodes() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("SELECT pt.productTypeCode FROM ProductType pt WHERE pt.metaData.activeInactiveFlag IS NULL OR pt.metaData.activeInactiveFlag = true", String.class)
                    .getResultList();
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeDAOImpl.class).error("Error fetching ProductTypes from MST: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
