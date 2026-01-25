package br.com.devfullcycle.account.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BalanceUpdatedEvent(
		@JsonProperty("Name") String name,
	    @JsonProperty("Payload") Payload payload
	) {
	    public record Payload(
	    	@JsonProperty("account_id_from") String accountIdFrom,
	        @JsonProperty("account_id_to") String accountIdTo,
	        @JsonProperty("balance_account_id_from") BigDecimal balanceFrom,
	        @JsonProperty("balance_account_id_to") BigDecimal balanceTo
	    ) {}
	}