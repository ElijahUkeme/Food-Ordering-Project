package com.elijah.foodorderingproject.repository.customer;

import com.elijah.foodorderingproject.model.customer.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel,Long> {
    CustomerModel findByEmail(String email);
    CustomerModel findByPhone(String phone);
}
