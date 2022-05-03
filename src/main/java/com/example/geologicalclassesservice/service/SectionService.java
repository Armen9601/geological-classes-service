package com.example.geologicalclassesservice.service;

import com.example.geologicalclassesservice.request.SectionRequest;
import com.example.geologicalclassesservice.response.SectionResponse;
import java.util.List;

public interface SectionService {

  SectionResponse findById(Long id);

  void update(Long id, SectionRequest sectionRequest);

  void add(SectionRequest sectionRequest);

  List<SectionResponse> findAll(int page, int size);

  void deleteById(Long id);
}
