package com.example.geologicalclassesservice.service.impl;

import com.example.geologicalclassesservice.entity.GeologicClass;
import com.example.geologicalclassesservice.entity.Section;
import com.example.geologicalclassesservice.exception.EntityNotFoundException;
import com.example.geologicalclassesservice.mapper.SectionMapper;
import com.example.geologicalclassesservice.repository.GeologicClassRepository;
import com.example.geologicalclassesservice.repository.SectionRepository;
import com.example.geologicalclassesservice.request.SectionRequest;
import com.example.geologicalclassesservice.response.SectionResponse;
import com.example.geologicalclassesservice.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final ModelMapper modelMapper;
    private final SectionMapper sectionMapper;
    private final SectionRepository sectionRepository;
    private final GeologicClassRepository geologicClassRepository;

    @Override
    public SectionResponse findById(Long id) {
        return sectionRepository
                .findById(id)
                .map(sectionMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void update(Long id, SectionRequest sectionRequest) {
        Section section = sectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<GeologicClass> geologicClasses = section.getGeologicClasses();
        modelMapper.map(sectionRequest, section);
        for (Long geologicalClassesId : sectionRequest.getGeologicalClassesIds()) {
            Optional<GeologicClass> byId = geologicClassRepository.findById(geologicalClassesId);
            byId.ifPresent(geologicClasses::add);
        }
    }

    @Override
    @Transactional
    public void add(SectionRequest sectionRequest) {
        sectionRepository.save(sectionMapper.toEntity(sectionRequest));
    }

    @Override
    public List<SectionResponse> findAll(int page, int size) {
        return sectionRepository.findAll(PageRequest.of(page, size)).stream()
                .map(sectionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        sectionRepository.findById(id).ifPresent(sectionRepository::delete);
    }

    @Override
    public List<SectionResponse> findAllByGeologicClassesCode(String code) {
        return sectionRepository.findAllByGeologicClassesCode(code).stream()
                .map(sectionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
