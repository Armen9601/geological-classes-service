package com.example.geologicalclassesservice.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "section")
public class Section{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "section_geological_classes",
      joinColumns = {@JoinColumn(name = "section_id")},
      inverseJoinColumns = {@JoinColumn(name = "geological_class_id")})
  private List<GeologicClass> geologicClasses;
}
