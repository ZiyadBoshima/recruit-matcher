package com.ziyad.recruitingspring.controller;

import com.ziyad.recruitingspring.model.GPTRequest;
import com.ziyad.recruitingspring.model.GPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GPTController {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        GPTRequest request = new GPTRequest(model, prompt);
        System.out.println("Request created " + request);
        request.setN(1);
        request.setTemperature(1);
        GPTResponse response = restTemplate.postForObject(apiUrl, request, GPTResponse.class);
        System.out.println("Response sent");
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response.";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
