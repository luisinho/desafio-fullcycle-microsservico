package br.com.devfullcycle.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;
import br.com.devfullcycle.account.exceptions.InvalidKafkaMessageException;

@Configuration
public class KafkaErrorHandlerConfig {
	
	@Bean
	DefaultErrorHandler kafkaErrorHandler(
	        KafkaTemplate<Object, Object> kafkaTemplate) {

	    DeadLetterPublishingRecoverer recoverer =
	        new DeadLetterPublishingRecoverer(kafkaTemplate);

	    DefaultErrorHandler handler =
	        new DefaultErrorHandler(recoverer, new FixedBackOff(0L, 0));

	    handler.addNotRetryableExceptions(InvalidKafkaMessageException.class);

	    return handler;
	}
}