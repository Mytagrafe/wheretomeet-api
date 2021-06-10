package com.wheretomeet;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Configuration
@EnableWebSocketMessageBroker
public class WhereToMeetMessengerConfig implements WebSocketMessageBrokerConfigurer {

    final static Logger log = LoggerFactory.getLogger(WhereToMeetMessengerConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    
        // Set prefix for the endpoint that the client listens for our messages from
        registry.enableSimpleBroker("/topic");
        
        // Set prefix for endpoints the client will send messages to
        registry.setApplicationDestinationPrefixes("/app");
        log.info("brokers initialized");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    
        // Registers the endpoint where the connection will take place
        registry.addEndpoint("/stomp")
            // Allow the origin http://localhost:63343 to send messages to us. (Base URL of the client)
            .setAllowedOrigins("http://localhost:8080")
            // Enable SockJS fallback options
            .withSockJS();
        log.info("endpoints initialized");
    }
}
