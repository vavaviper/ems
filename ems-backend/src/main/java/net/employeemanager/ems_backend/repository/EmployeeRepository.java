package net.employeemanager.ems_backend.repository;

import net.employeemanager.ems_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import net.employeemanager.ems_backend.dto.DepartmentDTO;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT new net.employeemanager.ems_backend.dto.DepartmentDTO(e.department, COUNT(e), AVG(e.salary)) " +
            "FROM Employee e GROUP BY e.department")
    List<DepartmentDTO> findEmployeeStatsByDepartment();
}
