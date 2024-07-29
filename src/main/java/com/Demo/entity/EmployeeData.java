package com.Demo.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EmployeeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne
    private RetailerAddress retailerAddress;
    private String startDate;
    private String finishDate;
}

