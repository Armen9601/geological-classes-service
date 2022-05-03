package com.example.geologicalclassesservice.mapper;

import com.example.geologicalclassesservice.entity.GeologicClass;
import com.example.geologicalclassesservice.mapper.config.BaseMapper;
import com.example.geologicalclassesservice.request.GeologicalClassRequest;
import com.example.geologicalclassesservice.response.GeologicalClassResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeologicalClassesMapper
    implements BaseMapper<GeologicClass, GeologicalClassRequest, GeologicalClassResponse> {

  private final ModelMapper mapper;

  @Override
  public GeologicClass toEntity(GeologicalClassRequest geologicalClassRequest) {
    return mapper.map(geologicalClassRequest, GeologicClass.class);
  }

  @Override
  public GeologicalClassResponse toResponse(GeologicClass geologicClass) {
    return mapper.map(geologicClass, GeologicalClassResponse.class);
  }
}
