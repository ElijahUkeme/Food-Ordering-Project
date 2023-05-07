package com.elijah.foodorderingproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class CustomerDto {

    private String name;
    private String email;
    private String password;
    private String phone;
}
