package ru.otus.spring.utils;

import au.com.bytecode.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@AllArgsConstructor
@Service
public class FileManager {
    private final String  filePath;

    public List<String[]> getCsvArray(){
        List<String[]> res = new java.util.ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        try (InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            CSVReader csvReader = new CSVReader(reader, ',' , '"' , 0);
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                res.add(nextLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
