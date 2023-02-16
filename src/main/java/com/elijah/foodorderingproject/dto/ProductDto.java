package com.elijah.foodorderingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private int price;
    private int stars;
    private int typeId;
    private Date createdDate;
    private Date updatedDate;
    private String location;
}
