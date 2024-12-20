package be.ifapme.election.utils;


import be.ifapme.election.model.Election;
import be.ifapme.election.model.Personne;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class CreateFile {

    private static final String filePath = "file/";

    private static <T> String createFileName(List<T> objects) throws IllegalAccessException {

        Class<?> clazz = objects.getFirst().getClass();
        Field[] fields = clazz.getDeclaredFields();
        fields[0].setAccessible(true);
        return fields[0].get(objects.getFirst()).toString();

    }

    public static <T> void createXlsx(List<T> objects) throws IllegalAccessException, IOException {

        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("List of objects is null or empty");
        }

        Class<?> clazz = objects.getFirst().getClass();
        Field[] fields = clazz.getDeclaredFields();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(createFileName(objects));
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            headerRow.createCell(i).setCellValue(fields[i].getName());
        }

        for (int i = 0; i < objects.size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                row.createCell(j).setCellValue(fields[j].get(objects.get(i)).toString());
            }
        }
        new File(filePath).mkdirs();
        FileOutputStream out = new FileOutputStream(filePath + createFileName(objects) + ".xlsx");
        workbook.write(out);
        out.close();

    }

    public static <T> void createCSV(List<T> objects) throws IllegalAccessException, IOException {

        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("List of objects is null or empty");
        }

        Class<?> clazz = objects.getFirst().getClass();
        Field[] fields = clazz.getDeclaredFields();
        new File(filePath).mkdirs();
        File file = new File(filePath + createFileName(objects) + ".csv");
        FileWriter outputFile = new FileWriter(file);

        CSVWriter writer = new CSVWriter(outputFile);
        String[] header = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            header[i] = fields[i].getName();
        }

        writer.writeNext(header);

        for (T object : objects) {
            String[] data = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                data[i] = fields[i].get(object).toString();
            }
            writer.writeNext(data);
        }
        writer.close();
    }

    public static Document createPDF(Election election, Personne personne) throws IOException, DocumentException {
        Document document = new Document();
        new File(election.getNom()).mkdirs();

        PdfWriter.getInstance(document, new FileOutputStream( election.getNom() + "/" + personne.getNom() + ".pdf"));
        return document;
    }
}
