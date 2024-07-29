package org.nucleus.dao;/*
Developer: Vibhav Sehrawat
*/

import org.apache.logging.log4j.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.nucleus.dto.ProductTypeDTO;
import org.nucleus.entity.temporary.ProductTypeTemp;
import org.nucleus.utility.dtomapper.ProductTypeDTOMapper;
import org.nucleus.utility.enums.RecordStatus;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductTypeTempDAOImpl implements ProductTypeTempDAO
{
    private final SessionFactory sessionFactory;
    private final ProductTypeDTOMapper productTypeDTOMapper;

    @Autowired
    public ProductTypeTempDAOImpl(SessionFactory sessionFactory, ProductTypeDTOMapper productTypeDTOMapper) {
        this.sessionFactory = sessionFactory;
        this.productTypeDTOMapper = productTypeDTOMapper;
    }


    @Override
    public boolean addProductType(ProductTypeDTO productType) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(productTypeDTOMapper.dtoToTemp(productType));
            return true;
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error inserting ProductType into TMP: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProductType(ProductTypeDTO productType) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(productTypeDTOMapper.dtoToTemp(productType));
            return true;
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error updating ProductType into TMP: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProductType(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            ProductTypeTemp productType = session.get(ProductTypeTemp.class, id);
            if (productType != null) {
                session.delete(productType);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error deleting ProductType from TMP: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public ProductTypeDTO getProductTypeByID(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return productTypeDTOMapper.tempToDto(session.get(ProductTypeTemp.class, id));
        }
        catch (Exception e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error fetching ProductType from TMP: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ProductTypeDTO getProductTypeByCode(String code) {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<ProductTypeTemp> query = session.createQuery("FROM ProductTypeTemp WHERE productTypeCode = :productTypeCode", ProductTypeTemp.class);
            query.setParameter("productTypeCode", code);
            return productTypeDTOMapper.tempToDto(query.getSingleResult());
        } catch (NoResultException e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).info("No ProductType found with given Code found...");
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error fetching ProductType from TMP: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<ProductTypeDTO> getAllProductTypes() {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<ProductTypeTemp> query = session.createQuery("FROM ProductTypeTemp pt WHERE pt.metaData.saveFlag = false", ProductTypeTemp.class);
            return query.getResultList()
                    .stream()
                    .map(productTypeDTOMapper::tempToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error fetching ProductTypes from TMP: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<ProductTypeDTO> getAllValidProductTypes() {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<ProductTypeTemp> query = session.createQuery("FROM ProductTypeTemp pt WHERE pt.metaData.saveFlag = false AND pt.metaData.recordStatus NOT IN (:statusList)", ProductTypeTemp.class);
            query.setParameter("statusList", Arrays.asList(RecordStatus.NR, RecordStatus.MR, RecordStatus.DR));
            return query.getResultList()
                    .stream()
                    .map(productTypeDTOMapper::tempToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error fetching ProductTypes from TMP: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public ProductTypeDTO getProductTypeWithSaveFlag() {
        try {
            Session session = sessionFactory.getCurrentSession();
            TypedQuery<ProductTypeTemp> query = session.createQuery("FROM ProductTypeTemp pt WHERE pt.metaData.saveFlag = true", ProductTypeTemp.class);
            return productTypeDTOMapper.tempToDto(query.getSingleResult());
        } catch (NoResultException e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).info("No ProductType found with saveFlag = true");
        }
        catch (HibernateException e) {
            LogManager.getLogger(ProductTypeTempDAOImpl.class).error("Error fetching ProductType from TMP: {}", e.getMessage());
        }
        return null;
    }

}
