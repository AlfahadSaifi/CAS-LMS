package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.entity.temporary.LoanApplicationTemp;
import org.nucleus.utility.dtomapper.LoanApplicationTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class LoanApplicationTempDAOImpl implements LoanApplicationTempDAO {

    private final LoanApplicationTempMapper loanApplicationTempMapper;

    private final SessionFactory factory;
    @Autowired
    public LoanApplicationTempDAOImpl(SessionFactory factory, LoanApplicationTempMapper loanApplicationTempMapper){
        this.factory=factory;
        this.loanApplicationTempMapper=loanApplicationTempMapper;
    }

    @Override
    public Long create(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO ==null)return null;

        // map dto to temp entity
        LoanApplicationTemp loanApplicationTemp = loanApplicationTempMapper.toEntity(loanApplicationDTO);
        try{
            Session session=factory.getCurrentSession();
            return (Long)session.save(loanApplicationTemp);
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception occurred while creating loanApplication: ", e);
        }
        return null;
    }

    @Override
    public LoanApplicationDTO read(Long loanId) {
        if(loanId==null)return null;
        try{
            Session session=factory.getCurrentSession();
            LoanApplicationTemp loanApplicationTemp = session.get(LoanApplicationTemp.class, loanId);

            // map entity to dto
            return loanApplicationTempMapper.toDTO(loanApplicationTemp);
        }
        catch (Exception e){
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception occurred while reading loanApplication: ", e);
        }
        return null;
    }

    @Override
    public LoanApplicationDTO readByLoanApplicationId(String loanApplicationId) {
        if(loanApplicationId==null)return null;
        try{
            Session session=factory.getCurrentSession();
            LoanApplicationTemp loanApplicationTemp =
                    session.createQuery("from LoanApplicationTemp where loanApplicationId=:loanApplicationId"
                            , LoanApplicationTemp.class).setParameter("loanApplicationId", loanApplicationId)
                            .uniqueResult();

            // map entity to dto
            return loanApplicationTempMapper.toDTO(loanApplicationTemp);
        }
        catch (Exception e){
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception occurred while reading loanApplication: ", e);
        }
        return null;
    }


    @Override
    public boolean update(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO ==null)return false;

        // map dto to entity
        LoanApplicationTemp loanApplicationTemp=loanApplicationTempMapper.toEntity(loanApplicationDTO);
        try{
            Session session=factory.getCurrentSession();
            session.update(loanApplicationTemp);
            return true;
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception occurred while updating loanApplication: ", e);
        }
        return false;
    }

    @Override
    public boolean merge(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO ==null)return false;

        // map dto to entity
        LoanApplicationTemp loanApplicationTemp=loanApplicationTempMapper.toEntity(loanApplicationDTO);
        try{
            Session session=factory.getCurrentSession();
            session.merge(loanApplicationTemp);
            return true;
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception occurred while updating loanApplication: ",e);
        }
        return false;
    }

    @Override
    public boolean delete(String loanApplicationId) {
        if(loanApplicationId ==null)return false;

        try{
            Session session=factory.getCurrentSession();
            String hql="delete from LoanApplicationTemp where loanApplicationId=:loanApplicationId";
            session.createQuery(hql).setParameter("loanApplicationId", loanApplicationId).executeUpdate();
            return true;
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception occurred while deleting loanApplication: ", e);
        }
        return false;
    }


    @Override
    public List<LoanApplicationDTO> readingAll() {
        try{
            Session session=factory.getCurrentSession();
            List<LoanApplicationTemp> loanApplicationTemps = session.createQuery("from LoanApplicationTemp", LoanApplicationTemp.class)
                    .list();

            // map entities to dto's
            return loanApplicationTemps.stream()
                    .map(loanApplicationTempMapper::toDTO)
                    .collect(Collectors.toList());
        }catch (Exception e){
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception occurred while readingAll loanApplication: ", e);
        }
        return Collections.emptyList();
    }

    public Long getNextSequenceNumber() {
        try{
            Session session=factory.getCurrentSession();
            return ((Number) session.createNativeQuery("SELECT loan_PK_seq.NEXTVAL FROM DUAL").uniqueResult()).longValue();
        }catch(Exception e) {
            LogManager.getLogger(LoanApplicationTempDAOImpl.class).error("Exception while reading Next Sequence Number " , e);
        }
        return null;
    }
}
