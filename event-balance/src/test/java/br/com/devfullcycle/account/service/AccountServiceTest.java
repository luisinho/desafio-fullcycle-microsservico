package br.com.devfullcycle.account.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.mockito.ArgumentMatchers.argThat;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.devfullcycle.account.dto.BalanceDto;
import br.com.devfullcycle.account.dto.BalanceUpdatedEvent;
import br.com.devfullcycle.account.entity.Account;
import br.com.devfullcycle.account.exceptions.AccountNotFoundException;
import br.com.devfullcycle.account.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private BalanceUpdatedEvent event;

    @BeforeEach
    public void setup() {

        this.account = new Account();
        this.account.setAccountIdFrom("acc-1");
        this.account.setAccountIdTo("acc-2");
        this.account.setBalanceAccountFrom(BigDecimal.valueOf(100));
        this.account.setBalanceAccountTo(BigDecimal.valueOf(200));
        this.account.setCreatedAt(Instant.now());

        this.event = new BalanceUpdatedEvent(
            "BalanceUpdated",
            new BalanceUpdatedEvent.Payload(
                "acc-1",
                "acc-2",
                BigDecimal.valueOf(150),
                BigDecimal.valueOf(250)
            )
        );
    }    

    @Test
    public void shouldReturnBalanceWhenAccountExists() throws Exception {

        when(this.repository.findByAccountIdFrom("acc-1"))
                .thenReturn(this.account);

        Account result = this.accountService.getBalance("acc-1");

        assertNotNull(result);
        assertEquals("acc-1", result.getAccountIdFrom());
        assertEquals(BigDecimal.valueOf(100), result.getBalanceAccountFrom());

        verify(this.repository).findByAccountIdFrom("acc-1");
    }

    @Test
    public void shouldThrowExceptionWhenAccountDoesNotExist() {

        when(this.repository.findByAccountIdFrom("acc-1"))
                .thenReturn(null);

        AccountNotFoundException exception =
            assertThrows(AccountNotFoundException.class,
                () -> this.accountService.getBalance("acc-1"));

        assertEquals(
            "No balance was found for account with ID: acc-1",
            exception.getMessage()
        );

        verify(this.repository).findByAccountIdFrom("acc-1");
    }    

    @Test
    public void shouldReturnPagedBalanceDtoList() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Account> page = new PageImpl<>(List.of(this.account));

        when(this.repository.findAll(pageable))
                .thenReturn(page);

        Page<BalanceDto> result = this.accountService.listBalance(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        BalanceDto dto = result.getContent().get(0);

        assertEquals("acc-1", dto.getAccountIdFrom());
        assertEquals("acc-2", dto.getAccountIdTo());
        assertEquals(BigDecimal.valueOf(100), dto.getBalanceFrom());
        assertEquals(BigDecimal.valueOf(200), dto.getBalanceTo());

        verify(repository).findAll(pageable);
    }

    @Test
    public void shouldReturnEmptyPageWhenNoAccountsExist() {

        Pageable pageable = PageRequest.of(0, 10);

        when(this.repository.findAll(pageable))
                .thenReturn(Page.empty());

        Page<BalanceDto> result = this.accountService.listBalance(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(this.repository).findAll(pageable);
    }

    @Test
    public void shouldUpdateBalanceWhenAccountExists() {

        when(this.repository.findByAccountIdFrom("acc-1"))
                .thenReturn(this.account);

        this.accountService.updateBalance(this.event);

        assertEquals(
            BigDecimal.valueOf(150),
            this.account.getBalanceAccountFrom()
        );

        assertEquals(
            BigDecimal.valueOf(250),
            this.account.getBalanceAccountTo()
        );

        verify(this.repository).save(this.account);
    }

    @Test
    public void shouldCreateNewAccountWhenAccountDoesNotExist() {

        when(this.repository.findByAccountIdFrom("acc-1"))
                .thenReturn(null);

        this.accountService.updateBalance(this.event);

        verify(this.repository).save(argThat(saved -> 
            saved.getAccountIdFrom().equals("acc-1") &&
            saved.getAccountIdTo().equals("acc-2") &&
            saved.getBalanceAccountFrom().equals(BigDecimal.valueOf(150)) &&
            saved.getBalanceAccountTo().equals(BigDecimal.valueOf(250)) &&
            saved.getCreatedAt() != null
        ));
    }
}