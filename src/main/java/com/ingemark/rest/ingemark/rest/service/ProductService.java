package com.ingemark.rest.ingemark.rest.service;

import java.util.List;

import com.ingemark.rest.ingemark.rest.domain.Product;
import com.ingemark.rest.ingemark.rest.model.ProductDTO;

public interface ProductService {

	List<ProductDTO> getAllProducts();
	
	ProductDTO findById(Integer id);
	
	ProductDTO getProductByName(String name);
	
	ProductDTO createNewProduct(ProductDTO productDTO);
	
	ProductDTO updatedProduct(Integer id, ProductDTO productDTO);
	
	void deleteProductById(Integer id);

	ProductDTO saveNewProductAndReturnProductDTO(Product product);
}
