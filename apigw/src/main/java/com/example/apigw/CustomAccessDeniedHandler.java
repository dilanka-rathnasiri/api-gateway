package com.example.apigw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> respBody = new HashMap<>();
        respBody.put("message", "Invalid url!");
        respBody.put("error", ex.getMessage());
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(respBody);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory().wrap(bytes)));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return exchange.getResponse().setComplete();
        }
    }
}
