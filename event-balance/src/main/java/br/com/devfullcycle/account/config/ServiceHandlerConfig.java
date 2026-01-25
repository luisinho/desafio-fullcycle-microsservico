package br.com.devfullcycle.account.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.devfullcycle.account.dto.ErrorResponseDto;
import br.com.devfullcycle.account.exceptions.AccountNotFoundException;

@RestControllerAdvice
public class ServiceHandlerConfig {
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> entityNotFound(AccountNotFoundException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body(new ErrorResponseDto(
	            		HttpStatus.NOT_FOUND.value(),
	            		HttpStatus.NOT_FOUND.name(),
	                    e.getMessage(),
	                    LocalDateTime
	                    .ofInstant(Instant.now(), ZoneOffset.UTC)
	                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	            ));
	}

}
