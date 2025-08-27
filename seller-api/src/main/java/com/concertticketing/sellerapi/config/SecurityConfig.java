package com.concertticketing.sellerapi.config;

import static com.concertticketing.sellerapi.security.constant.SecurityPaths.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;
import com.concertticketing.sellerapi.security.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(SWAGGER_PATHS).permitAll()
                .requestMatchers(AUTH_PATH).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        SellerRole[] roles = SellerRole.values();
        Arrays.sort(roles, Comparator.comparingInt(SellerRole::getDbValue));

        RoleHierarchyImpl.Builder builder = RoleHierarchyImpl.withDefaultRolePrefix();
        IntStream.range(0, roles.length - 1)
            .forEach(i -> builder.role(roles[i + 1].name()).implies(roles[i].name()));

        return builder.build();
    }
}
