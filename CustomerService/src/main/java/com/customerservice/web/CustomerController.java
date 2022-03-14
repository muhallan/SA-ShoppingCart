package com.customerservice.web;

import com.customerservice.domain.Customer;
import com.customerservice.domain.Customers;
import com.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService service;

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        service.addCustomer(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        service.updateCustomer(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable String customerId) {
        Customer customer = service.findCustomerById(customerId);
        if(customer == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Customer with id = " + customerId + " is not found!"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @GetMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String customerId) {
        service.deleteCustomer(customerId);
        return new ResponseEntity<String>("CustomerId = " + customerId + " is deleted!", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<Customers>(new Customers(service.getAllCustomer()), HttpStatus.OK);
    }
}
