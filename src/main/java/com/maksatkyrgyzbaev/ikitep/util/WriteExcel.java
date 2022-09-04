package com.maksatkyrgyzbaev.ikitep.util;

import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Component
public class WriteExcel {

    public void downloadFile(HttpServletResponse response, List<?> entity) throws IOException {
        String className = entity.get(0).getClass().getSimpleName();

        response.setHeader("Content-Disposition", "attachment; filename=" + className + "s.XLSX");
        response.setContentType("application/octet-stream");

        File file = createFileExcel(new File(className + "s.XLSX"), entity);

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             ServletOutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int byteRead = -1;
            while ((byteRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
    }

    private File createFileExcel(File file, List<?> entities) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = createSheet(workbook);
        createHeader(workbook, sheet, entities.get(0).getClass().getSimpleName());
        createCells(workbook, sheet, entities);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private Sheet createSheet(XSSFWorkbook workbook) {
        Sheet sheet = workbook.createSheet("List1");
        for (int i = 0; i < 5; i++) {
            sheet.setColumnWidth(i, 10000);
        }
        return sheet;
    }

    // Работа над заголовком
    private void createHeader(XSSFWorkbook workbook, Sheet sheet, String className) {
        Row header = sheet.createRow(0);

        // Стиль заголовка
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Стиль шрифта
        XSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);

        // Создаем ячейки, даем им номера и вносим текст
        String[] headerValue = null;
        if (className.equals("Book"))
            headerValue = new String[]{"Серийный номер", "Автор", "Название книги", "Количество бронирований", "Школа"};
        else
            headerValue = new String[]{"Логин", "Пароль", "Роль", "Полное имя", "Школа"};

        for (int i = 0; i < headerValue.length; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerValue[i]);
            headerCell.setCellStyle(headerStyle);
        }
    }

    // Добавляем строчки
    private void createCells(XSSFWorkbook workbook, Sheet sheet, List<?> entities) {

        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        if (entities.get(0).getClass().getSimpleName().equals("Book")) {
            List<Book> books = (List<Book>) entities;
            for (var i = 0; i < entities.size(); i++) {
                Book book = books.get(i);
                Row row = sheet.createRow(i + 1);

                Cell cell = row.createCell(0);
                cell.setCellValue(book.getSerialNumber());

                cell = row.createCell(1);
                cell.setCellValue(book.getAuthor());

                cell = row.createCell(2);
                cell.setCellValue(book.getBookName());

                cell = row.createCell(3);
                cell.setCellValue(book.getLikes());

                cell = row.createCell(4);
                cell.setCellValue(book.getSchool().getSchoolName());

            }
        } else {
            List<User> users = (List<User>) entities;
            for (var i = 0; i < users.size(); i++) {
                User user = users.get(i);
                Row row = sheet.createRow(i + 1);

                Cell cell = row.createCell(0);
                cell.setCellValue(user.getUsername());

                cell = row.createCell(1);
                cell.setCellValue(" - ");

                cell = row.createCell(2);
                cell.setCellValue(user.getRole().name());

                cell = row.createCell(3);
                cell.setCellValue(user.getFullName());

                cell = row.createCell(4);
                cell.setCellValue(user.getSchool().getSchoolName());

            }
        }


    }


}
