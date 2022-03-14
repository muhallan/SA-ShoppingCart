package com.customerservice.service;

import com.customerservice.data.CustomerRepository;
import com.customerservice.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomerService {

    @Autowired
    @Qualifier("customerRepository")
    CustomerRepository repository;

    public void addCustomer(Customer customer) {
        repository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        repository.save(customer);
    }

    public Customer findCustomerById(String cId) {
        return repository.findBycId(cId);
    }

    public void deleteCustomer(String cId) {
        repository.deleteById(cId);
    }

    public Collection<Customer> getAllCustomer() {
        return repository.findAll();
    }
}
