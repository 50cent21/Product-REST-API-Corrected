package com.ingemark.rest.ingemark.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingemark.rest.ingemark.rest.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	Product findByName(String name);

}
