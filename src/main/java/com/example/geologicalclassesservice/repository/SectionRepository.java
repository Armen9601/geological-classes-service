package com.example.geologicalclassesservice.repository;

import com.example.geologicalclassesservice.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(nativeQuery = true, value = "select sc.id, sc.name\n" +
            "from section sc\n" +
            "         left join\n" +
            "     section_geological_classes sgc on\n" +
            "         (sgc.geological_class_id =" +
            " (select id from geological_class where geological_class.code =:code))\n" +
            "where section_id = sc.id;")
    List<Section> findAllByGeologicClassesCode(@Param("code") String code);

}
