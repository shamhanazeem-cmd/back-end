package com.edu.Institiute.dto;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SpecializationDto {
    private Integer id;
    private String name;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
}
