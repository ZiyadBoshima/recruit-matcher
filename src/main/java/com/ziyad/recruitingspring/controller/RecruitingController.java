package com.ziyad.recruitingspring.controller;

import com.ziyad.recruitingspring.service.DocumentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RecruitingController {
    DocumentService documentService;

    RecruitingController() {
        documentService = new DocumentService();
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam(name = "file", required = false) MultipartFile file) {
        if (!file.isEmpty()) {
           return documentService.convertDocToText(file);
        } else return "File is empty!";
    }
}
