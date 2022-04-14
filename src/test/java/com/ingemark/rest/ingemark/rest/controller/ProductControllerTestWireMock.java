package com.ingemark.rest.ingemark.rest.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.ingemark.rest.ingemark.rest.controller.v1.ProductController;

@SpringBootTest
class ProductControllerTestWireMock {
	
	private static final Integer DEFAULT_CUSTOMER_ID = 1;
	
	public static final String NAME = "Toothbrush";
	
	CloseableHttpClient httpClient = HttpClients.createDefault();	
	
	WireMockServer wireMockServer;
	
	@BeforeEach
	void setUp() throws Exception {		
		
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
				.port(8090));
		
		wireMockServer.start();
		configureFor(wireMockServer.port());
		System.out.println(" " + wireMockServer.port());
	}

	@AfterEach
    public void teardown () {
		
        wireMockServer.stop();
    }
	
	@Test
	void testWireMock() {
		
		assertTrue(wireMockServer.isRunning());
	}
	
	@Test
	void testGetAllProducts() throws Exception{
		
		wireMockServer.stubFor(WireMock.get(ProductController.BASE_URL)
				.willReturn(aResponse()
			    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			    .withBody("[\r\n"
			    		+ " {\r\n"
			    		+ "    \"id\": 1,\r\n"
			    		+ "    \"code\": 6491750600,\r\n"
			    		+ "    \"name\": \"brush for me\",\r\n"
			    		+ "    \"priceHrk\": 13.99,\r\n"
			    		+ "    \"priceEur\": 105.25200226,\r\n"
			    		+ "    \"description\": \"Teeth for me\",\r\n"
			    		+ "    \"available\": true\r\n"
			    		+ " },\r\n"
			    		+ " {\r\n"
			    		+ "    \"id\": 2,\r\n"
			    		+ "    \"code\": 6491750602,\r\n"
			    		+ "    \"name\": \"brush for me2\",\r\n"
			    		+ "    \"priceHrk\": 13.99,\r\n"
			    		+ "    \"priceEur\": 105.25200226,\r\n"
			    		+ "    \"description\": \"Teeth for me2\",\r\n"
			    		+ "    \"available\": true\r\n"
			    		+ " }\r\n"
			    		+ "]")));
		
		HttpGet request = new HttpGet("http://localhost:8090/api/v1/product");
		HttpResponse httpResponse = httpClient.execute(request);
		String stringResponse = convertResponseToString(httpResponse);
		
		verify(getRequestedFor(urlEqualTo("/api/v1/product")));
        assertEquals("[\r\n"
        		+ " {\r\n"
        		+ "    \"id\": 1,\r\n"
        		+ "    \"code\": 6491750600,\r\n"
        		+ "    \"name\": \"brush for me\",\r\n"
        		+ "    \"priceHrk\": 13.99,\r\n"
        		+ "    \"priceEur\": 105.25200226,\r\n"
        		+ "    \"description\": \"Teeth for me\",\r\n"
        		+ "    \"available\": true\r\n"
        		+ " },\r\n"
        		+ " {\r\n"
        		+ "    \"id\": 2,\r\n"
        		+ "    \"code\": 6491750602,\r\n"
        		+ "    \"name\": \"brush for me2\",\r\n"
        		+ "    \"priceHrk\": 13.99,\r\n"
        		+ "    \"priceEur\": 105.25200226,\r\n"
        		+ "    \"description\": \"Teeth for me2\",\r\n"
        		+ "    \"available\": true\r\n"
        		+ " }\r\n"
        		+ "]", stringResponse);
		
		
	}
	
	@Test
	void testGetProductById() throws Exception{	
		
		wireMockServer.stubFor(get(urlEqualTo("/api/v1/product/" + DEFAULT_CUSTOMER_ID))
				.willReturn(aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody("{\r\n"
								+ "    \"id\": 1,\r\n"
								+ "    \"code\": 6491750600,\r\n"
								+ "    \"name\": \"Toothbrush\",\r\n"
								+ "    \"priceHrk\": 13.99,\r\n"
								+ "    \"priceEur\": 105.25200226,\r\n"
								+ "    \"description\": \"For Brushing Teeth\",\r\n"
								+ "    \"available\": true\r\n"
								+ "}")));
		
		
		HttpGet request = new HttpGet("http://localhost:8090/api/v1/product/1");
		HttpResponse httpResponse = httpClient.execute(request);
		String stringResponse = convertResponseToString(httpResponse);
		
		verify(getRequestedFor(urlEqualTo("/api/v1/product/1")));
		
		assertEquals("{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"code\": 6491750600,\r\n"
				+ "    \"name\": \"Toothbrush\",\r\n"
				+ "    \"priceHrk\": 13.99,\r\n"
				+ "    \"priceEur\": 105.25200226,\r\n"
				+ "    \"description\": \"For Brushing Teeth\",\r\n"
				+ "    \"available\": true\r\n"
				+ "}", stringResponse);
	}
	
	@Test
	void testGetProductByName() throws Exception {
		
		wireMockServer.stubFor(get(urlEqualTo(ProductController.BASE_URL + "/name/" + NAME))
				.willReturn(aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody("{\r\n"
								+ "    \"id\": 1,\r\n"
								+ "    \"code\": 6491750600,\r\n"
								+ "    \"name\": \"Toothbrush\",\r\n"
								+ "    \"priceHrk\": 13.99,\r\n"
								+ "    \"priceEur\": 105.25200226,\r\n"
								+ "    \"description\": \"For Brushing Teeth\",\r\n"
								+ "    \"available\": true\r\n"
								+ "}")));
		
		
		HttpGet request = new HttpGet("http://localhost:8090/api/v1/product/name/" + NAME);
		HttpResponse httpResponse = httpClient.execute(request);
		String stringResponse = convertResponseToString(httpResponse);
		
		verify(getRequestedFor(urlEqualTo(ProductController.BASE_URL + "/name/" + NAME)));
		
		assertEquals("{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"code\": 6491750600,\r\n"
				+ "    \"name\": \"Toothbrush\",\r\n"
				+ "    \"priceHrk\": 13.99,\r\n"
				+ "    \"priceEur\": 105.25200226,\r\n"
				+ "    \"description\": \"For Brushing Teeth\",\r\n"
				+ "    \"available\": true\r\n"
				+ "}", stringResponse);
	}

	@Test
	void testCreateProduct() throws Exception{
		
		wireMockServer.stubFor(post(urlEqualTo(ProductController.BASE_URL))
						.withHeader("content-type", equalTo("application/json"))
						.withRequestBody(containing("\"name\": \"Toothbrush\""))
		                .withRequestBody(containing("\"description\": \"For Brushing Teeth\""))
						.willReturn(aResponse()
								.withStatus(200)));
		
		InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("product.json");
        String jsonString = convertInputStreamToString(jsonInputStream);
        StringEntity entity = new StringEntity(jsonString);
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(String.format("http://localhost:8090/api/v1/product", 8090));
        request.addHeader("Content-Type", "application/json");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        
        verify(postRequestedFor(urlEqualTo("/api/v1/product"))
                .withHeader("Content-Type", equalTo("application/json")));
        assertEquals(200, response.getStatusLine().getStatusCode());
	}

	@Test
	void testUpdateProductById() throws Exception{
		
	   wireMockServer.stubFor(put(urlPathEqualTo(ProductController.BASE_URL + "/" + DEFAULT_CUSTOMER_ID))
			    		.withHeader("Content-Type", equalTo("application/json"))
					    .withRequestBody(containing("\"name\": \"Bluetooth Toothbrush\""))
		                .withRequestBody(containing("\"description\": \"For Brushing Teeth Fast\""))
		                .willReturn(aResponse()
								.withStatus(200)));
	   
	   InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("product_update.json");
       String jsonString = convertInputStreamToString(jsonInputStream);
       StringEntity entity = new StringEntity(jsonString);
       
       CloseableHttpClient httpClient = HttpClients.createDefault();
       HttpPut request = new HttpPut(String.format("http://localhost:8090/api/v1/product/" + DEFAULT_CUSTOMER_ID, 8090));
       request.addHeader("Content-Type", "application/json");
       request.setEntity(entity);
       HttpResponse response = httpClient.execute(request);
	   
	   
	   verify(putRequestedFor(urlEqualTo("/api/v1/product/" + DEFAULT_CUSTOMER_ID))
               .withHeader("Content-Type", equalTo("application/json")));
       assertEquals(200, response.getStatusLine().getStatusCode());	    		 
	}

	@Test
	void testDeleteProductById() throws Exception{
		
		wireMockServer.stubFor(delete(urlEqualTo(ProductController.BASE_URL + "/" + DEFAULT_CUSTOMER_ID))
				.willReturn(aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody("{\r\n"
								+ "    \"id\": 1,\r\n"
								+ "    \"code\": 6491750600,\r\n"
								+ "    \"name\": \"Toothbrush\",\r\n"
								+ "    \"priceHrk\": 13.99,\r\n"
								+ "    \"priceEur\": 105.25200226,\r\n"
								+ "    \"description\": \"For Brushing Teeth\",\r\n"
								+ "    \"available\": true\r\n"
								+ "}")));
		
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete request = new HttpDelete(String.format("http://localhost:8090/api/v1/product/" + DEFAULT_CUSTOMER_ID, 8090));
        request.addHeader("Content-Type", "application/json");
        HttpResponse response = httpClient.execute(request);
		
		
		verify(deleteRequestedFor(urlEqualTo("/api/v1/product/1"))
                .withHeader("Content-Type", equalTo("application/json")));
        assertEquals(200, response.getStatusLine().getStatusCode());
	}

	
	private String convertResponseToString(HttpResponse response) throws IOException{
		
		InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
	}
	
	private String convertInputStreamToString(InputStream inputStream) {
		
		Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
	}
}
