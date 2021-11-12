package com.example.elasticsearchdemo.repository;

import com.example.elasticsearchdemo.model.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
    List<Customer> findAllByNameIsLike(String name);

    List<Customer> findAllByNameIsLikeOrAge(String name, int age);
}
