package com.microservice.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfig().corsConfigurationSource();
    }
    @Bean
    public SecurityWebFilterChain oktaSecurityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchange -> exchange
                        .anyExchange().permitAll())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(config -> config.configurationSource(corsConfigurationSource()))
//                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
//                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((exchange, denied) -> Mono.error(denied)));
        return http.build();
    }
//    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new OktaRoleConverter());
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
}
