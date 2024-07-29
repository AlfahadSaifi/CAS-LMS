package org.nucleus.dao;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.entity.temporary.ReceiptTemp;
import org.nucleus.utility.dtomapper.ReceiptDTOMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ReceiptDAOTempImpl implements ReceiptDAOTemp {
    public static final Logger logger = LogManager.getLogger(ReceiptDAOTempImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    ReceiptDTOMapping receiptDTOMapping = new ReceiptDTOMapping();
    @Override
    public boolean createReceiptTemp(ReceiptDTO payment) {
        ReceiptTemp receiptTemp = receiptDTOMapping.mapDTOtoEntityTemp(payment);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(receiptTemp);
            logger.info("Receipt Payment Inserted successfully in temporary table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    @Override
    public ReceiptDTO getReceiptTempById(Long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            ReceiptTemp receiptTemp = session.get(ReceiptTemp.class, id);
            return receiptDTOMapping.mapEntityTemptoDTO(receiptTemp);
        } catch (Exception e) {
            logger.error(e.getMessage() + " in temporary table");
            return null;
        }
    }
    @Override
    public List<ReceiptDTO> getAllReceiptTemps() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<ReceiptTemp> receipts = session.createQuery("from ReceiptTemp", ReceiptTemp.class).list();
            return receipts.stream().map(n -> receiptDTOMapping.mapEntityTemptoDTO(n)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("no Receipt entries found in the temporary table");
            return Collections.emptyList();
        }
    }
    @Override
    public boolean updateReceiptTemp(ReceiptDTO updatedReceiptTemp) {
        ReceiptTemp receiptTemp = receiptDTOMapping.mapDTOtoEntityTemp(updatedReceiptTemp);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.update(receiptTemp);
            logger.info("Receipt updated successfully in temporary table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in temporary table");
            return false;
        }
    }
    @Override
    public boolean deleteReceiptTemp(ReceiptDTO receiptDTO){
        ReceiptTemp receiptTemp = receiptDTOMapping.mapDTOtoEntityTemp(receiptDTO);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(receiptTemp);
            logger.info("Receipt deleted successfully from temporary table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in temporary table");
            return false;
        }
        }

    @Override
    public List<ReceiptDTO> getListReceiptTempById(Long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<ReceiptTemp> receiptTemps = session.createQuery("from ReceiptTemp where RECEIVABLEPAYABLEID= :rid",ReceiptTemp.class).setParameter("rid",id).list();

            List<ReceiptDTO> receiptDTOS = new ArrayList<>();
            for(ReceiptTemp receiptTemp : receiptTemps){

                ReceiptDTO receiptDTO = receiptDTOMapping.mapEntityTemptoDTO(receiptTemp);
                System.out.println(receiptDTO);
                receiptDTOS.add(receiptDTO);
            }
            return receiptDTOS;
//            return receiptTemps.stream().map(n -> receiptDTOMapping.mapEntityTemptoDTO(n)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("ayush exception ", e);
            return Collections.emptyList();
        }
    }
}
