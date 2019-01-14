package com.codegym.customerversionx.repository;

import com.codegym.customerversionx.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    Page<Customer> findAllByNameContains(String name, Pageable pageable);
}
