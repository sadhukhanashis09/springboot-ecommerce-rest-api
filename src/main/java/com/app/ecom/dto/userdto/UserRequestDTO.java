package com.app.ecom.dto.userdto;

import com.app.ecom.dto.addressdto.AddressDTO;
import lombok.Data;

@Data

public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AddressDTO address;

    // Getters and Setters
}
