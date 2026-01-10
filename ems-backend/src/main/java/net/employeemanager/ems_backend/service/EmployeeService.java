package net.employeemanager.ems_backend.service;

import java.util.List;
import net.employeemanager.ems_backend.dto.EmployeeDto;
import net.employeemanager.ems_backend.dto.DepartmentDTO;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);

    void deleteEmployee(Long employeeId);

    List<DepartmentDTO> getEmployeeStatsByDepartment();
}
