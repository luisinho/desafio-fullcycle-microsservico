package br.com.devfullcycle.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devfullcycle.account.builders.BalanceDtoBuilder;
import br.com.devfullcycle.account.dto.BalanceDto;
import br.com.devfullcycle.account.entity.Account;
import br.com.devfullcycle.account.service.AccountService;

@RestController
@RequestMapping(value =  "/balances")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/{accountId}")
	public ResponseEntity<BalanceDto> getBalance(@PathVariable String accountId) throws Exception {

		Account account = this.accountService.getBalance(accountId);
		
		BalanceDto dto = BalanceDtoBuilder.builder()
		   .setAccountIdFrom(account.getAccountIdFrom())
		   .setAccountIdTo(account.getAccountIdTo())
		   .setBalanceFrom(account.getBalanceAccountFrom())
		   .setBalanceTo(account.getBalanceAccountTo())
		   .get();

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping
	public ResponseEntity<Page<BalanceDto>> list(Pageable pageable) {
		Page <BalanceDto> listDto = this.accountService.listBalance(pageable);

		return ResponseEntity.ok().body(listDto);
	}
}