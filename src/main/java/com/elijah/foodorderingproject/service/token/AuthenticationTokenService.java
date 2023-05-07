package com.elijah.foodorderingproject.service.token;

import com.elijah.foodorderingproject.model.customer.CustomerModel;
import com.elijah.foodorderingproject.model.exception.DataNotFoundException;
import com.elijah.foodorderingproject.model.token.AuthenticationToken;
import com.elijah.foodorderingproject.repository.token.AuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationTokenService {

    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken){
        authenticationTokenRepository.save(authenticationToken);
    }
    public AuthenticationToken getTokenByCustomer(CustomerModel customerModel){
        return authenticationTokenRepository.findByCustomerModel(customerModel);
    }

    public CustomerModel getCustomerByToken(String token) throws DataNotFoundException {
        AuthenticationToken authenticationToken = authenticationTokenRepository.findByToken(token);
        if (Objects.isNull(authenticationToken)){
            throw new DataNotFoundException("Token Not Found");
        }
        return authenticationToken.getCustomerModel();
    }
}
