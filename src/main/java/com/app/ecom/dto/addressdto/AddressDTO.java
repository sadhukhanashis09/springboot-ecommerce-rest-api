package com.app.ecom.dto.addressdto;

import lombok.Data;

@Data
public class AddressDTO {
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String country;

    // Getters and Setters
}
