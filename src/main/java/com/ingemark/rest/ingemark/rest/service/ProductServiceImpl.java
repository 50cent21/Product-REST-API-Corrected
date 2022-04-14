package com.ingemark.rest.ingemark.rest.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ingemark.rest.ingemark.rest.domain.EuroValue;
import com.ingemark.rest.ingemark.rest.domain.Product;
import com.ingemark.rest.ingemark.rest.exception.NotFoundException;
import com.ingemark.rest.ingemark.rest.mapper.ProductMapper;
import com.ingemark.rest.ingemark.rest.model.ProductDTO;
import com.ingemark.rest.ingemark.rest.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	public static final String HNB_EURO_VALUE_URL =  "https://api.hnb.hr/tecajn/v2?valuta=EUR";
	
	private final ProductMapper productMapper;
	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
		this.productMapper = productMapper;
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		
		return productRepository.findAll()
				.stream()
				.map(productMapper::productToProductDTO)
				.collect(Collectors.toList());	
	}

	@Override
	public ProductDTO findById(Integer id) {
		
		ProductDTO product = productRepository.findById(id)
				.map(productMapper::productToProductDTO)
				.orElseThrow(NotFoundException::new);
		
		log.info("Inside ProductServiceImpl! Product found : " + product.toString());
		
		return product;
	}
	
	@Override
	public ProductDTO getProductByName(String name) {
		
		ProductDTO product = productMapper.productToProductDTO(productRepository.findByName(name));
		
		log.info("Inside ProductServiceImpl! Product found: " +  product.toString());
		
		return product;
	}
	
	@Override 
	public ProductDTO createNewProduct(ProductDTO productDTO) {
		
		return saveNewProductAndReturnProductDTO(productMapper.productDTOToProduct(productDTO));
	}
	

	@Override
	public ProductDTO saveNewProductAndReturnProductDTO(Product product) {
		
		log.info("Inside ProductServiceImpl! Method: saveNewProductAndReturnProductDTO");
		//set unique 10digit code
		product.setCode(generateUniqueTenDigitCode());
		log.info("Unique code of the product: " + product.getCode());
		
		//convert price from HRK to EURO using HNB EURO Value
		product.setPriceEur(product.getPriceHrk().divide(euroValueRate(), 2, RoundingMode.HALF_UP));
		log.info("Euro Value Rate: " + euroValueRate());
		log.info("Price of the product in Euros: " + product.getPriceEur());
		
		//set product availability upon creation to true
		product.setAvailable(true);
		
		log.info("Product that is being saved: " + product.toString());
		
		productRepository.save(product);
		
		return productMapper.productToProductDTO(product);
	}

	@Override
	public ProductDTO updatedProduct(Integer id, ProductDTO productDTO) {
		
		log.info("Inside ProductServiceImpl! Method: updateProduct");
		
		return productRepository.findById(id).map(product -> {
			
			if(productDTO.getName() != null) {
				product.setName(productDTO.getName());
				log.info("Updated product name " + productDTO.getName());
			}
			
			//convert price from HRK to EURO using HNB EURO Value
			if(productDTO.getPriceHrk() != null) {
				product.setPriceHrk(productDTO.getPriceHrk());
				log.info("Updated product HRK price: " + productDTO.getPriceHrk());
				
				product.setPriceEur(productDTO.getPriceHrk().divide(euroValueRate(), 2, RoundingMode.HALF_UP));
				log.info("Updated product EUR price: " + productDTO.getPriceEur());
			}
			
			if(productDTO.getDescription() != null) {
				product.setDescription(productDTO.getDescription());
				log.info("Updated product description: " + productDTO.getDescription());
			}
			
			if(productDTO.isAvailable() != true) {
				product.setAvailable(true);
			}
			
			productRepository.save(product);
			
			log.info("Updated Product was saved with properties: " + product.toString());
			
			return productMapper.productToProductDTO(product);
			
		}).orElseThrow(NotFoundException::new);
		
	}

	@Override
	public void deleteProductById(Integer id) {
		
		productRepository.deleteById(id);

	}
	
	//taking the value of EURO using HNB API
	public BigDecimal euroValueRate() {
		
		log.info("Inside ProductServiceImpl! Method: euroValueRate");
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<List<EuroValue>> euroValueResponse = 
				restTemplate.exchange(HNB_EURO_VALUE_URL,
						HttpMethod.GET, null, new ParameterizedTypeReference<List<EuroValue>>(){
			
		         });
		List<EuroValue> euroValues = euroValueResponse.getBody();
		EuroValue euroValue = euroValues.get(0);
		BigDecimal euroValueRate = new BigDecimal(euroValue.getKupovniTecaj().replace(",", "."));
		
		log.info("Value of Euro: " + euroValueRate);
		
		return euroValueRate;	
	}
	
	//method for generating a 10 digit unique code
	public Long generateUniqueTenDigitCode() {
		
		Long id = Long.parseLong(String.valueOf(System.currentTimeMillis())
				  .substring(1,10)
				  .concat(String.valueOf(inc)));
		
		inc = (inc+1)%10;
		return id;
	}
	
	private static int inc = 0;
}
