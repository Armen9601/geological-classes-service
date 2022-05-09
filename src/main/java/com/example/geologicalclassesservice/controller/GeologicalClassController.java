package com.example.geologicalclassesservice.controller;

import com.example.geologicalclassesservice.request.GeologicalClassRequest;
import com.example.geologicalclassesservice.response.GeologicalClassResponse;
import com.example.geologicalclassesservice.service.GeologicalClassService;
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
import static com.example.geologicalclassesservice.util.UrlMappings.GEOLOGICAL_MAPPING_URL;
import static com.example.geologicalclassesservice.util.UrlMappings.ONLY_SLASH;

@RequiredArgsConstructor
@RestController
@RequestMapping(GEOLOGICAL_MAPPING_URL)
public class GeologicalClassController {

    private final GeologicalClassService geologicalClassService;

    @PostMapping(ONLY_SLASH)
    public ResponseEntity<HttpStatus> save(
            @RequestBody GeologicalClassRequest geologicalClassRequest) {
        geologicalClassService.add(geologicalClassRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(BY_ID)
    public ResponseEntity<HttpStatus> update(
            @PathVariable Long id, @RequestBody GeologicalClassRequest geologicalClassRequest) {
        geologicalClassService.update(id, geologicalClassRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(BY_ID)
    public ResponseEntity<GeologicalClassResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(geologicalClassService.findById(id));
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        geologicalClassService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(ONLY_SLASH)
    public ResponseEntity<List<GeologicalClassResponse>> findAll(
            @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(geologicalClassService.findAll(page, size));
    }
}
