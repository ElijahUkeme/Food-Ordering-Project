package com.elijah.foodorderingproject.model.token;

import com.elijah.foodorderingproject.model.customer.CustomerModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date createdDate;
    @ManyToOne(targetEntity = CustomerModel.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "customer_id")
    private CustomerModel customerModel;

    public AuthenticationToken(CustomerModel customerModel){
        this.customerModel = customerModel;
        this.createdDate = new Date();
        this.token = UUID.randomUUID().toString();
    }
}
