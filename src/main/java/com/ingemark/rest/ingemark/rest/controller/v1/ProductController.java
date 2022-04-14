package com.ingemark.rest.ingemark.rest.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ingemark.rest.ingemark.rest.domain.Product;
import com.ingemark.rest.ingemark.rest.exception.NotFoundException;
import com.ingemark.rest.ingemark.rest.model.ProductDTO;
import com.ingemark.rest.ingemark.rest.model.ProductListDTO;
import com.ingemark.rest.ingemark.rest.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {

	public static final String BASE_URL = "/api/v1/product";
	
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ProductListDTO getAllProducts() {
		
		log.info("Inside Product Controller! Method: getAllProducts");
		
		return new ProductListDTO(productService.getAllProducts());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDTO getProductById(@PathVariable Integer id) {
		
		log.info("Inside Product Controller! Method getProductById with ID value: " + id);
		
		return productService.findById(id);
	}
	
	@GetMapping("/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDTO getProductByName(@PathVariable String name) {
		
		log.info("Inside Product Controller! Method: getProductByName with String value: " + name);
		//find a product based on its field name
		ProductDTO product = productService.getProductByName(name);
		
		//if product is not found, throws a message "Resource Not Found!"
		if(product != null) {
			log.info("Product found with the matching name value");
			return product;
		} else {
			log.error("Product not found");
			throw new NotFoundException();
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
	
		log.info("Inside Product Controller! Method: createProduct");
		
		return productService.createNewProduct(productDTO);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDTO updateProductById(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO) {
		
		log.info("Inside Product Controller! Method updateProductById with ID value: " + id);
		
		return productService.updatedProduct(id, productDTO);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteProductById(@PathVariable Integer id) {
		
		log.info("Inside Product Controller! Method: deleteProductById with ID value: " + id);
		
		productService.deleteProductById(id);
		
		return "Product successfully deleted!";
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		
		log.info("Inside Product Controller! Method: handleValidationExceptions");
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		log.info("Handling Validation Exceptions");
		
		return errors;
	}
}
