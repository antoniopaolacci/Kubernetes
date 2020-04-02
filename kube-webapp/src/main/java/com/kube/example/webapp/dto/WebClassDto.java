package com.kube.example.webapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebClassDto {
	
    private long classId;
    private String teacherName;
    private long teacherId;
    private String courseName;
    private long courseId;
    private int numberOfStudents;
    private int year;
    
} //end class
