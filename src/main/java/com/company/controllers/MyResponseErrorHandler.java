package com.company.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class MyResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		if(response.getStatusCode() != HttpStatus.OK) {
			System.out.println("Has Error!");
			System.out.println("Status code: " + response.getStatusCode());
			return true;
		}
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		System.out.println("Error Handled");
		
	}
	

}
