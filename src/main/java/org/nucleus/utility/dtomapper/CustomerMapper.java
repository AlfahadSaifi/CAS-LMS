package org.nucleus.utility.dtomapper;

import org.nucleus.entity.permanent.Address;
import org.nucleus.entity.permanent.Customer;
import org.nucleus.dto.AddressDTO;
import org.nucleus.dto.CustomerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Developer : Monu Kumar*/
public class CustomerMapper {


    public static CustomerDTO toDTO(Customer customer){
        if(customer ==null)return null;

        CustomerDTO customerDto=new CustomerDTO();
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setCifNumber(customer.getCifNumber());
        customerDto.setContactNumber(customer.getContactNumber());
        customerDto.setEmailAddress(customer.getEmailAddress());
        customerDto.setPersonInfoDTO(PersonInfoMapper.toDTO(customer.getPersonInfoTemp()));
        customerDto.setOccupationInfoDTO(OccupationInfoMapper.toDTO(customer.getOccupationInfoTemp()));
        customerDto.setFinancialInfoDTO(FinancialInfoMapper.toDTO(customer.getFinancialInfoTemp()));

        return customerDto;
    }

    public static Customer toEntity(CustomerDTO customerDto){
        if(customerDto==null)return null;
        Customer customer =new Customer();
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setCifNumber(customerDto.getCifNumber());
        customer.setContactNumber(customerDto.getContactNumber());
        customer.setEmailAddress(customerDto.getEmailAddress());
        customer.setPersonInfoTemp(PersonInfoMapper.toEntity(customerDto.getPersonInfoDTO()));
        customer.setOccupationInfoTemp(OccupationInfoMapper.toEntity(customerDto.getOccupationInfoDTO()));
        customer.setFinancialInfoTemp(FinancialInfoMapper.toEntity(customerDto.getFinancialInfoDTO()));

        List<AddressDTO> addressDTOList = customerDto.getAddressDTOS();
        List<Address> addresses =new ArrayList<>();
        if(addressDTOList!=null){
            for(AddressDTO addressDTO: addressDTOList){
                addresses.add(AddressMapper.toEntity(addressDTO));
            }
        }
        customer.setAddresses(addresses);


        return customer;
    }

    public static CustomerDTO toDTOWithAddress(Customer customer){
        if(customer==null)return null;

        CustomerDTO customerDto=new CustomerDTO();
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setCifNumber(customer.getCifNumber());
        customerDto.setContactNumber(customer.getContactNumber());
        customerDto.setEmailAddress(customer.getEmailAddress());
        customerDto.setPersonInfoDTO(PersonInfoMapper.toDTO(customer.getPersonInfoTemp()));
        customerDto.setOccupationInfoDTO(OccupationInfoMapper.toDTO(customer.getOccupationInfoTemp()));
        customerDto.setFinancialInfoDTO(FinancialInfoMapper.toDTO(customer.getFinancialInfoTemp()));

        // set address in dto
        List<Address> addresses=customer.getAddresses();
        if(addresses!=null)
            customerDto.setAddressDTOS(addresses.stream().map(AddressMapper::toDTO).collect(Collectors.toList()));

        return customerDto;
    }
}
