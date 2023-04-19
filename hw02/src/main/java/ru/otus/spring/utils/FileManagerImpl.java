package ru.otus.spring.utils;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class FileManagerImpl implements FileManager {
    private final String  filePath;

    public FileManagerImpl(@Value("${filename.questions}")String filePath) {
        this.filePath = filePath;
    }
    @Override
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
