package com.example.geologicalclassesservice.controller;

import com.example.geologicalclassesservice.entity.Status;
import com.example.geologicalclassesservice.mapper.SectionMapper;
import com.example.geologicalclassesservice.repository.SectionRepository;
import com.example.geologicalclassesservice.service.ExcelParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.example.geologicalclassesservice.util.UrlMappings.EXCEL_PARSER_MAPPING_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(EXCEL_PARSER_MAPPING_URL)
public class ExcelParserController {

    private final ExcelParserService excelParserService;
    private final SectionRepository service;
    private final SectionMapper sectionMapper;

    @PostMapping("/import")
    public ResponseEntity<Long> importFile(@RequestParam MultipartFile file) throws Exception {
        return ResponseEntity.ok(excelParserService.importSection(file));
    }

    @GetMapping("/import/{id}")
    public ResponseEntity<Status> importingStatus(@PathVariable long id) {
        return ResponseEntity.ok(excelParserService.getStatusById(id));
    }

    @GetMapping("/export")
    public ResponseEntity<Long> export() throws Exception {
        return ResponseEntity.ok(excelParserService.exportSection(service.findById(1L).get()));
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<Status> exportingStatus(@PathVariable long id) {
        return ResponseEntity.ok(excelParserService.getStatusById(id));
    }

    @GetMapping("/export/{id}/file")
    public ResponseEntity<String> getFileByJobId(@PathVariable long id) {
        return ResponseEntity.ok(excelParserService.getFileByJobId(id));
    }

}
