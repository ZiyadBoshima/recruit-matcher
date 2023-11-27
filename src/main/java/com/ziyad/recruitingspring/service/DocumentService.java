package com.ziyad.recruitingspring.service;

import com.ziyad.recruitingspring.model.chatgpt.GPTRequest;
import com.ziyad.recruitingspring.model.chatgpt.GPTResponse;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.pdfbox.io.IOUtils.toByteArray;

@Service
public class DocumentService {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    public String convertDocToText(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            byte[] pdfBytes = toByteArray(inputStream);

            PDDocument document = Loader.loadPDF(pdfBytes);

            PDFTextStripper textStripper = new PDFTextStripper();

            String content = textStripper.getText(document);

            document.close();

            return  content;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read file";
        }
    }

    public ResponseEntity<String> extractJson(String options, String data) {
        String prompt;
        if (!options.isEmpty() && !data.isEmpty()) {
            prompt = "Extract " + options + " in JSON format. Text data: " + data;
        }
        else if (!data.isEmpty())
        {
            prompt = "Extract the data in JSON format: " + data;
        }
        else return new ResponseEntity<String>("No arguments!", HttpStatus.BAD_REQUEST);

        GPTRequest request = new GPTRequest(model, prompt);
        request.setN(1);
        request.setTemperature(1);

        try {
            GPTResponse response = restTemplate.postForObject(apiUrl, request, GPTResponse.class);
            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return new ResponseEntity<String>("No Response.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<String>(response.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
        } catch (HttpClientErrorException e)
        {
            return new ResponseEntity<String>("Exception Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
