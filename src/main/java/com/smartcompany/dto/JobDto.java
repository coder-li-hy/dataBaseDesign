package com.smartcompany.dto;

import com.smartcompany.entity.Department;
import com.smartcompany.entity.Level;
import com.smartcompany.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto extends Position {
    private Level level;//  具体职务和等级
    private Department department;// 具体部门
}
