package com.Demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetailerAddressDTO {
    private Long id;
    private String city;
    private String state;
    private String postCode;
    private String country;
}
