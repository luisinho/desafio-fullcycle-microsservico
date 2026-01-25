package br.com.devfullcycle.account.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import br.com.devfullcycle.account.dto.BalanceUpdatedEvent;
import br.com.devfullcycle.account.exceptions.InvalidKafkaMessageException;
import br.com.devfullcycle.account.service.AccountService;

@Service
public class AccountConsumer {
	
	private static final Logger log =
	        LoggerFactory.getLogger(AccountConsumer.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
    private AccountService accountService;

    @KafkaListener(topics = "balances", groupId = "balance-service")
    public void consume(String message) throws Exception {

    	log.info("Kafka message received: ", message);

    	if (message == null || message.isBlank()) {
    		log.warn("Invalid Kafka message received: [{}]", message);
    	    throw new InvalidKafkaMessageException("Invalid Kafka message");
    	}

    	try {
    		
    		BalanceUpdatedEvent event =
    				this.objectMapper.readValue(message, BalanceUpdatedEvent.class);
    		
    		this.accountService.updateBalance(event);

    	} catch (JsonProcessingException ex) {
    	    log.error("Invalid JSON message: {}", message, ex);
    	    throw new InvalidKafkaMessageException("Invalid JSON message");
    	}
    }
}