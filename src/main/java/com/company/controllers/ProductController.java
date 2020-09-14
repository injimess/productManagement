package com.company.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.company.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/test")
public class ProductController {

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping
	public ResponseEntity<String> getProduct(){
		String resourceUrl = "http://localhost:8080/product";
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
		return response;		
	}
	
	@GetMapping("/{id}")
	public String getProductNameById(@PathVariable("id") Long id) throws JsonMappingException, JsonProcessingException{
		
		String resourceUrl = "http://localhost:8080/product/"+id;
		 ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl,  String.class);
		 
		 ObjectMapper mapper = new ObjectMapper();
		 JsonNode jsonRootNode = mapper.readTree(response.getBody());
		 JsonNode name = jsonRootNode.get("productName");
				 
		 return name.asText();
	}
	
	@PostMapping(value ="")
	public Map<String,Object> createProduct(@RequestBody Product product){
		
		restTemplate.setErrorHandler(new MyResponseErrorHandler());
		String resourceUrl = "http://localhost:8080/product/";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceUrl)
				.queryParam("id", product.getProductID()).queryParam("name", product.getProductName())
				.queryParam("price", product.getProductPrice());
		
		Map<String, Object> productResponse = restTemplate.postForObject(builder.toUriString(), HttpEntity.EMPTY, Map.class);
		return productResponse; 
	}
	
	@PutMapping(value = "")
	public ResponseEntity<Product> updateProject(@RequestBody Product product){
		String resourceUrl = "http://localhost:8080/product/";
		
		HttpEntity<Product> request = new HttpEntity<>(product);
		ResponseEntity<Product> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, request , Product.class); 
		return response ; 
		
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map> deleteProduct(@PathVariable("id") Long id) {
		String resourceUrl = "http://localhost:8080/product/"+id; 
		
		ResponseEntity<Map> response = restTemplate.exchange(resourceUrl, HttpMethod.DELETE ,HttpEntity.EMPTY , Map.class); 
		return response; 		
	}
}
