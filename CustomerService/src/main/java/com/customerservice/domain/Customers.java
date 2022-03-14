package com.customerservice.domain;

import java.util.Collection;
import java.util.List;

public class Customers {
    private Collection<Customer> customers;

    public Customers(Collection<Customer> customers)
    {
        this.customers = customers;
    }

    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
