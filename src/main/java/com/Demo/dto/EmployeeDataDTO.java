package com.Demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDataDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String title;
    private String emailAddress;
    private String managersEmail;
    private String CDSID;
    private String DMSID;
    private String retailer;
    private RetailerAddressDTO retailerAddressDTO;
    private String startDate;
    private String finishDate;

}
