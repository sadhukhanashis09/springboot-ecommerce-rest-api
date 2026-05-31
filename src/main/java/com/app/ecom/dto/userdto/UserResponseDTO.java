package com.app.ecom.dto.userdto;

import com.app.ecom.dto.addressdto.AddressDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "id",
        "firstName",
        "lastName",
        "email",
        "phoneNumber",
        "role",
        "address"
})
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;

    private AddressDTO address;

    // Getters and Setters
}
