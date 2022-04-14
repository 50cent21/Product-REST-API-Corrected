package com.ingemark.rest.ingemark.rest.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "product")
public class Product implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "code")
	private Long code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price_hrk")
	private BigDecimal priceHrk;
	
	@Column(name = "price_eur")
	private BigDecimal priceEur;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_available")
	private boolean isAvailable;
	
	public Product() {
		
	}

	public Product(String name, BigDecimal priceHrk, String description, boolean isAvailable) {
		this.name = name;
		this.priceHrk = priceHrk;
		this.description = description;
		this.isAvailable = isAvailable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPriceHrk() {
		return priceHrk;
	}

	public void setPriceHrk(BigDecimal priceHrk) {
		this.priceHrk = priceHrk;
	}

	public BigDecimal getPriceEur() {
		return priceEur;
	}

	public void setPriceEur(BigDecimal priceEur) {
		this.priceEur = priceEur;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", name=" + name + ", priceHrk=" + priceHrk + ", priceEur="
				+ priceEur + ", description=" + description + ", isAvailable=" + isAvailable + "]";
	}
	
	
	
}
