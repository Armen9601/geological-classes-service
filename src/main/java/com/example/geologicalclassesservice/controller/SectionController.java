package com.example.geologicalclassesservice.controller;

import com.example.geologicalclassesservice.request.SectionRequest;
import com.example.geologicalclassesservice.response.SectionResponse;
import com.example.geologicalclassesservice.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.geologicalclassesservice.util.UrlMappings.BY_ID;
import static com.example.geologicalclassesservice.util.UrlMappings.GET_SECTIONS_BY_CODE;
import static com.example.geologicalclassesservice.util.UrlMappings.ONLY_SLASH;
import static com.example.geologicalclassesservice.util.UrlMappings.SECTIONS_MAPPING_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(SECTIONS_MAPPING_URL)
public class SectionController {

    private final SectionService sectionService;

    @PostMapping(ONLY_SLASH)
    public ResponseEntity<HttpStatus> save(@RequestBody SectionRequest sectionRequest) {
        sectionService.add(sectionRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(BY_ID)
    public ResponseEntity<HttpStatus> update(
            @PathVariable Long id, @RequestBody SectionRequest sectionRequest) {
        sectionService.update(id, sectionRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(BY_ID)
    public ResponseEntity<SectionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.findById(id));
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        sectionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(ONLY_SLASH)
    public ResponseEntity<List<SectionResponse>> findAll(
            @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(sectionService.findAll(page, size));
    }

    @GetMapping(GET_SECTIONS_BY_CODE)
    public ResponseEntity<List<SectionResponse>> findByGeologicalClassCode(@PathVariable String code) {
        return ResponseEntity.ok(sectionService.findAllByGeologicClassesCode(code));
    }
}
