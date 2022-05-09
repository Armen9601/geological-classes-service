package com.example.geologicalclassesservice.mapper;

import com.example.geologicalclassesservice.entity.Section;
import com.example.geologicalclassesservice.mapper.config.BaseMapper;
import com.example.geologicalclassesservice.request.SectionRequest;
import com.example.geologicalclassesservice.response.GeologicalClassResponse;
import com.example.geologicalclassesservice.response.SectionResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionMapper implements BaseMapper<Section, SectionRequest, SectionResponse> {

    private final ModelMapper mapper;
    private final GeologicalClassesMapper geologicalClassesMapper;

    @Override
    public Section toEntity(SectionRequest sectionRequest) {
        return mapper.map(sectionRequest, Section.class);
    }

    @Override
    public SectionResponse toResponse(Section section) {
        SectionResponse response = mapper.map(section, SectionResponse.class);
        List<GeologicalClassResponse> geologicalClassResponses =
                section.getGeologicClasses().stream().map(geologicalClassesMapper::toResponse).collect(Collectors.toList());
        response.setGeologicalClassResponses(geologicalClassResponses);
        return response;
    }
}
