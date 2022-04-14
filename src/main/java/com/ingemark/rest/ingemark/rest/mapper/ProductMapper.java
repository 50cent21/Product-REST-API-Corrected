package com.ingemark.rest.ingemark.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.ingemark.rest.ingemark.rest.domain.Product;
import com.ingemark.rest.ingemark.rest.model.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	ProductDTO productToProductDTO(Product product);
	
	Product productDTOToProduct(ProductDTO productDTO);
}
