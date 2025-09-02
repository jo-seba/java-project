package com.concertticketing.userapi.common.client;

import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.concertticketing.userapi.security.provider.JwtProvider;

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

    public void setDefaultAuthorization(Long userId) {
        String token = jwtProvider.generateToken(userId, JwtProvider.TokenType.ACCESS);
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

    public <T> ResponseEntity<T> post(String url, Object body, Class<T> clazz) {
        return exchange(url, HttpMethod.POST, body, clazz);
    }

    public <T> ResponseEntity<T> get(String url, Class<T> clazz) {
        return exchange(url, HttpMethod.GET, null, clazz);
    }

    public <T> ResponseEntity<T> put(String url, Object body, Class<T> clazz) {
        return exchange(url, HttpMethod.PUT, body, clazz);
    }

    public <T> ResponseEntity<T> delete(String url, Object body, Class<T> clazz) {
        return exchange(url, HttpMethod.DELETE, body, clazz);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, Object body, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> req = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, method, req, clazz);
    }
}
