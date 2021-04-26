package com.project.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void postTransactionSuccess () throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/points";
        URI uri = new URI(baseUrl);
        Transaction transaction = new Transaction();
        transaction.setPayer("DANNON");
        transaction.setPoints(1000);
        transaction.setTimestamp(new Date());

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void getBalanceSuccess () throws URISyntaxException {
        final String baseUrlPost = "http://localhost:" + randomServerPort + "/points";
        final String baseUrlGet = "http://localhost:" + randomServerPort + "/balance";

        URI uriPost = new URI(baseUrlPost);
        URI uriGet = new URI(baseUrlGet);

        Transaction transaction = new Transaction();
        transaction.setPayer("DANNON");
        transaction.setPoints(1000);
        transaction.setTimestamp(new Date());

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);
        this.restTemplate.postForEntity(uriPost, request, String.class);
        ResponseEntity<String> result = this.restTemplate.getForEntity(uriGet, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertTrue(Objects.requireNonNull(result.getBody()).contains("1000"));
        Assertions.assertTrue(Objects.requireNonNull(result.getBody()).contains("DANNON"));
    }
}