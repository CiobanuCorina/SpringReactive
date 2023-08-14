package com.example.testreactive;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;
    @GetMapping(value = "/test",produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testFlux(HttpServletResponse response) {
        response.addHeader("X-Content-Type-Options", "nosniff");
        response.addHeader("Vary", "Origin");
        response.addHeader("Vary", "Access-Control-Request-Method");
        response.addHeader("Vary", "Access-Control-Request-Headers");
        response.addHeader("X-XSS-Protection", "0");
        response.addHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");
        response.addHeader("X-Frame-Options", "sameorigin");
        return testService.getFlux();
    }
}
