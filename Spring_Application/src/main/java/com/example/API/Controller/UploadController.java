package com.example.API.Controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;

@RestController
@MultipartConfig(location="/tmp",
        fileSizeThreshold=0,
        maxFileSize=5242880,       // 5 MB
        maxRequestSize=20971520)   // 20 MB
public class UploadController {

    @ApiOperation(value = "Upload mappings excel file", notes = "Uploads the excel file and stores on couchbase")

    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "File uploaded successfully"),

            @ApiResponse(code = 500, message = "Internal server error"),

            @ApiResponse(code = 400, message = "Bad request")

    })

    @RequestMapping(value = "/upload", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
        public void uploadMappingsExcel(
            @ApiParam(value = "file", example = "user.ods", required = true)
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException{
        Path  path = null;
        for(MultipartFile file : Collections.singleton(multipartFile)) {
            path = Paths.get(file.getOriginalFilename());
            Files.write(path,file.getBytes());
        }

        FileInputStream file = new FileInputStream(path.toString());
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            }
            System.out.println();

        }
        
        }

    }





