package org.nucleus.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.ReceivablePayableDTO;
import org.nucleus.entity.permanent.LoanAccount;
import org.nucleus.entity.temporary.ReceivablePayableTemp;
import org.nucleus.utility.dtomapper.LoanAccountDTOMapper;
import org.nucleus.utility.dtomapper.ReceivablePayableDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ReceivablePayableTempDaoImpl implements ReceivablePayableTempDao {
    private static final Logger logger = LogManager.getLogger(ReceivablePayableTempDaoImpl.class);

    private final SessionFactory sessionFactory;
    @Autowired
    public ReceivablePayableTempDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    ReceivablePayableDtoMapper dtoMapper = new ReceivablePayableDtoMapper();
    LoanAccountDTOMapper loanAccountDTOMapper = new LoanAccountDTOMapper();
    @Override
    public boolean insertReceivablePayable(ReceivablePayableDTO receivablePayableDto) {
        ReceivablePayableTemp receivablePayableTemp = dtoMapper.mapObjectToReceivablePayableTemp(receivablePayableDto);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(receivablePayableTemp);
            logger.info("receivable payable inserted successfully in temporary table");
            return true;
        } catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    @Override
    public boolean updateReceivablePayable(ReceivablePayableDTO receivablePayableDto) {
        ReceivablePayableTemp receivablePayableTemp = dtoMapper.mapObjectToReceivablePayableTemp(receivablePayableDto);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.update(receivablePayableTemp);
            logger.info("Receivable Payable updated successfully in temporary table");
            return true;
        } catch (Exception e){
            logger.error(e.getMessage()+"exception occurred during updation of receivable payable"+receivablePayableTemp+" in temporary table");
            return false;
        }
    }
    @Override
    public boolean deleteReceivablePayable(ReceivablePayableDTO receivablePayableDto) {
        ReceivablePayableTemp receivablePayableTemp = dtoMapper.mapObjectToReceivablePayableTemp(receivablePayableDto);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(receivablePayableTemp);
            logger.info("receivable payable deleted successfully from temporary table");
            return true;
        } catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    @Override
    public ReceivablePayableDTO getReceivablePayable(Long receivablePayableId) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            ReceivablePayableTemp receivablePayable = session.get(ReceivablePayableTemp.class,receivablePayableId);
            return dtoMapper.mapTempObjectToreceivablePayableDTO(receivablePayable);
        } catch (Exception e){
            logger.error("no receivable payable found for id:"+receivablePayableId+" in temporary table");
            return null;
        }
    }
    @Override
    public List<ReceivablePayableDTO> getAllReceivablePayable() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            List<ReceivablePayableTemp> receivablePayables = session.createQuery("from ReceivablePayableTemp",ReceivablePayableTemp.class).list();
            return receivablePayables.stream()
                    .map(n->dtoMapper.mapTempObjectToreceivablePayableDTO(n))
                    .collect(Collectors.toList());
        } catch (Exception e){
            logger.error("no receivable payable entries found in the temporary table");
            return null;
        }
    }
    @Override
    public List<ReceivablePayableDTO> getReceivablePayableAgainstLoanIdAndDateRange(LoanAccountDTO loanAccount, Date startDate, Date endDate) {

        Session session;
        LoanAccount loanAccount1 = loanAccountDTOMapper.mapDTOToObject(loanAccount);
        try {
            session = sessionFactory.getCurrentSession();
            String hql = "select rp from ReceivablePayableTemp rp  where rp.loanAccount.loanAccountId = :loanAccountId and rp.receivablePayableDueDate >= :startDate and rp.receivablePayableDueDate <= :endDate";
            List<ReceivablePayableTemp> receivablePayables = session.createQuery(hql)
                    .setParameter("loanAccountId",loanAccount.getLoanAccountId())
                    .setParameter("startDate",startDate)
                    .setParameter("endDate",endDate).list();
            return receivablePayables.stream().map(n->dtoMapper.mapTempObjectToreceivablePayableDTO(n)).collect(Collectors.toList());
        } catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
}