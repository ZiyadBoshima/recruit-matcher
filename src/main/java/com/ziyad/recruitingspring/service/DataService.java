package com.ziyad.recruitingspring.service;

import com.ziyad.recruitingspring.model.chatgpt.GPTResponse;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.pdfbox.io.IOUtils.toByteArray;

@Service
public class DataService {

    @Autowired
    GPTService gptService;

    public String convertDocToText(MultipartFile file)  throws IOException {
        try {
            InputStream inputStream = file.getInputStream();

            byte[] pdfBytes = toByteArray(inputStream);

            PDDocument document = Loader.loadPDF(pdfBytes);

            PDFTextStripper textStripper = new PDFTextStripper();

            String content = textStripper.getText(document);

            document.close();

            return  content;
        } catch (IOException e) {
            return "Failed to read file";
        } catch (Exception e) {
            e.printStackTrace();
            return "An unexpected error occurred";
        }
    }

    public ResponseEntity<String> extractJson(String options, String data) {
        try {
            String prompt;

            if (!options.isEmpty() && !data.isEmpty()) {
                prompt = "Extract " + options + " in JSON format. Text data: " + data;
            }
            else if (!data.isEmpty()) {
                prompt = "Extract the data in JSON format: " + data;
            }
            else {
                return new ResponseEntity<String>("Invalid arguments!", HttpStatus.BAD_REQUEST);
            }

            GPTResponse response = gptService.generateResponse(prompt);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return new ResponseEntity<String>("No Response.", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<String>(response.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>("HTTP Client Error: " + e, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}