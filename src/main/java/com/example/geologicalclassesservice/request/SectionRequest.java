package com.example.geologicalclassesservice.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionRequest {

  private String name;
  private List<Long> geologicalClassesIds;
}
