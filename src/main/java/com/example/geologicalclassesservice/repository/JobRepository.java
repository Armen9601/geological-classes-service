package com.example.geologicalclassesservice.repository;

import com.example.geologicalclassesservice.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
