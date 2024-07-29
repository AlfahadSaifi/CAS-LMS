package org.nucleus.service;

import org.nucleus.dao.CustomerDAO;
import org.nucleus.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/** Developer : Monu Kumar*/
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDao;
    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDao){
        this.customerDao = customerDao;
    }


    // insert customer
    @Override
    public Long create(CustomerDTO customerDto) {
        if(customerDto==null)return null;

        // set customer cif number
        customerDto.setCifNumber(generateCIFNumber());

        return customerDao.create(customerDto);
    }


    @Override
    public CustomerDTO read(Long customerId) {
        if(customerId==null)return null;
        return customerDao.read(customerId);
    }

    @Override
    public boolean update(CustomerDTO customerDto) {
        if(customerDto==null)return false;

        return customerDao.update(customerDto);
    }

    @Override
    public boolean delete(Long customerId) {
        if(customerId==null)return false;
        return customerDao.delete(customerId);
    }

    // search customer by cifNumber or loanAccountNumber or loanApplicationId
    @Override
    @Transactional(readOnly = true)
    public CustomerDTO search(String cifNumber, String loanAccountNumber, String loanApplicationId) {
        CustomerDTO customerDTO=customerDao.search(cifNumber, loanAccountNumber, loanApplicationId);
        return customerDTO!=null ? customerDTO : customerDao.searchFromPermanent(loanAccountNumber, loanApplicationId);
    }

    public CustomerDTO getByCIFNumber(String cifNumber){
        return customerDao.getByCIFNumber(cifNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerWithAddressByCIFNumber(String cifNumber) {
        return customerDao.getCustomerWithAddressByCIFNumber(cifNumber);
    }


    // generate customer cif number
    private String generateCIFNumber() {
        Long sequenceNumber=customerDao.getNextSequenceNumber();
        StringBuilder sequence = new StringBuilder(String.valueOf(sequenceNumber));

        while (sequence.length() < 9) {
            sequence.insert(0, "0");
        }
        return "CIF" + sequence;
    }

}

