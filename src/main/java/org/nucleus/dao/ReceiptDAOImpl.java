package org.nucleus.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.entity.permanent.Receipt;
import org.nucleus.utility.dtomapper.ReceiptDTOMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ReceiptDAOImpl implements ReceiptDAO {
    public static final Logger logger = LogManager.getLogger(ReceiptDAOImpl.class);
    @Autowired
    SessionFactory sessionFactory;

    ReceiptDTOMapping receiptDTOMapping = new ReceiptDTOMapping();
    @Override
    public boolean createReceipt(ReceiptDTO payment) {
        System.out.println(payment+"--------------------------------------------");
        Receipt receipt = receiptDTOMapping.mapDTOtoEntity(payment);
        System.out.println(receipt);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(receipt);
            logger.info("Receipt Payment Inserted successfully in permanent table");
            return true;
        } catch (Exception e) {
            logger.error("hare hare", e);
            return false;
        }
    }

    @Override
    public ReceiptDTO getReceiptById(Long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            Receipt receipt = session.get(Receipt.class, id);
            return receiptDTOMapping.mapEntitytoDTO(receipt);
        } catch (Exception e) {
            logger.error(e.getMessage() + " in permanent table");
            return null;
        }
    }

    @Override
    public List<ReceiptDTO> getAllReceipts() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<Receipt> receipts = session.createQuery("from Receipt", Receipt.class).list();
            return receipts.stream().map(n -> receiptDTOMapping.mapEntitytoDTO(n)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("no Receipt entries found in the permanent table");
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updateReceipt(ReceiptDTO updatedReceipt) {
        Receipt receipt = receiptDTOMapping.mapDTOtoEntity(updatedReceipt);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.update(receipt);
            logger.info("Receipt updated successfully in permanent table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in permanent table");
            return false;
        }
    }

    @Override
    public boolean deleteReceipt(ReceiptDTO receiptDTO) {
        Receipt receipt = receiptDTOMapping.mapDTOtoEntity(receiptDTO);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(receipt);
            logger.info("Receipt deleted successfully from permanent table");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + " in permanent table");
            return false;
        }
    }

    public List<ReceiptDTO> getReceiptList(int batchNumber, int batchSize){

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Receipt", Receipt.class);


        List<Receipt>allocationList = query.getResultList();

        List<ReceiptDTO>receiptDTOList = new ArrayList<>();
        for(Receipt receipt: allocationList){
            System.out.println("---------------->"+receipt);
            receiptDTOList.add(receiptDTOMapping.mapEntitytoDTO(receipt));

        }
        return receiptDTOList;
       // return allocationList.stream().map(allocation -> receiptDTOMapping.mapEntitytoDTO(allocation)).collect(Collectors.toList());
    }

    public Integer getTotalNumberOfRows(){

        Session session = sessionFactory.getCurrentSession();
        System.out.println(session);
        Query query = session.createQuery("Select COUNT(*) from Receipt", Long.class);
        System.out.println(query.getResultList().toString());
        // Execute the query
        return Integer.parseInt(query.getSingleResult().toString());

    }
    @Override
    public List<ReceiptDTO> getallReceiptbyLoanACC(String id, Date fromdate, Date todate) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<Receipt> receipts = session.createQuery("from Receipt where loanAccountNumber=?1 and dateOfReceipt BETWEEN ?2 AND ?3", Receipt.class).setParameter(1,id).setParameter(2,fromdate).setParameter(3,todate).list();
            return receipts.stream().map(n -> receiptDTOMapping.mapEntitytoDTO(n)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("no Receipt entries found in the permanent table");
            return Collections.emptyList();
        }
    }


    public Boolean updateReceiptInBatch(List<ReceiptDTO>receiptDTOList){

        try {
            Session session = sessionFactory.getCurrentSession();

            List<Receipt> receiptList = receiptDTOList.stream().map(receipt -> receiptDTOMapping.mapDTOtoEntity(receipt)).collect(Collectors.toList());

            receiptList.forEach(session::update);

            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }





}
