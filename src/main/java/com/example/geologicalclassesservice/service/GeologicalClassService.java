package com.example.geologicalclassesservice.service;

import com.example.geologicalclassesservice.request.GeologicalClassRequest;
import com.example.geologicalclassesservice.response.GeologicalClassResponse;
import java.util.List;

public interface GeologicalClassService {

  GeologicalClassResponse findById(Long id);

  void update(Long id, GeologicalClassRequest geologicalClassRequest);

  void add(GeologicalClassRequest geologicalClassRequest);

  List<GeologicalClassResponse> findAll(int page, int size);

  void deleteById(Long id);
}
