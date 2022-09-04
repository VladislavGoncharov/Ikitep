package com.maksatkyrgyzbaev.ikitep.util;

import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.entity.Role;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReadExcel {

    public List<?> uploadFile(MultipartFile multipartFile, School school, String switcher) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        try (InputStream inputStreamReader = multipartFile.getInputStream();
             OutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStreamReader, outputStream);
        }
        Map<Integer, List<String>> data = new HashMap<>();

        Workbook workbook = loadWorkbook(file);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            data = processSheet(sheet);
        }

        file.delete();

        data.remove(0); // удаление заголовка
        if (switcher.equals("book")) {
            Map<Integer, Book> newBooks = data.entrySet().stream()
                    .collect(Collectors.toMap(
                                    key -> key.getKey(),
                                    value -> value.getValue().stream()
                                            .reduce((accum, string) -> accum + "&&&" + string)
                                            .map(string -> {
                                                String[] userArray = string.split("&&&");
                                                System.out.println(Arrays.toString(userArray));
                                                return Book.builder()
                                                        .serialNumber(userArray[0])
                                                        .author(userArray[1])
                                                        .bookName(userArray[2])
                                                        .school(school)
                                                        .build();
                                            })
                                            .get()
                            )
                    );
            return new ArrayList<>(newBooks.values());

        } else {
            Map<Integer, User> newUsers = data.entrySet().stream()
                    .collect(Collectors.toMap(
                                    key -> key.getKey(),
                                    value -> value.getValue().stream()
                                            .reduce((accum, string) -> accum + "&&&" + string)
                                            .map(string -> {
                                                String[] userArray = string.split("&&&");
                                                System.out.println(Arrays.toString(userArray));

                                                return User.builder()
                                                        .username(userArray[0])
                                                        .password(new BCryptPasswordEncoder().encode(userArray[1]))
                                                        .role(Role.STUDENT)
                                                        .fullName(userArray[3])
                                                        .school(school)
                                                        .build();
                                            })
                                            .get()
                            )
                    );
            return new ArrayList<>(newUsers.values());
        }
    }

    private Workbook loadWorkbook(File file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getName()).toLowerCase(Locale.ROOT);
        FileInputStream inputStream = new FileInputStream(file);
        switch (extension) {
            case "xls":
                // old format
                return new HSSFWorkbook(inputStream);
            case "xlsx":
                // new format
                return new XSSFWorkbook(inputStream);
            default:
                throw new IOException("Файл с расширением \"" + extension + "\" не поддерживается");
        }
    }

    private Map<Integer, List<String>> processSheet(Sheet sheet) {
        System.out.println("Sheet: " + sheet.getSheetName());
        Map<Integer, List<String>> data = new HashMap<>();
        Iterator<Row> iterator = sheet.rowIterator();
        for (int rowIndex = 0; iterator.hasNext(); rowIndex++) {
            Row row = iterator.next();
            processRow(data, rowIndex, row);
        }
        return data;

    }

    private void processRow(Map<Integer, List<String>> data, int rowIndex, Row row) {
        data.put(rowIndex, new ArrayList<>());
        for (Cell cell : row) {
            processCell(cell, data.get(rowIndex));
        }
    }

    private void processCell(Cell cell, List<String> dataRow) {
        switch (cell.getCellType()) {
            case STRING:
                dataRow.add(cell.getStringCellValue());
                break;
            case NUMERIC:
                dataRow.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
                break;
            default:
                dataRow.add(" ");
        }
    }
}
