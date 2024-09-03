package com.smartcompany.dto;

import com.smartcompany.entity.Employee;
import com.smartcompany.entity.Level;
import com.smartcompany.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends Employee {
    private List<Level> level;
    private List<Position> position;

}
