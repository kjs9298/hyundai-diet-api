package com.hyundai.diet.service;

import com.hyundai.diet.domain.Diet;
import com.hyundai.diet.enums.DietSubType;
import com.hyundai.diet.enums.DietType;
import com.hyundai.diet.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelService {
    private final String FILE_NAME = "20180326_diet.xlsx";

    private Map<String, Map<DietSubType, Diet>> dietMap;

    @PostConstruct
    public void init() {
        dietMap = makeDietMap(FILE_NAME);

    }

    public String getDiet(DietType dietType) {
        String key = makeKeyOnCurrentDateTime(dietType);

        if(dietMap.containsKey(key) == false) {
            return "I don't support on weekend.";

        }

        Map<DietSubType, Diet> subTypeDietMap = dietMap.get(key);

        StringBuilder builder = new StringBuilder();

        // TODO : Fix the calories
        subTypeDietMap.forEach((subType, diet) -> {
            builder.append("[" + subType.getDesc() + "]\n");
            builder.append(diet.getDietText());
            builder.append("\n");

        });

        String lastComment;
        switch (dietType) {
            case BREAKFAST:
                lastComment = "Happy 3rd anniversary with you !";
                break;
            case LUNCH:
                lastComment = "Thanks for your love.";
                break;
            case DINNER:
                lastComment = "I love to drive on your IONIQ. Let's go with this weekend !";
                break;
            default:
                lastComment = "오늘 하루도 좋은 하루 되세욧 !";
                break;
        }

        return builder.append(lastComment).toString().replace("\n\r", "\n");

    }

    private Map<String, Map<DietSubType, Diet>> makeDietMap(String fileName) {
        Workbook workBook = getWorkBook(fileName);
        Sheet sheet = workBook.getSheetAt(0);

        int rowCount = sheet.getPhysicalNumberOfRows();

        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getPhysicalNumberOfCells();

        List<String> headers = new ArrayList<>();
        for(int colNum = 0; colNum<colCount; ++colNum) {
            String content = headerRow.getCell(colNum).getStringCellValue();
            headers.add(content);

        }

        Map<String, Map<DietSubType, Diet>> resultMap = new HashMap<>();

        for(int rowNum = 1; rowNum<rowCount; ++rowNum) {
            Row row = sheet.getRow(rowNum);

            if(Objects.isNull(row)) {
                continue;

            }

            DietType dietType = DietType.getFromDesc(row.getCell(0).getStringCellValue());
            DietSubType dietSubType = DietSubType.getFromDesc(row.getCell(1).getStringCellValue());

            if(isDietText(dietType, dietSubType)) {
                for (int colNum = 2; colNum < colCount; ++colNum) {
                    String header = headers.get(colNum);
                    String key = makeKeyByHeader(header, dietType);

                    Map<DietSubType, Diet> subTypeDietMap = new LinkedHashMap<>();

                    if(resultMap.containsKey(key)) {
                        subTypeDietMap = resultMap.get(key);

                    }

                    if(Objects.isNull(row.getCell(colNum)) == false && StringUtils.isNotBlank(row.getCell(colNum).getStringCellValue())) {
                        Diet diet = new Diet(row.getCell(colNum).getStringCellValue());
                        subTypeDietMap.put(dietSubType, diet);

                        resultMap.put(key, subTypeDietMap);

                    }

                }

            }

        }

        return resultMap;

    }

    private boolean isDietText(DietType dietType, DietSubType subType) {
        if(Objects.isNull(dietType) || Objects.isNull(subType)) {
            return false;

        }
        return true;

    }

    private String makeKeyByHeader(String header, DietType dietType) {
        String date = Arrays.asList(header.split(" ")).get(0);
        String prefixDateTime = DateTimeUtils.convertToPrefixKey(date);

        return prefixDateTime + "_" + dietType.name();

    }

    private String makeKeyOnCurrentDateTime(DietType dietType) {
        String prefix = DateTimeUtils.getPrefixKeyByPreviousDateTime();

        return prefix + "_" + dietType.name();

    }


    private Workbook getWorkBook(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(fileName);

        Workbook workBook = null;

        if(fileName.toUpperCase().endsWith(".XLS")) {
            try {
                workBook = new HSSFWorkbook(inputStream);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);

            }
        } else if (fileName.toUpperCase().endsWith(".XLSX")) {
            try {
                workBook = new XSSFWorkbook(inputStream);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);

            }
        }

        return workBook;

    }


}
