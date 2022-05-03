package com.example.geologicalclassesservice.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionResponse {

  private String name;
  private List<GeologicalClassResponse> geologicalClassResponses;
}
