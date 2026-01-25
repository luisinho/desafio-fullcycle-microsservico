package br.com.devfullcycle.account.builders;

import java.math.BigDecimal;

import br.com.devfullcycle.account.dto.BalanceDto;

public class BalanceDtoBuilder {

	private BalanceDto dto;

	public static BalanceDtoBuilder builder() {
		BalanceDtoBuilder build = new  BalanceDtoBuilder();
		build.dto = new BalanceDto();
		return build;
	}

	public BalanceDtoBuilder setAccountIdFrom(String accountIdFrom) {
		this.dto.setAccountIdFrom(accountIdFrom);
		return this;
	}	

	public BalanceDtoBuilder setAccountIdTo(String accountIdTo) {
		this.dto.setAccountIdTo(accountIdTo);
		return this;
	}	

	public BalanceDtoBuilder setBalanceFrom(BigDecimal balanceFrom) {
		this.dto.setBalanceFrom(balanceFrom);
		return this;
	}

	public BalanceDtoBuilder setBalanceTo(BigDecimal balanceTo) {
		this.dto.setBalanceTo(balanceTo);
		return this;
	}

	public BalanceDto get() {
		return this.dto;
	}
}