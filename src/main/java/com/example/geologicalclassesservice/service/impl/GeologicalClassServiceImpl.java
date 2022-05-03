package com.example.geologicalclassesservice.service.impl;

import com.example.geologicalclassesservice.entity.GeologicClass;
import com.example.geologicalclassesservice.exception.EntityNotFoundException;
import com.example.geologicalclassesservice.mapper.GeologicalClassesMapper;
import com.example.geologicalclassesservice.repository.GeologicClassRepository;
import com.example.geologicalclassesservice.request.GeologicalClassRequest;
import com.example.geologicalclassesservice.response.GeologicalClassResponse;
import com.example.geologicalclassesservice.service.GeologicalClassService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeologicalClassServiceImpl implements GeologicalClassService {

  private final ModelMapper modelMapper;
  private final GeologicalClassesMapper geologicalClassesMapper;
  private final GeologicClassRepository geologicClassRepository;

  @Override
  public GeologicalClassResponse findById(Long id) {
    return geologicClassRepository
        .findById(id)
        .map(geologicalClassesMapper::toResponse)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void update(Long id, GeologicalClassRequest geologicalClassRequest) {
    GeologicClass geologicClass =
        geologicClassRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    modelMapper.map(geologicalClassRequest, geologicClass);
  }

  @Override
  @Transactional
  public void add(GeologicalClassRequest geologicalClassRequest) {
    if (geologicClassRepository.findByCode(geologicalClassRequest.getCode()).isEmpty()
        && geologicClassRepository.findByName(geologicalClassRequest.getName()).isEmpty()) {
      geologicClassRepository.save(geologicalClassesMapper.toEntity(geologicalClassRequest));
    }
  }

  @Override
  public List<GeologicalClassResponse> findAll(int page, int size) {
    return geologicClassRepository.findAll(PageRequest.of(page, size)).stream()
        .map(geologicalClassesMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    geologicClassRepository.findById(id).ifPresent(geologicClassRepository::delete);
  }
}
