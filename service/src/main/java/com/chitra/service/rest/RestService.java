package com.chitra.service.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
@PropertySource("classpath:application.properties")
public class RestService {

    @Value("${git_token}")
    private String git_token;

    private final RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> gitExchange(String git_url){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + git_token);
        HttpEntity httpEntity = new HttpEntity(headers);
        return restTemplate.exchange(git_url, HttpMethod.GET, httpEntity, String.class);
    }
}
