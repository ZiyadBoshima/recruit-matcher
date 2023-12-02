package com.ziyad.recruitingspring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void testExtractJsonWithData() {
        String options = "name, years of experience, and skills";
        String data = "name steven, experience 2 years, skills carpentry.";
        String expectedPrompt = "Extract " + options + " in JSON format. Text data: " + data;

        /*
        List<GPTResponse.Choice> list = new ArrayList<>(GPTResponse.Choice(1, new Message("User", "Response"))
        GPTResponse gptResponse = new GPTResponse(List<GPTResponse.Choice>().add(1, new Message("User", "Response"));
        gptResponse.getChoices().add(new GPTResponse.Choice(1, new Message("User", "Response")));

        when(gptService.generateResponse(expectedPrompt)).thenReturn(gptResponse);

        ResponseEntity<String> result = documentService.extractJson(options, data);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Response", result.getBody());
         */
    }
}
