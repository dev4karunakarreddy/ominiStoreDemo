package com.Demo.repository;

import com.Demo.entity.EmployeeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDataRepository extends JpaRepository<EmployeeData,Long> {
}
