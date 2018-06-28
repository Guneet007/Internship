package com.example.API.UploadController;

import com.example.API.Entities.Orders;
import com.example.API.Entities.User;
import com.example.API.Services.UploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

@RequestMapping("/rest")
@RestController
public class UploadOrdersController {
    @Autowired
    private UploadService uploadservice;
    Orders orders=new Orders();

    @ApiOperation(value = "Upload mappings excel file", notes = "Uploads the excel file and stores on couchbase")

    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "File uploaded successfully"),

            @ApiResponse(code = 500, message = "Internal server error"),

            @ApiResponse(code = 400, message = "Bad request")

    })

    @RequestMapping(value = "/uploadOrders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public void uploadMappingsExcel (@ApiParam(value = "file", example = "order.ods", required = true)
                                     @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException,ParseException {

        Path path = null;
        for(MultipartFile file : Collections.singleton(multipartFile)) {
            path = Paths.get(file.getOriginalFilename());
            Files.write(path,file.getBytes());
        }

        FileInputStream file = new FileInputStream(path.toString());
        uploadservice.saveOrders(file);

    }

}

