package org.nucleus.dao;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.CustomerDTO;
import org.nucleus.entity.permanent.Customer;
import org.nucleus.utility.dtomapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
    private final SessionFactory factory;
    @Autowired
    public CustomerDAOImpl(SessionFactory factory)
    {
        this.factory=factory;
    }

    @Override
    public Long create(CustomerDTO customerDTO) {
        if(customerDTO ==null)return null;

        // map dto to entity
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Session session=factory.getCurrentSession();
        return (Long)session.save(customer);
    }

    @Override
    public CustomerDTO read(Long customerId) {
        if(customerId==null)return null;

        Session session=factory.getCurrentSession();
        Customer customer = session.get(Customer.class, customerId);
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public boolean update(CustomerDTO customerDTO) {
        if(customerDTO ==null)return false;

        // map dto to entity
        Customer customer=CustomerMapper.toEntity(customerDTO);
        Session session=factory.getCurrentSession();
        session.saveOrUpdate(customer);
        return true;
    }

    @Override
    public boolean delete(Long customerId) {
        if(customerId==null)return false;

        Session session=factory.getCurrentSession();
        Customer customer =session.get(Customer.class, customerId);
        session.delete(customer);
        return true;
    }

    // search customer
    @Override
    public CustomerDTO search(String cifNumber, String loanAccountNumber, String loanApplicationId){
        if(cifNumber==null && loanAccountNumber==null && loanApplicationId==null)return null;

        try{
            Session session=factory.getCurrentSession();
            String hql = "SELECT c FROM org.nucleus.entity.permanent.Customer c " +
                    "LEFT JOIN c.loanApplications la " +
                    "WHERE c.cifNumber = :cifNumber OR " +
                    "la.loanAccountNumber = :loanAccountNumber OR " +
                    "la.loanApplicationId = :loanApplicationId";

            Customer customer= session.createQuery(hql, Customer.class)
                    .setParameter("cifNumber", cifNumber)
                    .setParameter("loanAccountNumber", loanAccountNumber)
                    .setParameter("loanApplicationId", loanApplicationId)
                    .getSingleResult();

            return CustomerMapper.toDTO(customer);
        }
        catch (Exception e){
            return null;
        }
    }

    // search customer based on to LAN or loanApplicationId those are having in permanent table
    @Override
    public CustomerDTO searchFromPermanent(String loanAccountNumber, String loanApplicationId) throws RuntimeException{
        if(loanAccountNumber==null && loanApplicationId==null)return null;

        Session session=factory.getCurrentSession();
        String hql = "SELECT lap.customer FROM LoanApplication lap " +
                "WHERE lap.loanAccountNumber = :loanAccountNumber OR " +
                "lap.loanApplicationId = :loanApplicationId";
        Customer customer = session.createQuery(hql, Customer.class)
                .setParameter("loanAccountNumber", loanAccountNumber)
                .setParameter("loanApplicationId", loanApplicationId)
                .getSingleResult();

        return CustomerMapper.toDTO(customer);
    }


    @Override
    public CustomerDTO getByCIFNumber(String cifNumber) {
        if(cifNumber==null)return null;

        Session session=factory.getCurrentSession();
        Customer customer = session.createQuery("from Customer where cifNumber =:cifNumber", Customer.class)
                .setParameter("cifNumber", cifNumber).uniqueResult();

        return CustomerMapper.toDTO(customer);
    }

    @Override
    public Long getNextSequenceNumber() {
        Session session=factory.getCurrentSession();
        return ((Number) session.createNativeQuery("SELECT loan_PK_seq.NEXTVAL FROM DUAL").uniqueResult()).longValue();
    }

    @Override
    public CustomerDTO getCustomerWithAddressByCIFNumber(String cifNumber) {
        if(cifNumber==null)return null;

        Session session=factory.getCurrentSession();
        Customer customer = session.createQuery("from Customer where cifNumber = :cifNumber", Customer.class)
                .setParameter("cifNumber", cifNumber)
                .uniqueResult();

        Hibernate.initialize(customer.getAddresses());
        return CustomerMapper.toDTOWithAddress(customer);
    }

}
