package com.example.demo_cssc.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig {

    private static final String TOKEN_ADMIN = "Bearer TOKEN_ADMIN_123";
    private static final String TOKEN_USER = "Bearer TOKEN_USER_123";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 🔥 Adicionado aqui
                .authorizeHttpRequests(auth -> auth
                        // 🔥 Desabilita autenticação no Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        // 🔥 Aplica autenticação apenas nos endpoints da API
                        .requestMatchers(HttpMethod.GET, "/soldados/**", "/missoes/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/soldados/**", "/missoes/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/soldados/**", "/missoes/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/soldados/**", "/missoes/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    static class TokenAuthFilter extends UsernamePasswordAuthenticationFilter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String authHeader = req.getHeader("Authorization");

            if (authHeader == null) {
                chain.doFilter(request, response);
                return;  // 🔥 Deixa passar sem autenticação
            }

            UserDetails userDetails;
            if (authHeader.equals(TOKEN_ADMIN)) {
                userDetails = User.withUsername("admin")
                        .password("")
                        .roles("ADMIN")
                        .build();
            } else if (authHeader.equals(TOKEN_USER)) {
                userDetails = User.withUsername("user")
                        .password("")
                        .roles("USER")
                        .build();
            } else {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("{\"error\": \"Acesso negado: Token inválido\"}");
                return;
            }

            // Define usuário autenticado
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            chain.doFilter(request, response);
        }
    }
}
