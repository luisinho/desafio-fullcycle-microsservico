package br.com.devfullcycle.account.dto;

import java.math.BigDecimal;

public class BalanceDto {
	
    private String accountIdFrom;
	
    private String accountIdTo;

    private BigDecimal balanceFrom;
    
    private BigDecimal balanceTo;

	public String getAccountIdFrom() {
		return accountIdFrom;
	}

	public void setAccountIdFrom(String accountIdFrom) {
		this.accountIdFrom = accountIdFrom;
	}

	public String getAccountIdTo() {
		return accountIdTo;
	}

	public void setAccountIdTo(String accountIdTo) {
		this.accountIdTo = accountIdTo;
	}

	public BigDecimal getBalanceFrom() {
		return balanceFrom;
	}

	public void setBalanceFrom(BigDecimal balanceFrom) {
		this.balanceFrom = balanceFrom;
	}

	public BigDecimal getBalanceTo() {
		return balanceTo;
	}

	public void setBalanceTo(BigDecimal balanceTo) {
		this.balanceTo = balanceTo;
	}
}