package net.employeemanager.ems_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private String department;
    private Long employeeCount;
    private Double averageSalary;
}
