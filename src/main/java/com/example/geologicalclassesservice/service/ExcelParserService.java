package com.example.geologicalclassesservice.service;

import com.example.geologicalclassesservice.entity.Section;
import com.example.geologicalclassesservice.entity.Status;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelParserService {

    Long importSection(MultipartFile file) throws Exception;

    Long exportSection(Section section) throws Exception;

    Status getStatusById(long id);

    String getFileByJobId(long id);
}
