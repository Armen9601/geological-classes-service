package com.example.geologicalclassesservice.repository;

import com.example.geologicalclassesservice.entity.GeologicClass;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeologicClassRepository extends JpaRepository<GeologicClass, Long> {

  Optional<GeologicClass> findByCode(String code);

  Optional<GeologicClass> findByName(String name);
}
