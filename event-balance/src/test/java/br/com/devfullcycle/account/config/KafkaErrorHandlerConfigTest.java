package br.com.devfullcycle.account.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;

@ExtendWith(MockitoExtension.class)
public class KafkaErrorHandlerConfigTest {

    @Mock
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Test
    public void shouldCreateDefaultErrorHandlerBean() {

        KafkaErrorHandlerConfig config = new KafkaErrorHandlerConfig();

        DefaultErrorHandler handler =
                config.kafkaErrorHandler(kafkaTemplate);

        assertNotNull(handler);
    }

    @Test
    public void shouldBeInstanceOfDefaultErrorHandler() {

        KafkaErrorHandlerConfig config = new KafkaErrorHandlerConfig();

        DefaultErrorHandler handler =
                config.kafkaErrorHandler(kafkaTemplate);

        assertTrue(handler instanceof DefaultErrorHandler);
    }
}
