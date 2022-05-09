package com.example.geologicalclassesservice.service.impl;

import com.example.geologicalclassesservice.entity.GeologicClass;
import com.example.geologicalclassesservice.entity.Job;
import com.example.geologicalclassesservice.entity.Section;
import com.example.geologicalclassesservice.entity.Status;
import com.example.geologicalclassesservice.exception.ExportingInProcessException;
import com.example.geologicalclassesservice.exception.FileNotFoundException;
import com.example.geologicalclassesservice.repository.FileRepository;
import com.example.geologicalclassesservice.repository.GeologicClassRepository;
import com.example.geologicalclassesservice.repository.JobRepository;
import com.example.geologicalclassesservice.repository.SectionRepository;
import com.example.geologicalclassesservice.service.ExcelParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class ExcelParserServiceImpl implements ExcelParserService {

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private static final String TYPE =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String FILE_PATH = "newFile.xlsx";
    private final SectionRepository sectionRepository;
    private final GeologicClassRepository geologicClassRepository;
    private final JobRepository jobRepository;
    private final FileRepository fileRepository;

    @Override
    @Async
    public Long importSection(MultipartFile file) throws Exception {
        if (!hasExcelFormat(file)) {
            throw new FileUploadException();
        }
        long id = Thread.currentThread().getId();
        jobRepository.save(new Job(id, Status.IN_PROGRESS));
        try {
            List<Section> sections = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            for (int index = firstRow + 1; index <= lastRow; index++) {
                List<GeologicClass> geologicClasses = new ArrayList<>();
                Section section = new Section();
                Row row = sheet.getRow(index);
                section.setName(row.getCell(row.getFirstCellNum()).toString());
                for (int cellIndex = 1; cellIndex < (row.getLastCellNum()); cellIndex += 2) {
                    GeologicClass geologicClass = new GeologicClass();
                    geologicClass.setName(row.getCell(row.getFirstCellNum() + cellIndex).toString());
                    geologicClass.setCode(row.getCell(row.getFirstCellNum() + cellIndex + 1).toString());
                    geologicClasses.add(geologicClass);
                    geologicClassRepository.save(geologicClass);
                }
                section.setGeologicClasses(geologicClasses);
                sections.add(section);
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(mapper.writeValueAsString(section));
            }
            workbook.close();
            sectionRepository.saveAll(sections);
            jobRepository.save(new Job(id, Status.DONE));
            return id;
        } catch (IOException e) {
            jobRepository.save(new Job(id, Status.ERROR));
            throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
        }
    }

    private boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    @Override
    @Async
    public Long exportSection(Section section) {
        if (section == null) {
            throw new EntityNotFoundException();
        }
        long id = Thread.currentThread().getId();
        jobRepository.save(new Job(id, Status.IN_PROGRESS));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(section.getName());
            int rownum = 1;
            Row row = sheet.createRow(rownum);
            Cell cell = row.createCell(0);
            cell.setCellValue(section.getName());
            createList(section.getGeologicClasses(), row);
            String pathname = FILE_PATH + CurrentDateFunction.NAME;
            FileOutputStream out = new FileOutputStream(new File(pathname));// file name with path
            fileRepository.save(com.example.geologicalclassesservice.entity.File.builder()
                    .filePath(pathname)
                    .jobId(id)
                    .build());
            workbook.write(out);
            out.close();
            jobRepository.save(new Job(id, Status.DONE));
            return id;
        } catch (Exception e) {
            jobRepository.save(new Job(id, Status.ERROR));
            throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
        }
    }

    @Override
    public String getFileByJobId(long id) {
        Job job = jobRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (job.getStatus().equals(Status.IN_PROGRESS)) {
            throw new ExportingInProcessException("exporting is in process");
        }
        return fileRepository.findFileByJobId(id).orElseThrow(FileNotFoundException::new).getFilePath();
    }

    @Override
    public Status getStatusById(long id) {
        return jobRepository.findById(id).orElseThrow(EntityNotFoundException::new).getStatus();
    }

    private static void createList(List<GeologicClass> geologicClasses, Row row) // creating cells for each row
    {
        int index = 1;
        for (Iterator<GeologicClass> geologicClass = geologicClasses.iterator();
             geologicClass.hasNext(); index += 2) {
            Cell cell = row.createCell(index);
            GeologicClass next = geologicClass.next();
            cell.setCellValue(next.getName());
            cell = row.createCell(index + 1);
            cell.setCellValue(next.getCode());
        }
    }
}
