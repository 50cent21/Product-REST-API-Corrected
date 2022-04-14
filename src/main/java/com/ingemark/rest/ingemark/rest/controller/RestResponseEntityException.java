package com.ingemark.rest.ingemark.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ingemark.rest.ingemark.rest.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityException extends ResponseEntityExceptionHandler{
	
	
	//When NotFoundException is thrown, return a message "Resource Not Found"
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(
			                     Exception exception, WebRequest request) {
		
		log.info("Inside RestControllerAdvice! Method: handleNotFoundException");
		
		return new ResponseEntity<Object> ("Resource Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
