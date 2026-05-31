package com.app.ecom.mapper;

import com.app.ecom.dto.userdto.UserRequestDTO;
import com.app.ecom.dto.userdto.UserResponseDTO;
import com.app.ecom.mapper.AddressMapper;
import com.app.ecom.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final AddressMapper addressMapper;

    public UserMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    // ✅ CREATE mapping
    public User toUserModel(UserRequestDTO dto) {
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        //user.setRole(dto.getRole());

        user.setAddress(addressMapper.toAddressModel(dto.getAddress()));

        return user;
    }


    public void updateUserFromDTO(UserRequestDTO dto, User user) {

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(addressMapper.toAddressModel(dto.getAddress()));

    }

    // ✅ RESPONSE mapping
    public UserResponseDTO toUserResponseDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole().name());

        dto.setAddress(addressMapper.toAddressDTO(user.getAddress()));

        return dto;
    }
}