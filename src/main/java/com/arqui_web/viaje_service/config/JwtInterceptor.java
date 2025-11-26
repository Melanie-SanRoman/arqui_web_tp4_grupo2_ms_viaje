package com.arqui_web.viaje_service.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class JwtInterceptor implements ClientHttpRequestInterceptor {

	@Value("${jwt.secret}")
    private String jwtToken;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        request.getHeaders().add("Authorization", "Bearer " + jwtToken);
        return execution.execute(request, body);
    }
}

