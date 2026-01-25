package br.com.devfullcycle.account.consumer;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.devfullcycle.account.dto.BalanceUpdatedEvent;
import br.com.devfullcycle.account.exceptions.InvalidKafkaMessageException;
import br.com.devfullcycle.account.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountConsumerTest {

	@Mock
    private ObjectMapper objectMapper;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountConsumer accountConsumer;

    private String validMessage;

    private BalanceUpdatedEvent event;

    @BeforeEach
    public void setup() {
        validMessage = """
            {
              "name": "BalanceUpdated",
              "payload": {
                "account_id_from": "acc-1",
                "account_id_to": "acc-2",
                "balance_account_from": 100,
                "balance_account_to": 200
              }
            }
        """;

        event = mock(BalanceUpdatedEvent.class);
    }

    @Test
    public void shouldConsumeMessageAndUpdateBalance() throws Exception {

        when(this.objectMapper.readValue(this.validMessage, BalanceUpdatedEvent.class))
                .thenReturn(this.event);

        this.accountConsumer.consume(this.validMessage);

        verify(this.objectMapper, times(1))
                .readValue(this.validMessage, BalanceUpdatedEvent.class);

        verify(this.accountService, times(1))
                .updateBalance(this.event);
    }

    @Test
    void shouldThrowInvalidKafkaMessageExceptionWhenJsonIsInvalid() throws Exception {

        String invalidJson = "{ invalid json }";

        when(this.objectMapper.readValue(invalidJson, BalanceUpdatedEvent.class))
                .thenThrow(JsonProcessingException.class);

        assertThrows(InvalidKafkaMessageException.class, () ->
                this.accountConsumer.consume(invalidJson)
        );

        verify(this.accountService, never()).updateBalance(any());
    }    

    @Test
    public void shouldThrowExceptionWhenObjectMapperFails() throws Exception {

    	String json = "{ json }";
    	
        when(this.objectMapper.readValue(json, BalanceUpdatedEvent.class))
                .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () ->
             this.accountConsumer.consume(this.validMessage)
        );

        verify(this.accountService, never()).updateBalance(any());
    }

    @Test
    public void shouldThrowExceptionWhenServiceFails() throws Exception {

        when(this.objectMapper.readValue(this.validMessage, BalanceUpdatedEvent.class))
                .thenReturn(event);

        doThrow(RuntimeException.class)
                .when(this.accountService).updateBalance(this.event);

        assertThrows(RuntimeException.class, () ->
              this.accountConsumer.consume(this.validMessage)
        );

        verify(this.accountService, times(1)).updateBalance(this.event);
    }
    
    @Test
    public void shouldThrowExceptionWhenMessageIsEmpty() {

        assertThrows(Exception.class, () ->
                this.accountConsumer.consume("")
        );

        verify(this.accountService, never()).updateBalance(any());
    }

    @Test
    public void shouldThrowExceptionWhenMessageIsNull() {

        assertThrows(Exception.class, () ->
                this.accountConsumer.consume(null)
        );

        verify(this.accountService, never()).updateBalance(any());
    }
}