package br.com.devfullcycle.account.builders;

import java.math.BigDecimal;
import java.time.Instant;

import br.com.devfullcycle.account.entity.Account;

public class AccountBuilder {
	
	private Account account;
	
	public static AccountBuilder builder() {
		 AccountBuilder build = new  AccountBuilder();
		 build.account = new Account();
		 return build;
	}
	
	public AccountBuilder setId(Long id) {
        this.account.setId(id);
        return this;
    }

    public AccountBuilder setAccountIdFrom(String accountIdFrom) {
    	this.account.setAccountIdFrom(accountIdFrom);
        return this;
    }
    
    public AccountBuilder setAccountIdTo(String accountIdTo) {
    	this.account.setAccountIdTo(accountIdTo);
        return this;
    }
    
    public AccountBuilder setBalanceAccountFrom(BigDecimal balanceAccountFrom) {
    	this.account.setBalanceAccountFrom(balanceAccountFrom);
    	return this;
	}
    
    public AccountBuilder setBalanceAccountTo(BigDecimal balanceAccountTo) {
    	this.account.setBalanceAccountTo(balanceAccountTo);
    	return this;
	}

    public AccountBuilder setCreatedAt(Instant createdAt) {
    	this.account.setCreatedAt(createdAt);
    	return this;
    }

    public Account get() {
    	return this.account;
    }	
}