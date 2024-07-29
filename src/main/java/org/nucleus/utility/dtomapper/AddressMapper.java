package org.nucleus.utility.dtomapper;

import org.nucleus.dto.AddressDTO;
import org.nucleus.entity.permanent.Address;

/** Developer : Monu Kumar*/
public class AddressMapper {

    public static Address toEntity(AddressDTO addressDTO){
        Address address =new Address();

        address.setAddressId(addressDTO.getAddressId());
        address.setAddressType(addressDTO.getAddressType());
        address.setFullAddress(addressDTO.getFullAddress());
        address.setRegion(addressDTO.getRegion());

        address.setCityName((addressDTO.getCityName()));
        address.setCountryName(addressDTO.getCountryName());
        address.setStateName((addressDTO.getStateName()));

        address.setDistrict(addressDTO.getDistrict());
        address.setRegion(addressDTO.getRegion());
        address.setFlatNumber(addressDTO.getFlatNumber());
        address.setPinCode(addressDTO.getPinCode());

        return address;
    }

    public static AddressDTO toDTO(Address address){
        AddressDTO addressDTO=new AddressDTO();

        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setAddressType(address.getAddressType());
        addressDTO.setFullAddress(address.getFullAddress());
        addressDTO.setRegion(address.getRegion());

        addressDTO.setCityName((address.getCityName()));
        addressDTO.setCountryName(address.getCountryName());
        addressDTO.setStateName((address.getStateName()));

        addressDTO.setDistrict(address.getDistrict());
        addressDTO.setRegion(address.getRegion());
        addressDTO.setFlatNumber(address.getFlatNumber());
        addressDTO.setPinCode(address.getPinCode());

        return addressDTO;
    }
}
