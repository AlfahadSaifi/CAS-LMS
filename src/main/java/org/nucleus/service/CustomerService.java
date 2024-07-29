package org.nucleus.service;


import org.nucleus.dto.CustomerDTO;

/** Developer : Monu Kumar*/
public interface CustomerService {
    Long create(CustomerDTO customerDto);

    CustomerDTO read(Long customerId);

    boolean update(CustomerDTO customerDto);

    boolean delete(Long customerId);

    CustomerDTO search(String cifNumber, String loanAccountNumber, String loanApplicationId);

    CustomerDTO getByCIFNumber(String cifNumber);

    CustomerDTO getCustomerWithAddressByCIFNumber(String cifNumber);

}
