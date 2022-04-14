package com.ingemark.rest.ingemark.rest.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductListDTO {

	List<ProductDTO> products;
}
