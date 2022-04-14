package com.ingemark.rest.ingemark.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//helper class for taking the value of EURO from HNB API
@JsonIgnoreProperties(ignoreUnknown = true)
public class EuroValue{
	
	@JsonProperty("kupovni_tecaj")
	private String kupovniTecaj;
	
	public EuroValue() {
		
	}

	public EuroValue(String kupovniTecaj) {
		this.kupovniTecaj = kupovniTecaj;
	}


	public String getKupovniTecaj() {
		return kupovniTecaj;
	}

	public void setKupovniTecaj(String kupovniTecaj) {
		this.kupovniTecaj = kupovniTecaj;
	}
	
	
}
