package com.example.geologicalclassesservice.mapper;

import com.example.geologicalclassesservice.entity.Section;
import com.example.geologicalclassesservice.mapper.config.BaseMapper;
import com.example.geologicalclassesservice.request.SectionRequest;
import com.example.geologicalclassesservice.response.SectionResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionMapper implements BaseMapper<Section, SectionRequest, SectionResponse> {

  private final ModelMapper mapper;

  @Override
  public Section toEntity(SectionRequest sectionRequest) {
    return mapper.map(sectionRequest, Section.class);
  }

  @Override
  public SectionResponse toResponse(Section section) {
    return mapper.map(section, SectionResponse.class);
  }
}
