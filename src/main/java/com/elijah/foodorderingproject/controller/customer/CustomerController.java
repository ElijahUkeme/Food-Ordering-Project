package com.elijah.foodorderingproject.controller.customer;

import com.elijah.foodorderingproject.dto.CustomerDto;
import com.elijah.foodorderingproject.dto.CustomerLoginDto;
import com.elijah.foodorderingproject.model.customer.CustomerModel;
import com.elijah.foodorderingproject.model.exception.DataAlreadyExistException;
import com.elijah.foodorderingproject.model.exception.DataNotFoundException;
import com.elijah.foodorderingproject.response.ApiResponse;
import com.elijah.foodorderingproject.response.RegistrationResponse;
import com.elijah.foodorderingproject.service.customer.CustomerService;
import com.elijah.foodorderingproject.service.token.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @PostMapping("/customer/create")
    public ResponseEntity<RegistrationResponse> createCustomer(@RequestBody CustomerDto customerDto) throws DataAlreadyExistException {
        return customerService.createCustomer(customerDto);
    }
    @GetMapping("/customer/getByToken/{token}")
    public ResponseEntity<CustomerModel> getCustomerByToken(@PathVariable("token") String token) throws DataNotFoundException {
        return new ResponseEntity<>(authenticationTokenService.getCustomerByToken(token),HttpStatus.OK);
    }
    @PostMapping("/customer/login")
    public ResponseEntity<RegistrationResponse> signInCustomer(@RequestBody CustomerLoginDto customerLoginDto) throws DataNotFoundException, NoSuchAlgorithmException {
        return customerService.signInCustomer(customerLoginDto);
    }

    @GetMapping("/customer/info")
    public ResponseEntity<CustomerModel> getUserInfo(@RequestBody CustomerLoginDto customerLoginDto) throws DataNotFoundException, NoSuchAlgorithmException {
        return customerService.getCustomerDetails(customerLoginDto);
    }

}
