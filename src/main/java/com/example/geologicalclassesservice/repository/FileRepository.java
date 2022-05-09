package com.example.geologicalclassesservice.repository;

import com.example.geologicalclassesservice.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findFileByJobId(long id);
}
