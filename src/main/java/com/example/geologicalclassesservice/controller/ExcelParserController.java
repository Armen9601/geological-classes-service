package com.example.geologicalclassesservice.controller;

import static com.example.geologicalclassesservice.util.UrlMappings.EXCEL_PARSER_MAPPING_URL;
import com.example.geologicalclassesservice.service.ExcelParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(EXCEL_PARSER_MAPPING_URL)
public class ExcelParserController {

  private final ExcelParserService excelParserService;

  @PostMapping("/import")
  public ResponseEntity<Long> importFile(@RequestParam MultipartFile file) throws Exception {
    return ResponseEntity.ok(excelParserService.importSection(file));
  }
}
