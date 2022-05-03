package com.example.geologicalclassesservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelParserService {

  Long importSection(MultipartFile file) throws Exception;
}
