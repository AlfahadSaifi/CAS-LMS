/*
 *Author: Rakesh Kumar
 *
 */

package org.nucleus.dao;
import org.apache.logging.log4j.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.entity.permanent.LoanAccount;
import org.nucleus.utility.dtomapper.LoanAccountDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class LoanAccountDAOImpl implements LoanAccountDAO{
    private final SessionFactory factory;
    private final LoanAccountDTOMapper loanAccountDTOMapper;
    @Autowired
    public LoanAccountDAOImpl(SessionFactory factory,LoanAccountDTOMapper loanAccountDTOMapper) {
        this.factory = factory;
        this.loanAccountDTOMapper=loanAccountDTOMapper;
    }

    @Override
    public boolean insertLoanAccount(LoanAccountDTO loanAccountDTO) {
        try{
            LoanAccount loanAccount=loanAccountDTOMapper.mapDTOToObject(loanAccountDTO);
            factory.getCurrentSession().save(loanAccount);
            return true;
        }
        catch (Exception e){
            LogManager.getLogger(LoanAccountDAO.class).error("Error while creating loan application ",e);
        }
        return false;
    }
    @Override
    public LoanAccountDTO getByLoanAccountId(Long loanAccountId) {
        try{
            return loanAccountDTOMapper.mapObjectToDTO(factory.getCurrentSession().get(LoanAccount.class, loanAccountId));
        }
        catch (Exception e){
            LogManager.getLogger(LoanAccountDAO.class).error("Error while reading by accountId ", e);
        }
        return null;
    }

    @Override
    public LoanAccountDTO getByAccountNumber(String loanAccountNumber) {
        try{
//            LoanAccount loanAccount= (LoanAccount) factory.getCurrentSession().createSQLQuery("select * from LOAN_ACCOUNT_TBL_BATCH_6 where loanAccountNumber= :loanAccountNumber")
//                    .setParameter("loanAccountNumber", loanAccountNumber)
//                    .addEntity(LoanAccount.class).list().get(0);
            LoanAccount loanAccount = (LoanAccount) factory.getCurrentSession()
                    .createQuery("from LoanAccount where loanAccountNumber =: loanAccountNumber")
                    .setParameter("loanAccountNumber", loanAccountNumber)
                    .getSingleResult();
            return loanAccountDTOMapper.mapObjectToDTO(loanAccount);
        }
        catch (Exception e){
            LogManager.getLogger(LoanAccountDAO.class).error("Error while reading by Loan Account ", e);
        }
        return null;
    }

    @Override
    public boolean updateLoanAccount(LoanAccountDTO loanAccount) {
        try{
            factory.getCurrentSession().merge(loanAccountDTOMapper.mapDTOToObject(loanAccount));
            return true;
        }
        catch (Exception e){
            LogManager.getLogger(LoanAccountDAO.class).error("Error while updating loanAccount ", e);
        }
        return false;
    }


    @Override
    public boolean delete(String accountNumber) {
        try{
            LoanAccount loanAccount=factory.getCurrentSession().load(LoanAccount.class, accountNumber);
            factory.getCurrentSession().delete(loanAccount);
            return true;
        }
        catch (Exception e){
            LogManager.getLogger(LoanAccountDAO.class).error("Error while deleting loan Account ", e);
        }
        return false;
    }

    @Override
    public List<LoanAccountDTO> getAllLoanAccounts() {
        try{
            return factory.getCurrentSession().createQuery("from LoanAccount", LoanAccount.class)
                    .list()
                    .stream()
                    .map(loanAccountDTOMapper::mapObjectToDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            LogManager.getLogger(LoanAccountDAO.class).error("Error while reading all loan Account : ", e);
        }
        return new ArrayList<>();
    }
    @Override
    public boolean updateAccountStatus(List<LoanAccountDTO> loanAccountDTO) {
        try {
            factory.getCurrentSession().update(loanAccountDTO);
            return true;
        }
        catch (HibernateException e){
            LogManager.getLogger(LoanAccountDAO.class).error("Error while updating status of loan account: "+e);
        }
        return false;
    }

    @Override
    public Long getRowCount() {
        return (Long) factory.getCurrentSession().createQuery("select count(*) from LoanAccount").getSingleResult();
    }
    @Override
    public List<LoanAccountDTO> getLoanAccountsInBatch(int offset, int batchSize) {

        try {
            Query queue = factory.getCurrentSession().createQuery("FROM LoanAccount la WHERE la.loanStatus = 'ACTIVE' AND ABS(la.loanAmountDisbursed - la.loanAmountPaidByCustomer) <= 36", LoanAccount.class)
                    .setFirstResult(offset*batchSize)
                    .setMaxResults(batchSize);
            return (List<LoanAccountDTO>) queue.getResultList().stream().map(loanAccount->loanAccountDTOMapper.mapObjectToDTO((LoanAccount) loanAccount)).collect(Collectors.toList());
        }
        catch (HibernateException e){
            LogManager.getLogger().error(e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean updateBatch(List<LoanAccountDTO> chunk, int batchSize) {
        LoanAccountDTOMapper loanAccountDTOMapper =new LoanAccountDTOMapper();
        try {
            Session session = factory.getCurrentSession();
            chunk.forEach((loanAccountDTO)->session.update(loanAccountDTOMapper.mapDTOToObject(loanAccountDTO)));
            return true;
        }
        catch (HibernateException e){
            LogManager.getLogger(LoanAccountDAO.class).error(e.getMessage());
        }
        return false;
    }

}
