package com.arqui_web.viaje_service.config;

import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class JwtInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getCredentials() instanceof String token) {
            request.getHeaders().add("Authorization", "Bearer " + token);
        }

        return execution.execute(request, body);
    }
}


