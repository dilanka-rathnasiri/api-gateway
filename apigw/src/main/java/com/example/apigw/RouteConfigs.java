package com.example.apigw;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfigs {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/server-1")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.rewritePath("/server-1", "/"))
                        .uri("http://localhost:3000"))
                .route(r -> r.path("/server-1/*")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.rewritePath("/server-1/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:3000"))
                .route(r -> r.path("/server-2")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.rewritePath("/server-2", "/"))
                        .uri("http://localhost:3001"))
                .route(r -> r.path("/server-2/*")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.rewritePath("/server-2/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:3001"))
                .route(r -> r.path("/server-3/*")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/server-3/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:3000"))
                .route(r -> r.path("/server-4/*")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/server-4/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:3001"))
                .build();
    }
}
