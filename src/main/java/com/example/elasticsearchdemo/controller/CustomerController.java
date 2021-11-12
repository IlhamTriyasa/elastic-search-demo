package com.example.elasticsearchdemo.controller;

import com.example.elasticsearchdemo.model.Customer;
import com.example.elasticsearchdemo.repository.CustomerRepository;
import com.example.elasticsearchdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService service;
    @Autowired
    private CustomerRepository customerELKrepository;

    @GetMapping("customer/find-byname/{name}")
    public List<Customer> findByName(@PathVariable String name){
        return  service.searchByName(name);
    }

    @GetMapping("customer/findby-name-age/{name}/{age}")
    public List<Customer> findByNameAndAge(@PathVariable String name, @PathVariable int age){
        return  service.searchByNameAndAge(name, age);
    }

    @GetMapping("customer")
    public Iterable<Customer> findAll(){
        return  customerELKrepository.findAll();
    }

    @PostMapping("customer/save")
    public boolean saveCustomer(@RequestBody List<Customer> customers) {
        boolean result = false;
        try {
            customerELKrepository.saveAll(customers);
            result = true;
        } catch ( Exception ex) {
            //
        }
        return result;
    }
}
