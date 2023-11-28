package com.ziyad.recruitingspring.service;

import com.ziyad.recruitingspring.model.chatgpt.GPTRequest;
import com.ziyad.recruitingspring.model.chatgpt.GPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GPTService {

    private final RestTemplate restTemplate;
    private final String model;
    private final String apiUrl;

    @Autowired
    public GPTService(@Qualifier("openaiRestTemplate") RestTemplate restTemplate,
                      @Value("${openai.model}") String model,
                      @Value("${openai.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.model = model;
        this.apiUrl = apiUrl;
    }


    public GPTResponse generateResponse(String prompt) {
        GPTRequest request = new GPTRequest(model, prompt);
        request.setN(1);
        request.setTemperature(1);

        return restTemplate.postForObject(apiUrl, request, GPTResponse.class);
    }
}