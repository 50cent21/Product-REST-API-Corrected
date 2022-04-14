package com.ingemark.rest.ingemark.rest.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Integer id;
	private Long code;
	private String name;
	
	@Min(value=0, message = "Price must be at least 0")
	private BigDecimal priceHrk;
	
	@Min(value=0, message = "Price must be at least 0")
	private BigDecimal priceEur;
	
	private String description;
	
	private boolean isAvailable = true;
}
