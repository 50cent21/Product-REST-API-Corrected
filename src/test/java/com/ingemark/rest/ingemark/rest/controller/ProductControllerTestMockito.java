package com.ingemark.rest.ingemark.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ingemark.rest.ingemark.rest.controller.v1.ProductController;
import com.ingemark.rest.ingemark.rest.domain.Product;
import com.ingemark.rest.ingemark.rest.model.ProductDTO;
import com.ingemark.rest.ingemark.rest.service.ProductService;


class ProductControllerTestMockito extends AbstractRestControllerTest{
	
	public static final String NAME = "Toothbrush";
	
	@Mock
	ProductService productService;
	
	@InjectMocks
	ProductController productController;
	
	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new RestResponseEntityException()).build();
		
	}

	@Test
	void testGetAllProducts() throws Exception {
		
		ProductDTO product1 = new ProductDTO();
		product1.setName("Toothbrush");
		
		ProductDTO product2 = new ProductDTO();
		product2.setName("Jacket");
		
		when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));
		
		mockMvc.perform(get(ProductController.BASE_URL + "/all")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$[0].name", equalTo("Toothbrush")))
				.andExpect(jsonPath("$[1].name", equalTo("Jacket")));
		
	}
	
	
	@Test
	void testGetProductById() throws Exception{	
			
		ProductDTO product = new ProductDTO();;
		product.setName("Toothbrush");
		
		when(productService.findById(anyInt())).thenReturn(product);
		
		mockMvc.perform(get(ProductController.BASE_URL + "/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.name", equalTo("Toothbrush")));
               
	}

	@Test
	void testCreateProduct() throws Exception{
		
		ProductDTO product = new ProductDTO();
		product.setName("Toothbrush");
		product.setDescription("For Brushing Teeth");
		
		when(productService.createNewProduct(any(ProductDTO.class))).thenReturn(product);
		
		mockMvc.perform(post(ProductController.BASE_URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(product)))
	        .andExpect(status().isCreated())
	        .andExpect(jsonPath("$.name", equalTo("Toothbrush")))
	        .andExpect(jsonPath("$.description", equalTo("For Brushing Teeth")));
		
	}

	@Test
	void testUpdateProductById() throws Exception{
		
		ProductDTO product = new ProductDTO();
		product.setName("Toothbrush");
		product.setDescription("For Brushing Teeth");
		
		when(productService.updatedProduct(anyInt(), any(ProductDTO.class))).thenReturn(product);
		
		mockMvc.perform(put(ProductController.BASE_URL + "/1")
			    .accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(product)))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.name", equalTo("Toothbrush")))
	        .andExpect(jsonPath("$.description", equalTo("For Brushing Teeth")));
		
	}

	@Test
	void testDeleteProductById() throws Exception{
		
		mockMvc.perform(delete(ProductController.BASE_URL + "/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk());
		
		verify(productService).deleteProductById(anyInt());
	}
}
