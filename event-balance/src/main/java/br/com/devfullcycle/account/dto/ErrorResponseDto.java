package br.com.devfullcycle.account.dto;

public record ErrorResponseDto(
		int status,
        String error,
        String message,
        String timestamp) {}
