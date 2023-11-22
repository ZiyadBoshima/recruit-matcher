package com.ziyad.recruitingspring.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.pdfbox.io.IOUtils.toByteArray;

public class DocumentService {
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
}
