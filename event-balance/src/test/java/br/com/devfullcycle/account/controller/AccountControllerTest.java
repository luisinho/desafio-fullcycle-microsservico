package br.com.devfullcycle.account.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import br.com.devfullcycle.account.builders.BalanceDtoBuilder;
import br.com.devfullcycle.account.config.ServiceHandlerConfig;
import br.com.devfullcycle.account.dto.BalanceDto;
import br.com.devfullcycle.account.entity.Account;
import br.com.devfullcycle.account.exceptions.AccountNotFoundException;
import br.com.devfullcycle.account.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;
    
    @InjectMocks
    private AccountController accountController;

    private Account account;

    @BeforeEach
    public void setup() {

    	this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.accountController)
                .setControllerAdvice(new ServiceHandlerConfig())
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver()
                )
                .build();

        this.account = new Account();
        this.account.setAccountIdFrom("acc-1");
        this.account.setAccountIdTo("acc-2");
        this.account.setBalanceAccountFrom(BigDecimal.valueOf(100));
        this.account.setBalanceAccountTo(BigDecimal.valueOf(200));
        this.account.setCreatedAt(Instant.now());
    }    

    @Test
    public void shouldReturnBalanceWhenAccountExists() throws Exception {

        when(this.accountService.getBalance("acc-1"))
                .thenReturn(this.account);

        this.mockMvc.perform(get("/balances/acc-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountIdFrom").value("acc-1"))
                .andExpect(jsonPath("$.accountIdTo").value("acc-2"))
                .andExpect(jsonPath("$.balanceFrom").value(100))
                .andExpect(jsonPath("$.balanceTo").value(200));
    }

    @Test
    public void shouldReturn404WhenAccountNotFound() throws Exception {

        when(this.accountService.getBalance("acc-1"))
                .thenThrow(new AccountNotFoundException("Account not found"));

        this.mockMvc.perform(get("/balances/acc-1"))
                .andExpect(status().isNotFound());
    }    

    @Test
    public void shouldReturnPagedBalances() throws Exception {

        BalanceDto dto = BalanceDtoBuilder.builder()
                .setAccountIdFrom("acc-1")
                .setAccountIdTo("acc-2")
                .setBalanceFrom(BigDecimal.valueOf(100))
                .setBalanceTo(BigDecimal.valueOf(200))
                .get();

        Page<BalanceDto> page =
                new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);

        when(this.accountService.listBalance(any(Pageable.class)))
                .thenReturn(page);

        this.mockMvc.perform(get("/balances")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].accountIdFrom").value("acc-1"))
                .andExpect(jsonPath("$.content[0].balanceFrom").value(100));
    }

    @Test
    public void shouldReturnEmptyPageWhenNoBalancesExist() throws Exception {

        Page<BalanceDto> emptyPage =
                new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(this.accountService.listBalance(any(Pageable.class)))
                .thenReturn(emptyPage);

        this.mockMvc.perform(get("/balances"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }
}