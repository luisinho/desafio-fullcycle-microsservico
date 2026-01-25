package br.com.devfullcycle.account.exceptions;

public class InvalidKafkaMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidKafkaMessageException(String message) {
        super(message);
    }
}