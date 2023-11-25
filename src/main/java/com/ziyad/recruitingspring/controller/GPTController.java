package com.ziyad.recruitingspring.controller;

import com.ziyad.recruitingspring.model.GPTRequest;
import com.ziyad.recruitingspring.model.GPTResponse;
import com.ziyad.recruitingspring.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GPTController {

    DocumentService documentService;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    public GPTController() { documentService = new DocumentService(); }

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        GPTRequest request = new GPTRequest(model, prompt);
        request.setN(1);
        request.setTemperature(1);
        GPTResponse response = restTemplate.postForObject(apiUrl, request, GPTResponse.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response.";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }

    @PostMapping("/structure-resume")
    public String structureResume(@RequestParam(name = "file", required = true) MultipartFile file) {
        String resume;
        if (!file.isEmpty()) {
            resume = documentService.convertDocToText(file);
        } else return "File is empty!";
        String prompt = "Structure my resume in json format: " + resume;

        GPTRequest request = new GPTRequest(model, prompt);
        request.setN(1);
        request.setTemperature(1);
        GPTResponse response = restTemplate.postForObject(apiUrl, request, GPTResponse.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response.";
        }
        return response.getChoices().get(0).getMessage().getContent();
    }
}
