package org.nucleus.dao;


import org.nucleus.dto.CustomerDTO;

public interface CustomerDAO {
    Long create(CustomerDTO customerDTO);
    CustomerDTO read(Long customerId);
    boolean update(CustomerDTO customerDTO);
    boolean delete(Long customerId);
    CustomerDTO search(String cifNumber, String loanAccountNumber, String loanApplicationId);
    CustomerDTO searchFromPermanent(String loanAccountNumber, String loanApplicationId);
    CustomerDTO getByCIFNumber(String cifNumber);
    Long getNextSequenceNumber();
    CustomerDTO getCustomerWithAddressByCIFNumber(String cifNumber);
}
