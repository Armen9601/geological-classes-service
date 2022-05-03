package com.example.geologicalclassesservice.service.impl;

import com.example.geologicalclassesservice.entity.GeologicClass;
import com.example.geologicalclassesservice.entity.Section;
import com.example.geologicalclassesservice.repository.SectionRepository;
import com.example.geologicalclassesservice.service.ExcelParserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ExcelParserServiceImpl implements ExcelParserService {

  private static final String TYPE =
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  private final SectionRepository sectionRepository;

  @Override
  @Async
  public Long importSection(MultipartFile file) throws Exception {
    if (!hasExcelFormat(file)) {
      throw new FileUploadException();
    }
    try {
      Workbook workbook = new XSSFWorkbook(file.getInputStream());
      Sheet sheet = workbook.getSheetAt(0);

      Iterator<Row> rows = sheet.iterator();
      List<Section> sections = new ArrayList<>();
      List<GeologicClass> geologicClasses = new ArrayList<>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }
        Iterator<Cell> cellsInRow = currentRow.iterator();
        Section section = new Section();
        int cellId = 0;
        while (cellsInRow.hasNext()) {

          Cell currentCell = cellsInRow.next();
          GeologicClass geologicClass = new GeologicClass();

          switch (cellId) {
            case 0:
              String sectionName = currentCell.getStringCellValue();
              section.setName(sectionName);
              break;
            case 1:
              geologicClass.setName(currentCell.getStringCellValue());
              break;
            case 2:
              geologicClass.setCode(currentCell.getStringCellValue());
              break;
            default:
              geologicClasses.add(geologicClass);
              break;
          }
          cellId++;
        }
        section.setGeologicClasses(geologicClasses);
      }
      workbook.close();
      sectionRepository.saveAll(sections);
      return Thread.currentThread().getId();
    } catch (IOException e) {
      throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
    }

  }

  private boolean hasExcelFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }
}
