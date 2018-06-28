package com.example.API.Services;

import com.example.API.Entities.*;
import com.example.API.Repository.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Service
public class UploadService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    NutrientRepository nutrientRepository;


    Orders orders=new Orders();
    Product product=new Product();
    Ingredients ingredients=new Ingredients();
    Nutrients nutrients=new Nutrients();


    public String saveNutrients(FileInputStream file) throws IOException {
        int i=0,j=0;

        HSSFWorkbook workbook = new HSSFWorkbook(file);

        HSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                if(i==0)
                    break;
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                switch (j){
                    case 0:nutrients.setName(cellValue);
                        break;
                    case 1: nutrients.setCalories(Double.parseDouble(cellValue));
                        break;

                    default:break;
                }
                j++;
                System.out.print(cellValue + "\t");
            }
            if(i!=0)
                nutrientRepository.save(nutrients);
            i++;
            j=0;
            System.out.println();

        }

        return ("Success");


    }

    public String saveUser(FileInputStream file) throws IOException {
        int i=0,j=0;
        HSSFWorkbook workbook = new HSSFWorkbook(file);

        HSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            User user=new User();
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                if(i==0)
                    break;
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                switch (j){
                    case 0:user.setUsername(cellValue);
                        break;
                    case 1: user.setPhoneNumber(Long.parseLong(cellValue));
                        break;
                    case 2: user.setAddress(cellValue);
                        break;

                    default:break;
                }
                j++;

            }
            if(i!=0)
                userRepository.save(user);
            i++;
            j=0;

        }
        return ("Success");



    }

    public String saveOrders(FileInputStream file) throws IOException,ParseException {
        int i=0,j=0;

        HSSFWorkbook workbook = new HSSFWorkbook(file);

        HSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                if(i==0)
                    break;
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                switch (j){
                    case 0:orders.setDate(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss ").parse(cellValue));
                        break;

                    default:break;
                }
                j++;
                System.out.print(cellValue + "\t");
            }
            if (i!=0)
                ordersRepository.save(orders);
            i++;
            j=0;
            System.out.println();

        }
        return ("Success");


    }

    public String saveProduct(FileInputStream file) throws IOException {
        int i=0,j=0;

        HSSFWorkbook workbook = new HSSFWorkbook(file);

        HSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                if(i==0)
                    break;
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                switch (j){
                    case 0:product.setPrice(Double.parseDouble(cellValue));
                        break;
                    default:break;
                }
                j++;
                System.out.print(cellValue + "\t");
            }
            if(i!=0)
                productRepository.save(product);
            i++;
            j=0;
            System.out.println();

        }
        return ("Success");


    }


    public String saveIngredients(FileInputStream file) throws IOException {
        int i=0,j=0;

        HSSFWorkbook workbook = new HSSFWorkbook(file);

        HSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                if(i==0)
                    break;
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                switch (j){
                    case 0:ingredients.setName(cellValue);
                        break;
                    default:break;
                }
                j++;
                System.out.print(cellValue + "\t");
            }
            if(i!=0)
                ingredientRepository.save(ingredients);
            i++;
            j=0;
            System.out.println();

        }


        return ("Success");
    }

}
