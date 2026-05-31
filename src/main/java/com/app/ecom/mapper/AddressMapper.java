package com.app.ecom.mapper;

import com.app.ecom.dto.addressdto.AddressDTO;
import com.app.ecom.model.Address;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address toAddressModel(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPincode(addressDTO.getPincode());
        address.setCountry(addressDTO.getCountry());
        return address;
    }

    public AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            return null;
        }
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setPincode(address.getPincode());
        addressDTO.setCountry(address.getCountry());
        return addressDTO;
    }
}
