package com.elijah.foodorderingproject.repository.token;

import com.elijah.foodorderingproject.model.customer.CustomerModel;
import com.elijah.foodorderingproject.model.token.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken,Long> {

    AuthenticationToken findByCustomerModel(CustomerModel customerModel);
    AuthenticationToken findByToken(String token);
}
