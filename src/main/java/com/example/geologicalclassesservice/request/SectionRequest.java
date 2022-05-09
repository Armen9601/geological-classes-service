package com.example.geologicalclassesservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionRequest {

    private String name;
    private List<Long> geologicalClassesIds;
}
