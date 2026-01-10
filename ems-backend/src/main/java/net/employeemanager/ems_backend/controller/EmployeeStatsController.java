package net.employeemanager.ems_backend.controller;

import lombok.AllArgsConstructor;
import net.employeemanager.ems_backend.dto.DepartmentDTO;
import net.employeemanager.ems_backend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeStatsController {

    private EmployeeService employeeService;

    @Operation(summary = "Employee stats by department", description = "Returns department name, employee count, and average salary for each department")
    @GetMapping("/stats")
    public ResponseEntity<List<DepartmentDTO>> getEmployeeStats() {
        List<DepartmentDTO> stats = employeeService.getEmployeeStatsByDepartment();
        return ResponseEntity.ok(stats);
    }
}
