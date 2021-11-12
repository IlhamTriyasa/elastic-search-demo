package com.example.elasticsearchdemo.service;

import com.example.elasticsearchdemo.model.Customer;
import com.example.elasticsearchdemo.repository.CustomerRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.query.ElasticsearchStringQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@Service
public class CustomerService {
    @Autowired
    private ElasticsearchOperations operations;
    @Autowired
    private ElasticsearchOperations template;
    @Autowired
    CustomerRepository repository;

    public List<Customer> searchByName(String name){
        List<Customer> customers = repository.findAllByNameIsLike(name);
        return customers;
    }

    public List<Customer> searchByNameAndAge(String name, int age){
        List<Customer> customers = repository.findAllByNameIsLikeOrAge(name, age);
        return customers;
    }

    public List<MultiGetItem<Customer>> getCustomerSearchData(String input) {
        String search = ".*" + input + ".*";
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(input)
                                    .field("name")
                                    .field("email")
                                    .field("age")
                                    .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                                    .build();
        List<MultiGetItem<Customer>> customers = template.multiGet(searchQuery, Customer.class);
        return customers;
    }
}
