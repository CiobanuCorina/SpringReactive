package com.example.testreactive;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    RestTemplate restTemplate;
    public Flux<String> getFlux() {
        List<String> list = new ArrayList<>();
        list.add("{\"data1\":\"test\"}");
        list.add("{\"data2\":\"test\"}");
        list.add("{\"data3\":\"test\"}");
        Flux<String> flux = Flux.fromIterable(list).delayElements(Duration.ofSeconds(10));

        return flux.flatMap(elem -> {
            return Mono.defer(() -> Mono.just(this.getUpperCase(elem)));
        });
    }

    public String getUpperCase(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", "DEMO-API-KEY");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String url = "https://api.thecatapi.com/v1/images";
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
        }
        return "test";
    }
}
