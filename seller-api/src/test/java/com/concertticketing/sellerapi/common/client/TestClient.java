package com.concertticketing.sellerapi.common.client;

import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.concertticketing.sellerapi.common.response.SuccessResponse;
import com.concertticketing.sellerapi.security.provider.JwtProvider;

public class TestClient {
    private final TestRestTemplate restTemplate;
    private final JwtProvider jwtProvider;

    public TestClient(Environment environment, JwtProvider jwtProvider) {
        TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplateBuilder());
        restTemplate.setUriTemplateHandler(
            new LocalHostUriTemplateHandler(environment)
        );
        this.restTemplate = restTemplate;
        this.jwtProvider = jwtProvider;
    }

    public void setDefaultAuthorization(Integer sellerId) {
        String token = jwtProvider.generateToken(sellerId, JwtProvider.TokenType.ACCESS);
        setAuthorizationToken(token);
    }

    private void setAuthorizationToken(String token) {
        restTemplate.getRestTemplate().getInterceptors()
            .add((request, body, execution) -> {
                if (!request.getHeaders().containsKey("Authorization")) {
                    request.getHeaders().setBearerAuth(token);
                }
                return execution.execute(request, body);
            });
    }

    public <T> ResponseEntity<SuccessResponse<T>> post(String url, Object body, Class<T> clazz) {
        return exchange(url, HttpMethod.POST, body, clazz);
    }

    public <T> ResponseEntity<SuccessResponse<T>> get(String url, Class<T> clazz) {
        return exchange(url, HttpMethod.GET, null, clazz);
    }

    public <T> ResponseEntity<SuccessResponse<T>> put(String url, Object body, Class<T> clazz) {
        return exchange(url, HttpMethod.PUT, body, clazz);
    }

    public <T> ResponseEntity<SuccessResponse<T>> delete(String url, Object body, Class<T> clazz) {
        return exchange(url, HttpMethod.DELETE, body, clazz);
    }

    public <T> ResponseEntity<SuccessResponse<T>> exchange(String url, HttpMethod method, Object body, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> req = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<SuccessResponse<T>> type = ParameterizedTypeReference.forType(
            ResolvableType.forClassWithGenerics(SuccessResponse.class, clazz).getType()
        );
        return restTemplate.exchange(url, method, req, type);
    }
}
