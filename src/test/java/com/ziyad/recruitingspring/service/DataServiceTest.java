package com.ziyad.recruitingspring.service;

import com.ziyad.recruitingspring.model.Message;
import com.ziyad.recruitingspring.model.chatgpt.GPTResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DataServiceTest {

    @InjectMocks private DataService dataService;

    @Mock
    private GPTService gptService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void convertDocToText_validPdf_true() throws IOException {
        Path pdfFilePath = Paths.get("src", "test", "resources", "dummy.pdf");

        InputStream pdfInputStream = Files.newInputStream(pdfFilePath);

        MockMultipartFile mockFile = new MockMultipartFile("dummy.pdf", pdfInputStream);

        String result = dataService.convertDocToText(mockFile);
        assertEquals("Dummy PDF file\n", result);
    }

    @Test
    public void convertDocToText_invalidPdf_false() throws IOException {
        Path pdfFilePath = Paths.get("src", "test", "resources", "invalid.doc");

        InputStream pdfInputStream = Files.newInputStream(pdfFilePath);

        MockMultipartFile mockFile = new MockMultipartFile("invalid.pdf", pdfInputStream);

        String result = dataService.convertDocToText(mockFile);
        assertEquals("Failed to read file", result);
    }

    @Test
    public void extractJson_validData_true() {
        String options = "name, years of experience, and skills";
        String data = "name steven, experience 2 years, skills carpentry and cage fighting.";

        GPTResponse mockResponse = new GPTResponse();
        GPTResponse.Choice mockChoice = new GPTResponse.Choice();
        mockChoice.setMessage(new Message("user", "Mock response content"));
        mockResponse.setChoices(Collections.singletonList(mockChoice));

        when(gptService.generateResponse(anyString())).thenReturn(mockResponse);

        ResponseEntity<String> result = dataService.extractJson(options, data);


        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(gptService, times(1)).generateResponse(anyString());
    }

    @Test
    public void extractJson_invalidData_false() {
        String options = "name, years of experience, and skills";
        String data = "";

        GPTResponse mockResponse = new GPTResponse();
        GPTResponse.Choice mockChoice = new GPTResponse.Choice();
        mockChoice.setMessage(new Message("user", "Mock response content"));
        mockResponse.setChoices(Collections.singletonList(mockChoice));

        when(gptService.generateResponse(anyString())).thenReturn(mockResponse);

        ResponseEntity<String> result = dataService.extractJson(options, data);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
