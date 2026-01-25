package br.com.devfullcycle.account.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devfullcycle.account.builders.AccountBuilder;
import br.com.devfullcycle.account.builders.BalanceDtoBuilder;

import br.com.devfullcycle.account.dto.BalanceDto;
import br.com.devfullcycle.account.dto.BalanceUpdatedEvent;
import br.com.devfullcycle.account.entity.Account;
import br.com.devfullcycle.account.exceptions.AccountNotFoundException;
import br.com.devfullcycle.account.repository.AccountRepository;

@Service
public class AccountService {    

	@Autowired
	private AccountRepository repository;

	@Transactional(readOnly = true)
	public Account getBalance(String accountId) throws Exception {

		Account account = this.repository.findByAccountIdFrom(accountId);

		if (account == null) {
			throw new AccountNotFoundException("No balance was found for account with ID: " + accountId);	
		}

		return account;	
	}
	
	@Transactional(readOnly = true)
	public Page<BalanceDto> listBalance(Pageable pageable) {
		
		Page<Account>  list = this.repository.findAll(pageable);
		
		return list.map(entity -> 
		   BalanceDtoBuilder.builder()
			  .setAccountIdFrom(entity.getAccountIdFrom())
			  .setAccountIdTo(entity.getAccountIdTo())
			  .setBalanceFrom(entity.getBalanceAccountFrom())
			  .setBalanceTo(entity.getBalanceAccountTo())
			  .get()				
		);
	}

	@Transactional
    public void updateBalance(BalanceUpdatedEvent event) {
		
		String accountId = event.payload().accountIdFrom();

		Account account = this.repository.findByAccountIdFrom(accountId);		
		
		if (account == null) {
			account = this.getEntity(event);
			this.repository.save(account);			
		} else {
			account.setBalanceAccountFrom(event.payload().balanceFrom());
			account.setBalanceAccountTo(event.payload().balanceTo());
			this.repository.save(account);
		}
            
    }

	private Account getEntity(BalanceUpdatedEvent event) {
		return AccountBuilder.builder()
				.setAccountIdFrom(event.payload().accountIdFrom())
				.setAccountIdTo(event.payload().accountIdTo())
				.setBalanceAccountFrom(event.payload().balanceFrom())
				.setBalanceAccountTo(event.payload().balanceTo())
				.setCreatedAt(Instant.now())
				.get();
				
	}
}