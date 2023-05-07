package com.elijah.foodorderingproject.service.customer;

import com.elijah.foodorderingproject.dto.CustomerDto;
import com.elijah.foodorderingproject.dto.CustomerLoginDto;
import com.elijah.foodorderingproject.model.customer.CustomerModel;
import com.elijah.foodorderingproject.model.exception.DataAlreadyExistException;
import com.elijah.foodorderingproject.model.exception.DataNotFoundException;
import com.elijah.foodorderingproject.model.token.AuthenticationToken;
import com.elijah.foodorderingproject.repository.customer.CustomerRepository;
import com.elijah.foodorderingproject.response.ApiResponse;
import com.elijah.foodorderingproject.response.RegistrationResponse;
import com.elijah.foodorderingproject.service.token.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @Transactional
    public ResponseEntity<RegistrationResponse> createCustomer(CustomerDto customerDto) throws DataAlreadyExistException {
        if (Objects.nonNull(customerRepository.findByEmail(customerDto.getEmail()))){
            throw new DataAlreadyExistException("Email Address Already Taken");
        }
        if (Objects.nonNull(customerRepository.findByPhone(customerDto.getPhone()))){
            throw new DataAlreadyExistException("Phone Number Already Taken");
        }
        String encryptedCustomerPassword = customerDto.getPassword();
        try {
            encryptedCustomerPassword = hashPassword(customerDto.getPassword());
        }catch (Exception e) {
            e.printStackTrace();
        }
        CustomerModel customerModel = new CustomerModel();
        customerModel.setEmail(customerDto.getEmail());
        customerModel.setName(customerDto.getName());
        customerModel.setPhone(customerDto.getPhone());
        customerModel.setPassword(encryptedCustomerPassword);
        customerRepository.save(customerModel);
        final AuthenticationToken authenticationToken = new AuthenticationToken(customerModel);
        authenticationTokenService.saveConfirmationToken(authenticationToken);

        return new ResponseEntity<>(new RegistrationResponse("Registration Successful",""+authenticationToken.getToken()), HttpStatus.CREATED);

    }

    public ResponseEntity<RegistrationResponse> signInCustomer(CustomerLoginDto customerLoginDto) throws DataNotFoundException, NoSuchAlgorithmException {
        CustomerModel customerModel = customerRepository.findByEmail(customerLoginDto.getEmail());
        if (Objects.isNull(customerModel)){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        if (!customerModel.getPassword().equals(hashPassword(customerLoginDto.getPassword()))){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        final AuthenticationToken authenticationToken = authenticationTokenService.getTokenByCustomer(customerModel);
        return new ResponseEntity<>(new RegistrationResponse("Login Successfully", authenticationToken.getToken()),HttpStatus.OK);
    }

    public ResponseEntity<CustomerModel> getCustomerDetails(CustomerLoginDto customerLoginDto) throws DataNotFoundException, NoSuchAlgorithmException {
        CustomerModel customerModel = customerRepository.findByEmail(customerLoginDto.getEmail());
        if (Objects.isNull(customerModel)){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        if (customerModel.getPassword().equals(hashPassword(customerLoginDto.getPassword()))){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        return new ResponseEntity<>(customerModel,HttpStatus.OK);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toLowerCase().substring(0,12);
        return hash;
    }
}
